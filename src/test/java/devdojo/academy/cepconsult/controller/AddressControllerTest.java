package devdojo.academy.cepconsult.controller;

import devdojo.academy.cepconsult.commons.AddressUtils;
import devdojo.academy.cepconsult.commons.FileUtils;
import devdojo.academy.cepconsult.exception.DuplicateEntryException;
import devdojo.academy.cepconsult.exception.NotFoundException;
import devdojo.academy.cepconsult.service.AddressService;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AddressController.class)
@Import({AddressUtils.class, FileUtils.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressControllerTest {
    private static final String URL = "/v1/address";
    private static final String ADDRESS_NOT_FOUND = "Address not found";
    private static final String ADDRESS_DUPLICATE = "Address %s is already in use";

    @MockBean
    private AddressService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AddressUtils addressUtils;
    @Autowired
    private FileUtils fileUtils;

    @Test
    @DisplayName("findAll() returns 'all' addresses in data base")
    @Order(1)
    void findAll_ReturnsAllAddresses_WhenSuccessful() throws Exception {

        var allAddress = addressUtils.newAddressList();

        var response = fileUtils.readResourceFile("get/find-all-address-200.json");

        BDDMockito.when(service.findAll()).thenReturn(allAddress);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("findByCep() returns the address according to the 'CEP'")
    @Order(2)
    void findByCep_ReturnAddress_WhenSuccessful() throws Exception {

        var address = addressUtils.newAddressList().getFirst();

        var cep = address.getCep();

        var response = fileUtils.readResourceFile("get/find-address-by-cep-200.json");

        BDDMockito.when(service.findByCep(cep)).thenReturn(address);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/search/{cep}", cep))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("findByCep() return 'null' fields when address does not exist yet")
    @Order(3)
    void findByCep_ReturnAddressNullFields_WhenAddresDoesNotExistYet() throws Exception {

        var cep = "00000-000";

        var address = addressUtils.addressNullFields();

        var response = fileUtils.readResourceFile("get/find-address-by-cep-null-fields-200.json");

        BDDMockito.when(service.findByCep(cep)).thenReturn(address);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/search/{cep}", cep))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("findById() returns the address according to the 'id'")
    @Order(4)
    void findById_ReturnAddress_WhenSuccessful() throws Exception {

        var address = addressUtils.newAddressList().getFirst();

        var id = 1L;

        var response = fileUtils.readResourceFile("get/find-address-by-id-200.json");

        BDDMockito.when(service.findById(id)).thenReturn(address);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("findById() throw NotFoundException when no address is found")
    @Order(5)
    void findById_ReturnNotFoundException_WhenAddressNotFound() throws Exception {

        var idNotFound = 999L;

        var response = fileUtils.readResourceFile("response-not-found-address-by-id-404.json");

        BDDMockito.when(service.findById(ArgumentMatchers.any())).thenThrow(new NotFoundException(ADDRESS_NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", idNotFound))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("save() Creates Address in database")
    @Order(6)
    void save_CreatesAddress_WhenSuccessful() throws Exception {

        var address = addressUtils.newAddressList().getFirst();

        var cep = "00000-001";

        var response = fileUtils.readResourceFile("post/save-address-200.json");

        BDDMockito.when(service.save(cep)).thenReturn(address);

        mockMvc.perform(MockMvcRequestBuilders.post(URL + "/{cep}", cep))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("save() Returns DuplicateEntryException when CEP is duplicated")
    @Order(7)
    void save_ReturnsDuplicateEntryException_WhenCepIsDuplicated() throws Exception {

        var addressDuplicatedToSave = addressUtils.newAddressList().getFirst();

        var cep = addressDuplicatedToSave.getCep();

        var response = fileUtils.readResourceFile("post/save-address-duplicated-404.json");

        BDDMockito.doThrow(new DuplicateEntryException(ADDRESS_DUPLICATE)).when(service).save(cep);

        mockMvc.perform(MockMvcRequestBuilders.post(URL + "/{cep}", cep))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("delete() Removes address in database")
    @Order(8)
    void delete_RemovesAddress_WhenSuccessful() throws Exception {

        BDDMockito.doNothing().when(service).delete(ArgumentMatchers.any());

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    @DisplayName("delete() throw NotFoundException when no address is found")
    void delete_ThrowNotFoundException_WhenAddressNotFound() throws Exception {

        var response = fileUtils.readResourceFile("response-not-found-address-by-id-404.json");

        BDDMockito.doThrow(new NotFoundException(ADDRESS_NOT_FOUND)).when(service).delete(ArgumentMatchers.any());

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @DisplayName("update() Update Address")
    void update_UpdateAddress_WhenSuccessful() throws Exception {

        var request = fileUtils.readResourceFile("put/update-address-200.json");

        BDDMockito.doNothing().when(service).update(ArgumentMatchers.any());

        mockMvc.perform(MockMvcRequestBuilders.put(URL).content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("update() throw NotFoundException when no address is found")
    void update_ThrowNotFoundException_WhenAddressNotFound() throws Exception {

        var request = fileUtils.readResourceFile("put/update-address-200.json");

        var response = fileUtils.readResourceFile("response-not-found-address-by-id-404.json");

        BDDMockito.doThrow(new NotFoundException(ADDRESS_NOT_FOUND)).when(service).update(ArgumentMatchers.any());

        mockMvc.perform(MockMvcRequestBuilders.put(URL).content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }


}