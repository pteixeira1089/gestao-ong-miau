package br.org.miauaumigos.backend.model.entity;

import br.org.miauaumigos.backend.model.enums.EspecieAnimal;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "especie_animal", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private EspecieAnimal especieAnimal;

    @Column(nullable = false, length = 1)
    private Character sexo;

    @Column(nullable = false)
    private String pelagem;

    @Column(nullable = true, length = 2000)
    private String bio;

    @Column(nullable = true)
    private String urlFoto;

    @CreationTimestamp // O Hibernate preenche automaticamente ao salvar
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoAnimal> eventos;
}
