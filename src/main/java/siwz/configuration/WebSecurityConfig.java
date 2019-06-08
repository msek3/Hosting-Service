package siwz.configuration;

import siwz.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/", "/login", "/logout")
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/userinfo")
                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

        http.authorizeRequests()
                .antMatchers("/admins")
                .access("hasRole('ROLE_ADMIN')");

        http.authorizeRequests()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");

        http.authorizeRequests()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
    }
}