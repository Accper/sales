package com.business.config;

import com.business.service.RoleFilterSecurityInterceptor;
import com.business.utils.MD5Util;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

/**
 * @Author: taoye
 * @Description: WebSecurityConfig 认证和授权配置
 * @Date: 15:29 2018/8/10
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private RoleFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String) rawPassword));
            } //user Details Service验证
        });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        // 解决静态资源被拦截问题
//        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**","/login", "/logout","/loginFailure")  // 需要过滤无需登陆的接口
                .permitAll()
                .anyRequest().authenticated() // 任何请求，登录后可以访问
                .and().cors().disable().sessionManagement().and()
                .formLogin()
                .loginPage("/login").usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/dashboard", true)
                //.successForwardUrl("/loginSuccess")
                .failureForwardUrl("/loginFailure")
                .and()
                .logout().deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll();
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class).csrf().disable();
    }
}
