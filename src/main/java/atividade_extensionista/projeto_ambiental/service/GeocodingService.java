package atividade_extensionista.projeto_ambiental.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeocodingService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public GeocodingService(WebClient.Builder webClientBuilder) {
        // Inicializa o cliente HTTP apontando para o servidor gratuito do OpenStreetMap
        this.webClient = webClientBuilder.baseUrl("https://openstreetmap.org").build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Transforma um endereço de texto em coordenadas geográficas gratuitamente.
     */
    public double[] buscarCoordenadas(String endereco) {
        try {
            // O OpenStreetMap exige um "User-Agent" válido no cabeçalho para evitar bloqueios
            String jsonResposta = this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search")
                            .queryParam("format", "json")
                            .queryParam("limit", "1")
                            .queryParam("q", endereco)
                            .build())
                    .header("User-Agent", "ProjetoAmbiental-1.0")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Aguarda a resposta síncrona

            // Mapeia a árvore do JSON recebido
            JsonNode raiz = objectMapper.readTree(jsonResposta);

            // Verifica se a API encontrou o endereço físico correspondente
            if (raiz.isArray() && !raiz.isEmpty()) {
                double lat = raiz.get(0).get("lat").asDouble();
                double lon = raiz.get(0).get("lon").asDouble();
                return new double[]{lat, lon};
            }

        } catch (Exception e) {
            throw new RuntimeException("Falha na comunicação com o serviço de mapas gratuito.", e);
        }

        throw new IllegalArgumentException("O sistema de mapas não conseguiu localizar o endereço digitado.");
    }

}
