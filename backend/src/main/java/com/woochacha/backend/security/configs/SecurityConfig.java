package com.woochacha.backend.security.configs;

<<<<<<< HEAD
import com.woochacha.backend.config.JwtSecurityConfig;
import com.woochacha.backend.domain.jwt.*;
=======
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
<<<<<<< HEAD
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
=======
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

<<<<<<< HEAD
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter, JwtTokenProvider jwtTokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter();
    }

=======
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // img, js, css 파일 등 보안 필터를 적용할 필요가 없는 리소스를 설정
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
<<<<<<< HEAD
        http
                .csrf().disable() // CSRF 보호 비활성화 (회원가입은 POST 요청이므로 필요하지 않음)
                .httpBasic().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

=======
        http.csrf().disable() // CSRF 보호 비활성화 (회원가입은 POST 요청이므로 필요하지 않음)
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS) // JWT Token 인증방식으로 세션은 필요 없으므로 비활성화

                .and()

                .authorizeRequests()
                .antMatchers("/", "/users/register", "/users/login").permitAll()
                .antMatchers("/products", "/products/details/**", "/products/filter", "/products/search").permitAll()
                .antMatchers("/users/**", "/products/**").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
<<<<<<< HEAD
                .anyRequest().authenticated()

                .and()
                    .apply(new JwtSecurityConfig(jwtTokenProvider));

        http.cors();
//        http
//                .formLogin()
//                .successHandler(CustomAuthFailureHandler.class);
=======
                .anyRequest().authenticated();

>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa

    }
}
