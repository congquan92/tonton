package tonton.server.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tonton.server.common.enums.RoleName;
import tonton.server.model.Role;
import tonton.server.repository.RoleRepository;

@Configuration
@RequiredArgsConstructor
public class RoleDataInitializer {

    private final RoleRepository roleRepository;

    @Bean
    public ApplicationRunner seedRoles() {
        return args -> {
            createRoleIfMissing(RoleName.B2C, "Khách hàng cá nhân");
            createRoleIfMissing(RoleName.B2B, "Khách hàng doanh nghiệp");
            createRoleIfMissing(RoleName.SALE, "Nhân viên bán hàng");
            createRoleIfMissing(RoleName.ADMIN, "Quản trị viên");
        };
    }

    private void createRoleIfMissing(RoleName roleName, String description) {
        if (roleRepository.existsByName(roleName)) {
            return;
        }
        Role role = new Role();
        role.setName(roleName);
        role.setDescription(description);
        roleRepository.save(role);
    }
}
