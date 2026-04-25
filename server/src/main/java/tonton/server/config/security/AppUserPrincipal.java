package tonton.server.config.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tonton.server.common.enums.RoleName;
import tonton.server.model.User;

import java.util.Collection;
import java.util.List;

@Getter
public class AppUserPrincipal implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final Integer tokenVersion;
    private final RoleName roleName;
    private final List<GrantedAuthority> authorities;

    private AppUserPrincipal(Long id, String username, String password, Integer tokenVersion, RoleName roleName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tokenVersion = tokenVersion == null ? 1 : tokenVersion;
        this.roleName = roleName;
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleName.name()));
    }

    public static AppUserPrincipal fromUser(User user) {
        return new AppUserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getTokenVersion(),
                user.getRole().getName()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
