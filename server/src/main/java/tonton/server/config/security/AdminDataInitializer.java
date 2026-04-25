package tonton.server.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import tonton.server.common.enums.RoleName;
import tonton.server.model.Role;
import tonton.server.model.User;
import tonton.server.repository.RoleRepository;
import tonton.server.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
public class AdminDataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap-admin.enabled:true}")
    private boolean enabled;

    @Value("${app.bootstrap-admin.username:admin}")
    private String username;

    @Value("${app.bootstrap-admin.password:admin123}")
    private String password;

    @Value("${app.bootstrap-admin.email:nguyencongquan9211@gmail.com}")
    private String email;

    @Value("${app.bootstrap-admin.full-name:Nguyễn Công Quân}")
    private String fullName;

    @Value("${app.bootstrap-admin.phone-number:0345658495}")
    private String phoneNumber;

    @Bean
    public ApplicationRunner seedAdminUser() {
        return args -> {
            if (!enabled || userRepository.existsByUsername(username)) {
                return;
            }

            Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(RoleName.ADMIN);
                        role.setDescription("Quản trị viên hệ thống");
                        return roleRepository.save(role);
                    });

            User admin = new User();
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setEmail(email);
            admin.setFullName(fullName);
            admin.setPhoneNumber(phoneNumber);
            admin.setRole(adminRole);
            admin.setTokenVersion(1);
            userRepository.save(admin);
        };
    }
}
