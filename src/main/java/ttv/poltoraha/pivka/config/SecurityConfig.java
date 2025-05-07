package ttv.poltoraha.pivka.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ttv.poltoraha.pivka.serviceImpl.UserDetailsServiceImpl;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * В текущем проекте система секьюрки представляет собой следующее:
 * У нас есть пользователи, которые хранятся в БД. Логин/пароль
 * У нас есть конфиг тут, где через authorizeHttpRequests можно вводить ограничения,
 * чтобы не давать обычным пользакам добавлять новые книги
 *
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                // без этой штуки вам не даст авторизоваться в веб-окошке бд h2
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .cors(AbstractHttpConfigurer::disable)
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

//        http.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN")
//                .requestMatchers("/**").permitAll().anyRequest().authenticated()
//                .and().formLogin().permitAll().and().logout().permitAll().and().httpBasic();
//                http.cors().disable().csrf().disable();

        return http.build();
    }
}
