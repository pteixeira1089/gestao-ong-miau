package br.org.miauaumigos.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String logradouro;

    @Column(nullable = false, length = 10)
    private String numero;

    @Column(nullable = true, length = 100)
    private String complemento;

    @Column(nullable = true, length = 200)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false, length = 10)
    private String cep;

    @OneToMany(mappedBy = "endereco")
    @JsonIgnore
    private Set<Adotante> adotantes = new HashSet<>();
}
