package devdojo.academy.cepconsult.service;

import devdojo.academy.cepconsult.consumes.AddressConsumes;
import devdojo.academy.cepconsult.domain.Address;
import devdojo.academy.cepconsult.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    private final AddressConsumes consumes;


    public List<Address> findAll() {

        return repository.findAll();

    }

    public Address findByCep(String cep) {

        return consumes.responseApi(cep);

    }

    public Address findById(Long id) {

        return repository.findById(id).orElseThrow(() -> new RuntimeException("No address found by id"));

    }

    public Address save(String cep) {

        var address = consumes.responseApi(cep);

        return repository.save(address);

    }


    public void delete(Address address) {
        repository.delete(address);
    }

    public void update(Address address) {

        assertAddressExistsInDatabase(address);

        repository.save(address);

    }

    private void assertAddressExistsInDatabase(Address address) {

        findById(address.getId());

    }
}
