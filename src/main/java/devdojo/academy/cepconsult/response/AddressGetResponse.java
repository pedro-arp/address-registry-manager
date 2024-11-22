package devdojo.academy.cepconsult.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressGetResponse {
    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String service;
}
