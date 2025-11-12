package br.org.miauaumigos.backend.model.entity;

import br.org.miauaumigos.backend.model.enums.TipoEventoAnimal;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "evento_animal")
public class EventoAnimal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "tipo_evento_animal", nullable = false)
    private TipoEventoAnimal tipo;

    @Column(nullable = false)
    private LocalDate dataEvento;

    @Column
    private String obsEvento;

}
