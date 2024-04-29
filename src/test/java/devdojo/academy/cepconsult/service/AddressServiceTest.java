package devdojo.academy.cepconsult.service;

import devdojo.academy.cepconsult.commons.AddressUtils;
import devdojo.academy.cepconsult.consumes.AddressConsumes;
import devdojo.academy.cepconsult.domain.Address;
import devdojo.academy.cepconsult.exception.DuplicateEntryException;
import devdojo.academy.cepconsult.exception.NotFoundException;
import devdojo.academy.cepconsult.repository.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    private List<Address> address;

    @Mock
    private AddressRepository repository;

    @Mock
    private AddressConsumes consumes;

    @InjectMocks
    private AddressUtils addressUtils;

    @InjectMocks
    private AddressService service;

    @BeforeEach
    void init() {

        address = addressUtils.newAddressList();

    }

    @Test
    @DisplayName("findAll() returns 'all' addresses in data base")
    void findAll_ReturnsAllAddresses_WhenSuccessful() {

        var allAddresses = addressUtils.newAddressList();

        var size = allAddresses.size();

        BDDMockito.when(repository.findAll()).thenReturn(allAddresses);

        var addresses = service.findAll();

        Assertions.assertThat(addresses).isNotNull().hasSize(size);

    }


    @Test
    @DisplayName("findByCep() returns the address according to the CEP")
    void findByCep_ReturnAddress_WhenSuccessful() {

        var cep = address.get(0).getCep();

        BDDMockito.when(consumes.responseApi(cep)).thenReturn(Optional.of(address.get(0)));

        var byCep = service.findByCep(cep);

        Assertions.assertThat(byCep).isEqualTo(address.get(0));

    }

    @Test
    @DisplayName("findById() returns the address according to the 'id'")
    void findById_ReturnAddress_WhenSuccessful() {

        var addressFound = addressUtils.newAddressList().get(0);

        var id = addressFound.getId();

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(addressFound));

        var address = service.findById(id);

        Assertions.assertThat(address).isNotNull().isEqualTo(addressFound);

    }

    @Test
    @DisplayName("findById() throw NotFoundException when no address is found")
    void findById_ReturnNotFoundException_WhenAddressNotFound() {

        var id = 1L;

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.findById(id))
                .isInstanceOf(NotFoundException.class);

    }

    @Test
    @DisplayName("save() Creates Address in database")
    void save_CreatesAddress_WhenSuccessful() {

        var addressToSave = addressUtils.newAddressToSave();

        var cep = addressToSave.getCep();

        BDDMockito.when(consumes.responseApi(cep)).thenReturn(Optional.of(addressToSave));

        BDDMockito.when(repository.save(addressToSave)).thenReturn(addressToSave);

        var addressSaved = service.save(addressToSave.getCep());

        Assertions.assertThat(addressToSave).hasNoNullFieldsOrProperties().isNotNull().isEqualTo(addressSaved);

    }

    @Test
    @DisplayName("save() Returns DuplicateEntryException when CEP is duplicated")
    void save_ReturnsDuplicateEntryException_WhenCepIsDuplicated() {

        var addressDuplicatedToSave = this.address.get(0);

        var cep = addressDuplicatedToSave.getCep();

        BDDMockito.when(consumes.responseApi(cep)).thenReturn(Optional.of(addressDuplicatedToSave));

        BDDMockito.doThrow(DuplicateEntryException.class).when(repository).save(addressDuplicatedToSave);

        Assertions.assertThatException()
                .isThrownBy(() -> service.save(addressDuplicatedToSave.getCep()))
                .isInstanceOf(DuplicateEntryException.class);


    }

    @Test
    @DisplayName("delete() Removes address in database")
    void delete_RemovesAddress_WhenSuccessful() {

        var id = 1L;

        var addressToDelete = addressUtils.newAddressList().get(0);

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(addressToDelete));

        BDDMockito.doNothing().when(repository).delete(ArgumentMatchers.any());

        service.delete(id);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(id));

    }

    @Test
    @DisplayName("delete() throw NotFoundException when no address is found")
    void delete_ThrowNotFoundException_WhenAddressNotFound() {

        var id = 1L;

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(id))
                .isInstanceOf(NotFoundException.class);

    }

    @Test
    @DisplayName("update() Update Address")
    void update_UpdateAddress_WhenSuccessful() {

        var id = 1L;

        var addressToUpdate = addressUtils.newAddressList().get(0);

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(addressToUpdate));

        BDDMockito.when(repository.save(addressToUpdate)).thenReturn(addressToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(addressToUpdate));

    }

    @Test
    @DisplayName("update() throw NotFoundException when no address is found")
    void update_ThrowNotFoundException_WhenAddressNotFound() {

        var addressToUpdate = addressUtils.newAddressList().get(0);

        BDDMockito.when(repository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(addressToUpdate))
                .isInstanceOf(NotFoundException.class);

    }

}