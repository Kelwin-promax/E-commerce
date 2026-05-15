package com._DM.E_commerce.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilitado para facilitar testes em API REST
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll() // Permite cadastro de novos usuários
                .requestMatchers(HttpMethod.GET, "/produtos/**", "/categorias/**").permitAll() // Catálogo público
                .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll() // Permite visualizar imagens enviadas
                .anyRequest().authenticated() // Exige autenticação para o restante (pedidos, pagamentos, etc)
            )
            .httpBasic(Customizer.withDefaults()) // Permite autenticação Basic (útil para Postman/Insomnia)
            .formLogin(Customizer.withDefaults()); // Habilita o formulário de login padrão do Spring

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
