package devdojo.academy.cepconsult.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String cep;
    @Column(nullable = false)
    private String logradouro;
    private String complemento;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String localidade;
    @Column(nullable = false)
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
}
