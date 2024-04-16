package devdojo.academy.cepconsult.consumes;

import devdojo.academy.cepconsult.domain.Address;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AddressConsumes {
    public ResponseEntity<Address> cepGet(String cep) {
        var url = String.format("https://viacep.com.br/ws/%s/json/", cep);
        var restTemplate = new RestTemplate();
        return restTemplate.exchange(url, HttpMethod.GET, null, Address.class);
    }

}
