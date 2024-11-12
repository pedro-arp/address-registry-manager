package devdojo.academy.cepconsult.controller;

import devdojo.academy.cepconsult.mapper.AddressMapper;
import devdojo.academy.cepconsult.request.AddressPutRequest;
import devdojo.academy.cepconsult.response.AddressGetResponse;
import devdojo.academy.cepconsult.response.AddressGetResponseCep;
import devdojo.academy.cepconsult.response.AddressPostResponse;
import devdojo.academy.cepconsult.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/address", "v1/address/"})
@RequiredArgsConstructor
@Log4j2
public class AddressController {

    private final AddressService service;

    private final AddressMapper mapper;

    @GetMapping
    public ResponseEntity<List<AddressGetResponse>> findAll() {

        log.info("Request received find all address in database ");

        var address = service.findAll();

        var response = mapper.toAddressList(address);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/search/{cep}")
    public ResponseEntity<AddressGetResponseCep> findByCep(@PathVariable String cep) {

        log.info("Request received find address by uriCep '{}'", cep);

        var address = service.findByCep(cep);

        var response = mapper.toAddressGetResponseCep(address);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<AddressGetResponse> findById(@PathVariable Long id) {
        log.info("Request received find address by id '{}'", id);

        var address = service.findById(id);

        var response = mapper.toAddressGetResponse(address);

        return ResponseEntity.ok(response);
    }

    @PostMapping("{cep}")
    public ResponseEntity<AddressPostResponse> save(@PathVariable("cep") @Valid String cep) {

        log.info("Request received to save address by uriCep '{}'", cep);

        var addressToSave = service.save(cep);

        var response = mapper.addressPostResponse(addressToSave);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        log.info("Request received to delete address by id '{}'", id);

        service.delete(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody AddressPutRequest request) {

        log.info("Request received to update address by id '{}'", request);

        var address = mapper.toAddress(request);

        service.update(address);

        return ResponseEntity.noContent().build();

    }

}
