package net.stawrul.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Klasa encyjna reprezentująca towar w sklepie (książkę).
 */
@Entity
@Table(name = "Cd")
@EqualsAndHashCode(of = "id")
@NamedQueries(value = {
        @NamedQuery(name = Cd.FIND_ALL, query = "SELECT b FROM Cd b")
})
public class Cd extends Product {
    public static final String FIND_ALL = "Cd.FIND_ALL";

    @Getter
    @Setter
    String author;

    @Getter
    @Setter
    Integer songs;

    @Getter
    @Setter
    String genre;

}
