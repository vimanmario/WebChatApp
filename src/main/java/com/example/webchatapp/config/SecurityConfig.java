package com.example.webchatapp.config;

import com.example.webchatapp.filter.CookieCleanupFilter;
import com.example.webchatapp.filter.NoCacheFilter;
import com.example.webchatapp.filter.SameSiteCookieFilter;
import com.example.webchatapp.filter.SessionDebugFilter;
import com.example.webchatapp.handler.CustomLogoutHandler;
import com.example.webchatapp.service.CustomRememberMeServices;
import com.example.webchatapp.service.UserService;
import jakarta.servlet.http.Cookie;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.DelegatingFilterProxy;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SameSiteCookieFilter sameSiteCookieFilter;

    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder, SameSiteCookieFilter sameSiteCookieFilter) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.sameSiteCookieFilter = sameSiteCookieFilter;
    }

    // incarcarea utilizatorului si validarea parolei folosind userService
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
        return auth.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/chat").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/conversations").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/chat", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // "perform_logout" sau "/logout"
                        .logoutSuccessUrl("/") // Redirectionare catre login dupa log-out , avea "/login inainte"
                        .invalidateHttpSession(true) // Invalidatează sesiunea
                        .clearAuthentication(true) // Șterge autentificarea
                        .deleteCookies("JSESSIONID", "remember-me")
                        .addLogoutHandler(new CustomLogoutHandler())
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecretKey") // Cheie pentru securizarea token-ului
                        .tokenValiditySeconds(60 * 60) // o ora // Valabilitate de 7 zile (7 * 24 * 60 * 60)
                        .rememberMeCookieName("remember-me") // Numele cookie-ului "remember me"
                        .rememberMeParameter("remember-me")
                        .useSecureCookie(true) // Pentru HTTPS
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/login?expired")
                )
                .addFilterBefore(sameSiteCookieFilter, SessionManagementFilter.class)
                .addFilterBefore(new CookieCleanupFilter(), LogoutFilter.class);

        return http.build();
    }

    // acesta dezactiveaza  cache-ul pe paginile sensibile, teoretic nu prea avem nevoie de el fiindca in majoritatea
    // aplicatiilor web acesta este gestionat mai usor la nivel de HTTP si nu e nevoie sa fie dezactivat explicit
    // o sa comentam metoda momentan

    @Bean
    public FilterRegistrationBean<NoCacheFilter> noCacheFilter() {
        FilterRegistrationBean<NoCacheFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new NoCacheFilter());
        registrationBean.addUrlPatterns("/chat/*", "/login", "/register"); // Specifică URL-urile ce vor trece prin filtru
        registrationBean.setOrder(1);  // Setează prioritatea filtrului
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SessionDebugFilter> sessionDebugFilter() {
        FilterRegistrationBean<SessionDebugFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionDebugFilter());
        registrationBean.addUrlPatterns("/*"); // Aplică filtru pe toate rutele
        registrationBean.setOrder(2); // Prioritate mai mică decât NoCacheFilter
        return registrationBean;
    }

}