package net.stawrul.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.MERGE;

/**
 * Klasa encyjna reprezentująca towar w sklepie (książkę).
 */
@Entity
@EqualsAndHashCode(of = "id")
public abstract class Product {

    @Getter
    @Id
    UUID id = UUID.randomUUID();

    @Getter
    @Setter
    Integer amount;

    @Getter
    @Setter
    String title;

    @Getter
    @Setter
    Integer price;
}
