package devdojo.academy.cepconsult.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import devdojo.academy.cepconsult.commons.AddressUtils;
import devdojo.academy.cepconsult.config.RestClientConfiguration;
import devdojo.academy.cepconsult.config.ViaCepApiConfigurationProperties;
import devdojo.academy.cepconsult.exception.NotFoundException;
import devdojo.academy.cepconsult.repository.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

@RestClientTest({AddressService.class, RestClientConfiguration.class, ViaCepApiConfigurationProperties.class, ObjectMapper.class, AddressUtils.class})
class AddressRestClientTest {
    @MockBean
    private AddressRepository repository;
    @Autowired
    private AddressUtils addressUtils;
    @Autowired
    private AddressService service;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private ViaCepApiConfigurationProperties properties;

    @AfterEach
    void reset() {
        server.reset();
    }


    @Test
    @DisplayName("findCep returns CepGetResponse when successful")
    void findCep_ReturnsCepGetResponse_WhenSuccessful() throws JsonProcessingException {
        var cep = "00000-200";
        var cepGetResponse = addressUtils.newAddress();
        var jsonResponse = mapper.writeValueAsString(cepGetResponse);
        var requestTo = MockRestRequestMatchers.requestToUriTemplate(properties.baseUrl() + properties.uriCep(), cep);
        var withSuccess = MockRestResponseCreators.withSuccess(jsonResponse, MediaType.APPLICATION_JSON);
        server.expect(requestTo).andRespond(withSuccess);

        Assertions.assertThat(service.findByCep(cep))
                .isNotNull()
                .isEqualTo(cepGetResponse);
    }

    @Test
    @DisplayName("findCep returns NotFoundException when Unsuccessful")
    void findCep_ReturnsCepGetResponse_WhenUnsuccessful() throws JsonProcessingException {
        var cep = "4040000";
        var cepErrorResponse = addressUtils.newAddress();
        var jsonResponse = mapper.writeValueAsString(cepErrorResponse);
        var expectedErrorMessage = """
                404 NOT_FOUND "CepErrorResponse[name=CepPromiseError, message=Todos os serviços de CEP retornaram erro., type=service_error, errors=[CepInnerErrorResponse[name=ServiceError, message=CEP INVÁLIDO, service=correios]]]"
                """.trim();
        var requestTo = MockRestRequestMatchers.requestToUriTemplate(properties.baseUrl() + properties.uriCep(), cep);
        var withResourceNotFound = MockRestResponseCreators.withResourceNotFound().body(jsonResponse);
        server.expect(requestTo).andRespond(withResourceNotFound);

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByCep(cep))
                .withMessage(expectedErrorMessage)
                .isInstanceOf(NotFoundException.class);
    }


    @Test
    @DisplayName("save() Creates Address in database")
    void save_CreatesAddress_WhenSuccessful() throws JsonProcessingException {

        var cep = "00000-200";
        var cepGetResponse = addressUtils.newAddress();
        var jsonResponse = mapper.writeValueAsString(cepGetResponse);
        var requestTo = MockRestRequestMatchers.requestToUriTemplate(properties.baseUrl() + properties.uriCep(), cep);
        var withSuccess = MockRestResponseCreators.withSuccess(jsonResponse, MediaType.APPLICATION_JSON);
        server.expect(requestTo).andRespond(withSuccess);

        BDDMockito.when(repository.save(cepGetResponse)).thenReturn(cepGetResponse);

        Assertions.assertThat(service.save(cep))
                .isNotNull()
                .isEqualTo(cepGetResponse);
    }

//    @Test
//    @DisplayName("save() Returns DuplicateEntryException when CEP is duplicated")
//    void save_ReturnsDuplicateEntryException_WhenCepIsDuplicated() {
//
//        var addressDuplicatedToSave = this.address.get(0);
//
//        var cep = addressDuplicatedToSave.getCep();
//
//        BDDMockito.when(service.findByCep(cep)).thenReturn(addressDuplicatedToSave);
//
//        BDDMockito.doThrow(DuplicateEntryException.class).when(repository).save(addressDuplicatedToSave);
//
//        Assertions.assertThatException()
//                .isThrownBy(() -> service.save(addressDuplicatedToSave.getCep()))
//                .isInstanceOf(DuplicateEntryException.class);
//
//    }


}