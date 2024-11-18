package devdojo.academy.cepconsult.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressPutRequest {
    @NotNull
    private Long id;
    @NotBlank(message = "the field 'uriCep' is required")
    private String cep;
    @NotBlank(message = "the field 'city' id required")
    private String city;
    @NotBlank(message = "the field 'neighborhood' id required")
    private String neighborhood;
    @NotBlank(message = "the field 'street' id required")
    private String street;
    @NotBlank(message = "the field 'state' id required")
    private String state;

}
