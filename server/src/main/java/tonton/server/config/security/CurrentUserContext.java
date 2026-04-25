package tonton.server.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tonton.server.common.enums.RoleName;
import tonton.server.exception.ForbiddenException;
import tonton.server.exception.UnauthorizedException;

@Component
public class CurrentUserContext {

    public AppUserPrincipal requirePrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Cần đăng nhập để truy cập");
        }
        if (!(authentication.getPrincipal() instanceof AppUserPrincipal principal)) {
            throw new UnauthorizedException("Cần đăng nhập để truy cập");
        }
        return principal;
    }

    public Long requireUserId() {
        return requirePrincipal().getId();
    }

    public boolean isPrivileged() {
        RoleName roleName = requirePrincipal().getRoleName();
        return roleName == RoleName.ADMIN || roleName == RoleName.SALE;
    }

    public void ensureOwnerOrPrivileged(Long ownerUserId) {
        if (isPrivileged()) {
            return;
        }
        Long currentUserId = requireUserId();
        if (ownerUserId == null || !ownerUserId.equals(currentUserId)) {
            throw new ForbiddenException("Bạn không có quyền truy cập tài nguyên này");
        }
    }
}
