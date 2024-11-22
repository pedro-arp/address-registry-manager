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
    private String street;
    @Column(nullable = false)
    private String neighborhood;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    private String service;


}
