package br.org.miauaumigos.backend.model.entity;

import br.org.miauaumigos.backend.model.enums.EspecieAnimal;

import java.util.List;

public class Animal {
    private Long id;
    private String nome;
    private EspecieAnimal especieAnimal;
    private Character sexo;
    private String pelagem;
    private String bio;
    private String urlFoto;
    private List<EventoAnimal> eventos;
}
