package ru.urfu.kursach.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(a -> {
            a.requestMatchers("/index").permitAll();
            a.requestMatchers("/register/**").permitAll();
            a.requestMatchers("/users").hasRole("ADMIN");
            a.requestMatchers("/actuator/**").hasRole("ADMIN");
            a.requestMatchers("/match/create/**").not().hasRole("READ_ONLY");
            a.requestMatchers("/match/delete/**").not().hasRole("READ_ONLY");
            a.requestMatchers("/match/part/create/**").not().hasRole("READ_ONLY");
            a.requestMatchers("/match/part/edit/**").not().hasRole("READ_ONLY");
            a.requestMatchers("/player/create/**").not().hasRole("READ_ONLY");
            a.requestMatchers("/player/edit/**").not().hasRole("READ_ONLY");
            a.requestMatchers("/team/create/**").not().hasRole("READ_ONLY");
            a.requestMatchers("/team/edit/**").not().hasRole("READ_ONLY");
            a.anyRequest().authenticated();
//            a.anyRequest().permitAll();
        }).formLogin(form -> {
            form.loginPage("/login");
            form.loginProcessingUrl("/login");
            form.defaultSuccessUrl("/index");
            form.permitAll();
        }).logout(l -> l.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());
        return http.build();
    }
}
