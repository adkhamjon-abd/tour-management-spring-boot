package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.filter.AuthenticationLoggingFilter;
import org.example.security.JwtAuthFilter;
import org.example.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public JwtAuthFilter jwtAuthFilter(UserDetailsService userDetailsService,
                                       JwtUtil jwtUtil) {
        return new JwtAuthFilter(jwtUtil, userDetailsService);
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public UserDetailsService userDetailsService() {

        var manager = new InMemoryUserDetailsManager();
 
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        UserDetails company = User.withDefaultPasswordEncoder()
                        .username("company")
                        .password("company")
                        .roles("USER")
                        .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build();


        manager.createUser(user);
        manager.createUser(admin);
        manager.createUser(company);

        return manager; 

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) {
        http.authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers(HttpMethod.GET,"/tours/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/companies/**").permitAll()
                                .requestMatchers("/users/**").authenticated()
                                .requestMatchers("/users/auth").permitAll()
                                .requestMatchers("/bookings/{id:^[0-9]+$}").permitAll()
                                .anyRequest().denyAll()
                );
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new AuthenticationLoggingFilter(), JwtAuthFilter.class);

        return http.build();
    }

}
