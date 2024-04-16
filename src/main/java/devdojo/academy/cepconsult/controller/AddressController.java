package devdojo.academy.cepconsult.controller;

import devdojo.academy.cepconsult.mapper.AddressMapper;
import devdojo.academy.cepconsult.response.AddressGetResponse;
import devdojo.academy.cepconsult.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"v1/address", "v1/address/"})
@RequiredArgsConstructor
@Log4j2
public class AddressController {

    private final AddressService service;

    private final AddressMapper mapper;


    @GetMapping("{cep}")
    public ResponseEntity<AddressGetResponse> findByCep(@PathVariable("cep") String cep) {

        log.info("Request received find address by cep '{}'", cep);

        var address = service.findByCep(cep);

        var response = mapper.toAddressGetResponse(address);

        return ResponseEntity.ok(response);
    }

}
