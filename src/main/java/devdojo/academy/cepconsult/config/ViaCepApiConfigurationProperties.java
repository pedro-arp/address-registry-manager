package devdojo.academy.cepconsult.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "brasil-api")
public record ViaCepApiConfigurationProperties(String baseUrl, String uriCep) {
}