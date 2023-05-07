package com.example.Chat.Config;


import com.example.Chat.Security.JwtAuthenticationTokenFilter;
import com.example.Chat.Service.ChatUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSocketSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final ChatUserService chatUserService;

    private final RedisTemplate<String,Object> redisTemplate;

    @Bean
    @Operation(summary = "securityFilterChain", description = "securityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/error").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .addFilterBefore(new JwtAuthenticationTokenFilter(chatUserService,redisTemplate), UsernamePasswordAuthenticationFilter.class)
                //认证失败处理器;
                .exceptionHandling().authenticationEntryPoint(((request, response, authException) -> {
                    //请求转发到登陆失败接口
                    request.getRequestDispatcher("/userAuth/loginFail").forward(request, response);
                }))
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    //请求转发到权限不足接口
                    request.getRequestDispatcher("/userAuth/loginFail").forward(request, response);
                })
                .and()
                .authenticationProvider(authenticationProvider);
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(
                "/userAuth", "/userAuth/loginFail","/userAuth/register", "/socket/**",
                "/error", "/file/**", "/doc.html",
                "/webjars/**", "/swagger-resources/**", "/v3/**",
                "/favicon.ico"));
    }

}
