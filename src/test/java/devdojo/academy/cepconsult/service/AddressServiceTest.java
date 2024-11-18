package devdojo.academy.cepconsult.service;

import devdojo.academy.cepconsult.commons.AddressUtils;
import devdojo.academy.cepconsult.exception.NotFoundException;
import devdojo.academy.cepconsult.repository.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository repository;

    @InjectMocks
    private AddressUtils addressUtils;

    @InjectMocks
    private AddressService service;


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
    @DisplayName("findById() returns the address according to the 'id'")
    void findById_ReturnAddress_WhenSuccessful() {

        var addressFound = addressUtils.newAddressList().getFirst();

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
    @DisplayName("delete() Removes address in database")
    void delete_RemovesAddress_WhenSuccessful() {

        var id = 1L;

        var addressToDelete = addressUtils.newAddressList().getFirst();

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

        var addressToUpdate = addressUtils.newAddressList().getFirst();

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(addressToUpdate));

        BDDMockito.when(repository.save(addressToUpdate)).thenReturn(addressToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(addressToUpdate));

    }

    @Test
    @DisplayName("update() throw NotFoundException when no address is found")
    void update_ThrowNotFoundException_WhenAddressNotFound() {

        var addressToUpdate = addressUtils.newAddressList().getFirst();

        BDDMockito.when(repository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(addressToUpdate))
                .isInstanceOf(NotFoundException.class);

    }

}