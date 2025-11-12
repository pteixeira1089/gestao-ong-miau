package br.org.miauaumigos.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "colaboradores")
public class Colaborador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 100)
    private String telefone;

    @Column(name= "url_foto")
    private String urlFoto;

    @Column(length = 2000)
    private String bio;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "colaboradores_roles",
            joinColumns = @JoinColumn(name = "colaborador_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Evita loop infinito no JSON
    private Set<VinculoFuncional> vinculosFuncionais = new HashSet<>();


}
