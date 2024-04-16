package devdojo.academy.cepconsult.service;

import devdojo.academy.cepconsult.domain.Address;
import devdojo.academy.cepconsult.repository.AddressHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressHardCodedRepository repository;

    public Address findByCep(String cep) {
        return repository.findByCep(cep);
    }

}
