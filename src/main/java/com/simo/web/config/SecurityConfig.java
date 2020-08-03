package com.simo.web.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/users/login**", "/css/**", "/js/**", "/webjars/**").permitAll()
                .antMatchers("/users/register**").not().authenticated()
                .antMatchers("/**").authenticated()
//                .and()
//                .rememberMe()
//                .rememberMeParameter('rememberMe')
//                .key('remember Me Encryption Key')
//                .rememberMeCookieName('rememberMeCookieName')
//                .tokenValiditySeconds(10000)
                .and()
                .formLogin()
                .loginPage("/users/login")
                .loginProcessingUrl("/login/authenticate")
                .failureForwardUrl("/users/login?param.error=bad_credentials")
                .successForwardUrl("/home")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/users/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
