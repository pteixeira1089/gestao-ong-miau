package br.org.miauaumigos.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="adotantes")
public class Adotante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String nome;
    
    @Column(nullable = true, length = 100)
    private String telefone;
    
    @Column(nullable = true, length = 500)
    private String email;
    
    @Column(nullable = false, length = 255)
    private String documentoIdentificacao;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
    
    @Column(nullable = true, length = 2000)
    private String observacoes;
    
    @Column(nullable = true, length = 2000)
    private String fotoUrl;
}
