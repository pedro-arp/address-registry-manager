package devdojo.academy.cepconsult.consumes;

import devdojo.academy.cepconsult.domain.Address;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AddressConsumes {
    public Address responseApi(String cep) {
        var url = String.format("https://viacep.com.br/ws/%s/json/", cep);
        var restTemplate = new RestTemplate();
        var exchange = restTemplate.exchange(url, HttpMethod.GET, null, Address.class);
        return exchange.getBody();
    }

}
