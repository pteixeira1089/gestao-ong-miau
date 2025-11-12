package br.org.miauaumigos.backend.model.entity;

import br.org.miauaumigos.backend.model.enums.TipoFuncao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vinculos_funcionais")
public class VinculoFuncional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_id", nullable = false)
    @JsonBackReference //Evita loop infinito em serializações json: indica que a classe VinculoFuncional é a classe filha, e não deve serializar a classe Colaborador
    private Colaborador colaborador;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "tipo_funcao", nullable = false)
    private TipoFuncao tipoFuncao;
}
