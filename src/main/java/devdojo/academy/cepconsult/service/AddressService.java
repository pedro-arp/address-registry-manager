package devdojo.academy.cepconsult.service;

import devdojo.academy.cepconsult.consumes.AddressConsumes;
import devdojo.academy.cepconsult.domain.Address;
import devdojo.academy.cepconsult.exception.DuplicateEntryException;
import devdojo.academy.cepconsult.exception.NotFoundException;
import devdojo.academy.cepconsult.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private static final String ADDRESS_NOT_FOUND = "Address not found";
    private static final String ADDRESS_DUPLICATE = "Address %s is already in use";

    private final AddressRepository repository;

    private final AddressConsumes consumes;


    public List<Address> findAll() {

        return repository.findAll();

    }

    public Address findByCep(String cep) {

        return consumes.responseApi(cep).orElseThrow(() -> new NotFoundException(ADDRESS_NOT_FOUND));

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
