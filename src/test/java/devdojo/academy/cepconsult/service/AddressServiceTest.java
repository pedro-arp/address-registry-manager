package devdojo.academy.cepconsult.service;

import devdojo.academy.cepconsult.commons.AddressUtils;
import devdojo.academy.cepconsult.domain.Address;
import devdojo.academy.cepconsult.repository.AddressHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    private List<Address> address;

    @Mock
    private AddressHardCodedRepository repository;

    @InjectMocks
    private AddressUtils addressUtils;

    @InjectMocks
    private AddressService service;

    @BeforeEach
    void init() {
        address = addressUtils.newAddressList();
    }


    @Test
    @DisplayName("findByCep() returns the address according to the CEP")
    void findByCep_ReturnAddress_WhenSuccessful() {
        var cep = address.get(0).getCep();

        BDDMockito.when(repository.findByCep(cep)).thenReturn(address.get(0));

        var byCep = service.findByCep(cep);

        Assertions.assertThat(byCep).isEqualTo(address.get(0));
    }
}