package com.binance.crypto.bot.config.security;

import com.binance.crypto.bot.config.security.listener.LoginFailureHandler;
import com.binance.crypto.bot.config.security.listener.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/login", "/static/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/dashboard", true)
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .and()
                .authenticationProvider(authenticationProvider());


        return http.build();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }


}
