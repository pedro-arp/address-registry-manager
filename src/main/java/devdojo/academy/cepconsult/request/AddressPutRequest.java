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
    @NotBlank(message = "the field 'cep' is required")
    private String cep;
    @NotBlank(message = "the field 'logradouro' id required")
    private String logradouro;
    private String complemento;
    @NotBlank(message = "the field 'bairro' id required")
    private String bairro;
    @NotBlank(message = "the field 'localidade' id required")
    private String localidade;
    @NotBlank(message = "the field 'uf' id required")
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
}
