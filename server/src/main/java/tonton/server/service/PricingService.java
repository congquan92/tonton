package tonton.server.service;

import tonton.server.common.enums.RoleName;
import tonton.server.model.Product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public interface PricingService {
    BigDecimal resolveUnitPrice(Product product, BigDecimal quantity, RoleName roleName);

    Map<Long, BigDecimal> resolveUnitPriceForProducts(Collection<Long> productIds, BigDecimal quantity, RoleName roleName);
}
