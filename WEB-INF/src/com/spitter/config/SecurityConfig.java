package com.spitter.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

import com.spitter.security.encoder.MD5Encoder;

/**
 * @author Tyler Yin
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public MD5Encoder md5Encoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin().loginPage("/login").and().logout().logoutSuccessUrl("/").and().rememberMe()
                .tokenRepository(new InMemoryTokenRepositoryImpl()).tokenValiditySeconds(2419200).key("spittrKey").and()
                .httpBasic().realmName("Spitter").and().authorizeRequests().antMatchers("/").authenticated()
                .antMatchers("/spitter/me").authenticated().antMatchers(HttpMethod.POST, "/spittles").authenticated()
                .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, true from Spitter where username=?")
                .authoritiesByUsernameQuery("select username, role from Spitter where username=?")
                .passwordEncoder(md5Encoder)
                .rolePrefix("ROLE_");
        // .groupAuthoritiesByUsername(JdbcUserDetailsManager.DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY)
    }

}