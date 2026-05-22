package atividade_extensionista.projeto_ambiental.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfigurations {

    private SecurityFilter securityFilter;

    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // --- CORS (Livre para qualquer um)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // --- ENDPOINTS PÚBLICOS DE LOGIN (Não exigem Token) ---
                        .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/autenticacao/guest").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v3/api-docs/**", "/swagger-ui/**").permitAll()

                        // --- PERMISSÕES DO GUEST (Convidado) ---
                        // O Guest pode criar denúncias, ler artigos e olhar a transparência da bolsa
                        .requestMatchers(HttpMethod.POST, "/ocorrencia").hasAnyRole("GUEST", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/educacao/conteudos").hasAnyRole("GUEST", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/financeiro/transparencia-de-valores").hasAnyRole("GUEST", "ADMIN")

                        // --- PERMISSÕES EXCLUSIVAS DO ADMIN/FISCAL ---
                        // Mudar status de denúncias e aplicar multas exige perfil de administrador
                        .requestMatchers(HttpMethod.PATCH, "/ocorrencia/*/status").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/financeiro/multas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/educacao/conteudos").hasRole("ADMIN")

                        // --- QUALQUER OUTRA REQUISIÇÃO EXIGE AUTH ---
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Libera todas as rotas do sistema (/ocorrencia, /autenticacao, etc.)
                        .allowedOrigins("http://localhost:4200") // Permite apenas o seu Front-end Angular acessar
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Libera os métodos HTTP
                        .allowedHeaders("*") // Libera todos os cabeçalhos de requisição
                        .allowCredentials(true); // Permite envio de cookies/tokens se necessário futuramente
            }
        };
    }
}
