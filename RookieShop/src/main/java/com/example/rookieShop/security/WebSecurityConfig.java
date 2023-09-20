package com.example.RookieShop.security;

import com.example.RookieShop.security.jwt.JwtAuthEntryPoint;
import com.example.RookieShop.security.jwt.JwtAuthTokenFilter;
import com.example.RookieShop.security.jwt.JwtUtils;
import com.example.RookieShop.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    final private JwtAuthEntryPoint unauthorizedHandler;

    private final JwtUtils jwtUtils;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthEntryPoint unauthorizedHandler, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        // TODO
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/categories**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
                .antMatchers("/api/categories**").hasRole("ADMIN")
                .antMatchers("/api/categories/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/products**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/**").permitAll()
                .antMatchers("/api/products**").hasRole("ADMIN")
                .antMatchers("/api/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/orders**").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/api/orders**").hasRole("ADMIN")
                .antMatchers("/api/orders**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET,"/api/users/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PATCH,"/api/users**").hasRole("USER")
                .antMatchers("/api/customers**").hasAnyRole("USER","ADMIN")
                .antMatchers("/api/product/rate/**").hasRole("USER")
                .antMatchers("/api/comments**").permitAll()
                .antMatchers("/api/comments/**").permitAll()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
