package br.org.miauaumigos.backend.model.entity;

import br.org.miauaumigos.backend.model.enums.TipoEventoAnimal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "evento_padrao")
@PrimaryKeyJoinColumn(name = "evento_id")
public class EventoPadrao extends EventoAnimal {

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "tipo_evento_animal", nullable = false)
    private TipoEventoAnimal tipo;

    public EventoPadrao(TipoEventoAnimal tipo) {
        this.tipo = tipo;
    }
}
