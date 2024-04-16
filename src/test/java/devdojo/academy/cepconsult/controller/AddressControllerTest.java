package devdojo.academy.cepconsult.controller;

import devdojo.academy.cepconsult.commons.AddressUtils;
import devdojo.academy.cepconsult.commons.FileUtils;
import devdojo.academy.cepconsult.mapper.AddressMapperImpl;
import devdojo.academy.cepconsult.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AddressController.class)
@Import({AddressMapperImpl.class, AddressUtils.class, FileUtils.class})
class AddressControllerTest {
    private static final String URL = "/v1/address";
    @MockBean
    private AddressService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AddressUtils addressUtils;
    @Autowired
    private FileUtils utils;

    @Test
    @DisplayName("findByCep() returns the address according to the CEP")
    void findByCep_ReturnAddress_WhenSuccessful() throws Exception {

        var address = addressUtils.newAddressList().get(0);

        var cep = address.getCep();

        var response = utils.readResourceFile("get-find-by-cep-200.json");

        BDDMockito.when(service.findByCep(cep)).thenReturn(address);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{cep}", cep))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("findByCep() return null fields when address does not exist yet")
    void findByCep_ReturnAddressNullFields_WhenAddresDoesNotExistYet() throws Exception {

        var cep = "00000-000";

        var address = addressUtils.addressNullFields();

        var response = utils.readResourceFile("get-find-by-cep-null-fields-200.json");

        BDDMockito.when(service.findByCep(cep)).thenReturn(address);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{cep}", cep))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }
}