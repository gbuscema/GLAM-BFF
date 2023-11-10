package com.glam.bff.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class JWTSecurityConfig {

  @Value("${spring.authorization-enabled}")
  private Boolean authorizationEnabled;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                    //TODO Use same origin or just disable?
                    //.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            )
            .authorizeHttpRequests(request -> {
              if(authorizationEnabled){
                request.requestMatchers("/authentication/register").permitAll();
                request.requestMatchers(HttpMethod.GET, "/recommendation/**")
                        .hasAnyAuthority("USER", "ADMIN");
                request.requestMatchers(HttpMethod.POST, "/recommendation/*")
                        .hasAnyAuthority("ADMIN", "USER");
                //TODO - Why doesn't the regex work?
                //request.requestMatchers(HttpMethod.GET, "/auth/**").permitAll();
                request.anyRequest().permitAll();
              }else{
                request.anyRequest().permitAll();
              }
            })
            // TEMPORARY DISABLED FOR TESTING
            //.formLogin(Customizer.withDefaults())
            //.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
            .build();
  }
}