package atividade_extensionista.projeto_ambiental.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

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
