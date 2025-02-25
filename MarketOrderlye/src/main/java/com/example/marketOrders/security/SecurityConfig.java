package com.example.marketOrders.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 🔥 Отключаем CSRF только для API (чтобы работали POST-запросы на регистрацию и логин)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/auth/**") // ⛔ CSRF отключен только для API
                )

                // 🔒 Настройка доступа к страницам
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login").permitAll() // ✅ Доступно всем
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 🔒 Только админам
                        .requestMatchers("/user/**").hasRole("USER") // 🔒 Только юзерам
                        .anyRequest().authenticated() // 🔒 Все остальные запросы требуют входа
                )

                // 🔑 Настройки формы логина
                .formLogin(login -> login
                        .loginPage("/login") // 🔥 Оставляем стандартную страницу логина
                        .defaultSuccessUrl("/dashboard", true) // ✅ Перенаправление после входа
                        .permitAll() // 🔓 Страница логина доступна всем
                )

                // 🚪 Настройки выхода
                .logout(logout -> logout
                        .logoutUrl("/logout") // 🔥 URL для выхода
                        .logoutSuccessUrl("/") // 🔙 Перенаправление после выхода
                        .permitAll() // ✅ Выход доступен всем
                );

        return http.build();
    }
}


//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf().disable() // Отключаем CSRF для простых POST-запросов
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.POST, "/customers").permitAll() // Разрешаем POST-запросы
//                        .requestMatchers(HttpMethod.GET, "/customers").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults()) // Включаем Basic Auth
//                .build();
//    }
//}

    /*  старый метод, до хеширования пароля
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable() // Пока отключаем CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Открываем всё
                )
                .build();
    }
    */