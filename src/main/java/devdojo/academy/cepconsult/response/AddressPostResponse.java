package devdojo.academy.cepconsult.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressPostResponse {
    private Long id;
    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String service;
}
