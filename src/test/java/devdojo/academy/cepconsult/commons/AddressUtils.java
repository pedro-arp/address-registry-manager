package devdojo.academy.cepconsult.commons;

import devdojo.academy.cepconsult.domain.Address;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressUtils {

    public List<Address> newAddressList() {
        var address01 = Address.builder().id(1L).cep("00000-001").state("SP").city("São Paulo").neighborhood("Centro").street("Rua Teste 01").service("viacep").build();
        var address02 = Address.builder().id(2L).cep("00000-002").state("SP").city("São Paulo").neighborhood("Centro").street("Rua Teste 02").service("viacep").build();
        var address03 = Address.builder().id(3L).cep("00000-003").state("SP").city("São Paulo").neighborhood("Centro").street("Rua Teste 03").service("viacep").build();
        return new ArrayList<>(List.of(address01, address02, address03));
    }

    public Address addressNullFields() {
        return Address.builder().cep(null).state(null).service(null).city(null).neighborhood(null).street(null).street(null).build();

    }

    public Address newAddress() {
        return Address.builder().id(4L).cep("00000-004").state("SP").street("Avenida Paulista").neighborhood("Centro").city("São Paulo").service("viacep").build();

    }

}
