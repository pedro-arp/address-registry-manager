package devdojo.academy.cepconsult.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import devdojo.academy.cepconsult.config.ViaCepApiConfigurationProperties;
import devdojo.academy.cepconsult.domain.Address;
import devdojo.academy.cepconsult.exception.DuplicateEntryException;
import devdojo.academy.cepconsult.exception.NotFoundException;
import devdojo.academy.cepconsult.repository.AddressRepository;
import devdojo.academy.cepconsult.response.CepErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private static final String ADDRESS_NOT_FOUND = "Address not found";
    private static final String ADDRESS_DUPLICATE = "Address %s is already in use";

    private final AddressRepository repository;

    private final ViaCepApiConfigurationProperties properties;

    private final RestClient viaCepRestClient;

    private final ObjectMapper mapper;


    public Address findByCep(String cep) {
        return viaCepRestClient
                .get()
                .uri(properties.baseUrl() + properties.uriCep(), cep)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    var cepErrorResponse = mapper.readValue(response.getBody().readAllBytes(), CepErrorResponse.class);
                    throw new NotFoundException(cepErrorResponse.message());
                })
                .body(Address.class);
    }


    public List<Address> findAll() {
        return repository.findAll();

    }

    public Address findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(ADDRESS_NOT_FOUND));

    }

    public Address save(String cep) {
        var address = findByCep(cep);
        assertCepIsUnique(cep, address.getId());
        return repository.save(address);

    }


    public void delete(Long id) {
        var address = findById(id);
        repository.delete(address);
    }

    public void update(Address address) {
        assertAddressExistsInDatabase(address);
        repository.save(address);
    }

    private void assertAddressExistsInDatabase(Address address) {
        findById(address.getId());
    }

    private void assertCepIsUnique(String cep, Long addressId) {
        repository.findByCep(cep)
                .ifPresent(cepFound -> {
                    if (!cepFound.getId().equals(addressId)) {
                        throw new DuplicateEntryException(ADDRESS_DUPLICATE.formatted(cep));
                    }
                });
    }

}
