package devdojo.academy.cepconsult.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "viacep-api")
public record ViaCepApiConfigurationProperties(String baseUrl, String uriCep) {
}