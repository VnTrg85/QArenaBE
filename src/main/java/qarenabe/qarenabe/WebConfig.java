package qarenabe.qarenabe;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Allow React origin
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true) // Important: Allow credentials (cookies)
                .exposedHeaders("Authorization");
    }
    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Tắt CSRF nếu không cần thiết
                .authorizeRequests(requests -> {
                    try {
                        requests
                        .requestMatchers("/public/**").permitAll()  // Các route không cần auth
                        .anyRequest().authenticated()  // Các route còn lại yêu cầu xác thực
                        .and()
                        .httpBasic(basic -> basic.disable());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }); 

        return http.build();
    }
    
}




