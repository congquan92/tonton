package tonton.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import tonton.server.config.security.AppUserDetailsService;
import tonton.server.config.security.RestAccessDeniedHandler;
import tonton.server.config.security.RestAuthenticationEntryPoint;
import tonton.server.config.security.jwt.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AppUserDetailsService appUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/error",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login", "/api/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/products/**",
                                "/api/categories/**",
                                "/api/uoms/**",
                                "/api/posts/**",
                                "/api/post-categories/**"
                        ).permitAll()

                        // ADMIN 
                        .requestMatchers("/api/users/**", "/api/roles/**").hasRole("ADMIN")

                        // ADMIN và SALE
                        .requestMatchers(HttpMethod.POST,
                                "/api/products/**",
                                "/api/categories/**",
                                "/api/uoms/**",
                                "/api/posts/**",
                                "/api/post-categories/**",
                                "/api/product-images/**",
                                "/api/price-tiers/**",
                                "/api/uom-conversions/**",
                                "/api/uploads/**"
                        ).hasAnyRole("ADMIN", "SALE")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/products/**",
                                "/api/categories/**",
                                "/api/uoms/**",
                                "/api/posts/**",
                                "/api/post-categories/**",
                                "/api/product-images/**",
                                "/api/price-tiers/**",
                                "/api/uom-conversions/**"
                        ).hasAnyRole("ADMIN", "SALE")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/products/**",
                                "/api/categories/**",
                                "/api/uoms/**",
                                "/api/posts/**",
                                "/api/post-categories/**",
                                "/api/product-images/**",
                                "/api/price-tiers/**",
                                "/api/uom-conversions/**"
                        ).hasAnyRole("ADMIN", "SALE")

                        .requestMatchers("/api/auth/logout", "/api/auth/me").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .rememberMe(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(appUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
