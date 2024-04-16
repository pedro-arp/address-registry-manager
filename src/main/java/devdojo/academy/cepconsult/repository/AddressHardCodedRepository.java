package devdojo.academy.cepconsult.repository;

import devdojo.academy.cepconsult.consumes.AddressConsumes;
import devdojo.academy.cepconsult.domain.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class AddressHardCodedRepository {

    private final AddressConsumes consumes;

    public Address findByCep(String cep) {
        return consumes.cepGet(cep).getBody();
    }
}
