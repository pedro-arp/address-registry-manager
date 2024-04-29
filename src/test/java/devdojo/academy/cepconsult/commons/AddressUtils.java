package devdojo.academy.cepconsult.commons;

import devdojo.academy.cepconsult.domain.Address;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressUtils {

    public List<Address> newAddressList() {
        var address01 = Address.builder().id(1L).cep("00000-001").logradouro("Rua Teste 01").complemento("").bairro("Bairro Test 01").localidade("São Paulo").uf("SP").ibge("0000001").gia("0001").ddd("01").siafi("0001").build();
        var address02 = Address.builder().id(2L).cep("00000-002").logradouro("Rua Teste 02").complemento("").bairro("Bairro Test 02").localidade("São Paulo").uf("SP").ibge("0000002").gia("0002").ddd("02").siafi("0002").build();
        var address03 = Address.builder().id(3L).cep("00000-003").logradouro("Rua Teste 03").complemento("").bairro("Bairro Test 03").localidade("São Paulo").uf("SP").ibge("0000003").gia("0003").ddd("03").siafi("0003").build();
        return new ArrayList<>(List.of(address01, address02, address03));
    }

    public Address addressNullFields() {
        return Address.builder().cep(null).logradouro(null).complemento(null).bairro(null).localidade(null).uf(null).ibge(null).gia(null).ddd(null).siafi(null).build();

    }

    public Address newAddressToSave() {
        return Address.builder().id(4L).cep("00000-004").logradouro("Rua Teste 04").complemento("").bairro("Bairro Test 04").localidade("São Paulo").uf("SP").ibge("0000004").gia("0004").ddd("04").siafi("0004").build();

    }

}
