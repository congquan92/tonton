package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tonton.server.common.enums.RoleName;
import tonton.server.model.PriceTier;
import tonton.server.model.Product;
import tonton.server.repository.PriceTierRepository;
import tonton.server.repository.ProductRepository;
import tonton.server.service.PricingService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final PriceTierRepository priceTierRepository;
    private final ProductRepository productRepository;

    @Override
    public BigDecimal resolveUnitPrice(Product product, BigDecimal quantity, RoleName roleName) {
        if (product == null || product.getId() == null) {
            return BigDecimal.ZERO;
        }
        Map<Long, BigDecimal> priceMap = resolveUnitPriceForProducts(
                Collections.singletonList(product.getId()),
                quantity,
                roleName
        );
        BigDecimal resolved = priceMap.get(product.getId());
        if (resolved != null && resolved.compareTo(BigDecimal.ZERO) > 0) {
            return resolved;
        }
        return extractPriceFromAttributes(product.getAttributes());
    }

    @Override
    public Map<Long, BigDecimal> resolveUnitPriceForProducts(Collection<Long> productIds, BigDecimal quantity, RoleName roleName) {
        if (productIds == null || productIds.isEmpty()) {
            return Collections.emptyMap();
        }

        BigDecimal targetQty = quantity == null ? BigDecimal.ONE : quantity;
        RoleName resolvedRole = roleName == null ? RoleName.B2C : roleName;

        List<Long> ids = productIds.stream().filter(Objects::nonNull).distinct().toList();
        List<PriceTier> tiers = priceTierRepository.findByProductIdInAndRole_NameOrderByProductIdAscMinQtyDesc(ids, resolvedRole);
        if (tiers.isEmpty() && resolvedRole != RoleName.B2C) {
            tiers = priceTierRepository.findByProductIdInAndRole_NameOrderByProductIdAscMinQtyDesc(ids, RoleName.B2C);
        }

        Map<Long, List<PriceTier>> grouped = tiers.stream()
                .collect(Collectors.groupingBy(t -> t.getProduct().getId()));

        Map<Long, BigDecimal> result = new HashMap<>();
        for (Long productId : ids) {
            BigDecimal resolved = resolveFromTiers(grouped.get(productId), targetQty);
            if (resolved != null) {
                result.put(productId, resolved);
                continue;
            }
            productRepository.findById(productId)
                    .ifPresent(product -> result.put(productId, extractPriceFromAttributes(product.getAttributes())));
        }
        return result;
    }

    private BigDecimal resolveFromTiers(List<PriceTier> tiers, BigDecimal quantity) {
        if (tiers == null || tiers.isEmpty()) {
            return null;
        }
        BigDecimal qty = quantity == null ? BigDecimal.ONE : quantity;
        for (PriceTier tier : tiers) {
            if (tier.getMinQty() == null || tier.getPrice() == null) {
                continue;
            }
            if (qty.compareTo(tier.getMinQty()) >= 0) {
                return tier.getPrice().setScale(2, RoundingMode.HALF_UP);
            }
        }
        return tiers.stream()
                .filter(t -> t.getPrice() != null)
                .min(Comparator.comparing(PriceTier::getMinQty, Comparator.nullsLast(BigDecimal::compareTo)))
                .map(PriceTier::getPrice)
                .map(p -> p.setScale(2, RoundingMode.HALF_UP))
                .orElse(null);
    }

    private BigDecimal extractPriceFromAttributes(Map<String, Object> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return BigDecimal.ZERO;
        }
        Object raw = attributes.get("price");
        if (raw == null) {
            raw = attributes.get("basePrice");
        }
        if (raw == null) {
            return BigDecimal.ZERO;
        }
        if (raw instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue()).setScale(2, RoundingMode.HALF_UP);
        }
        try {
            return new BigDecimal(raw.toString()).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException ex) {
            return BigDecimal.ZERO;
        }
    }
}
