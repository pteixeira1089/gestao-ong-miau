package br.org.miauaumigos.backend.model.entity;

import br.org.miauaumigos.backend.model.enums.TipoEventoAnimal;

import java.time.LocalDate;

public class EventoAnimal {
    private Long id;
    private Animal animal;
    private TipoEventoAnimal tipo;
    private LocalDate data;
    private String descricao;

}
