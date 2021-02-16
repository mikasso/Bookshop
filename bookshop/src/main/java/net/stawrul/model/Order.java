package net.stawrul.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Klasa encyjna reprezentująca zamówienie w sklepie.
 */
@Entity
@Table(name = "orders")
@EqualsAndHashCode(of = "id")
public class Order {

    @Getter
    @Id
    UUID id = UUID.randomUUID();

    @Getter
    @ManyToMany(cascade = {MERGE})
    List<Book> orderedBooks = new ArrayList<Book>();

    @Getter
    @ManyToMany(cascade = {MERGE})
    List<Film>  orderedFilms = new ArrayList<Film>();

    @Getter
    @ManyToMany(cascade = {MERGE})
    List<Cd>  orderedCds = new ArrayList<Cd>();

    @Getter
    @Temporal(TIMESTAMP)
    Date creationDate;

    /**
     * Ustawienie pola creationDate na aktualny czas w chwili zapisu zamówienia
     * do bazy danych.
     */
    @PrePersist
    public void prePersist(){
        this.creationDate = new Date();
    }
}
