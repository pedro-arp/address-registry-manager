package devdojo.academy.cepconsult.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfiguration {

    private final ViaCepApiConfigurationProperties viaCepApiConfigurationProperties;

    @Bean
    public RestClient viaCepApiClient(RestClient.Builder builder) {
        return builder.baseUrl(viaCepApiConfigurationProperties.baseUrl()).build();
    }

}