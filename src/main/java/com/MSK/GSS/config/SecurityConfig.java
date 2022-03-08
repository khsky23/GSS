package com.MSK.GSS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers("/css/**", "/js/**","/img/**","/error/**","/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests( authorize -> authorize

                        .mvcMatchers(
                                "/members/join",
                                         "/members/login"
                        )
                        .anonymous()
                        .mvcMatchers(
                                "/articles/**",
                                "/"
                        )
                        .permitAll()
                        .mvcMatchers(
                                "/adm/**"
                        )
                        .hasRole("ADMIN")
                        .anyRequest()
                        .denyAll()
                )

                .formLogin()
                .loginPage("/members/login")
                .loginProcessingUrl("/members/doLogin")
                .usernameParameter("loginId")
                .passwordParameter("loginPw")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .and()
                .sessionManagement()
                .invalidSessionUrl("/")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        }
    }


