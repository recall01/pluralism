package com.wenyizhou.job.config;

import com.wenyizhou.job.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                //区分大小写
                .antMatchers("/studentList","/teacherInfo","/detailsJob" ).hasRole("TEACHER")
                .antMatchers("/studentInfo","/newsList","/jobRecord","/improveInfo").hasRole("STUDENT")
                .antMatchers("/backstage").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and().anonymous()
                .and()
                .formLogin().loginPage("/user/login").defaultSuccessUrl("/login")
                .and()
                .httpBasic();
        http.csrf().disable();
    }
}
