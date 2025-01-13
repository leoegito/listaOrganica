package leoegito.listaOrganica.Configuration;


import leoegito.listaOrganica.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private MyUserService myUserService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.addExposedHeader(HttpHeaders.LOCATION);
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/console/**").hasRole("ADMIN");
                    registry.requestMatchers("/home", "/product", "/product/**", "/product/search" ,"/users/register", "/h2-console/**").permitAll();
                    registry.requestMatchers(HttpMethod.DELETE, "/**").permitAll();
                    registry.requestMatchers(HttpMethod.PUT, "/**").permitAll();
                    registry.requestMatchers(HttpMethod.POST, "/**").permitAll();
                    registry.requestMatchers(HttpMethod.GET, "/**").permitAll();
                    registry.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    registry.requestMatchers("/user/**").hasRole("USER");
                    registry.requestMatchers("/shoppingList/**", "/price/**", "/users/**").authenticated();
                })
//                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
//                        .loginPage("/login")
//                        .successHandler(new AuthenticationSuccessHandler())
//                        .permitAll()
//                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return this.myUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
