package org.zerock.api1014.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zerock.api1014.security.filter.JWTCheckFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

         //        http.authorizeHttpRequests(
         //           (auth) -> auth.requestMatchers("/api/v1/product/list").permitAll()
         //   );

        http.formLogin(config -> config.disable());

        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.NEVER));

        http.csrf(config -> config.disable());

        http.addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
