package br.org.miauaumigos.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adocao")
@PrimaryKeyJoinColumn(name = "evento_id")
public class Adocao extends EventoAnimal {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adotante_id", nullable = false)
    private Adotante adotante;

}
