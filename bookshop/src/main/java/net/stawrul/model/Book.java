package net.stawrul.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

/**
 * Klasa encyjna reprezentująca towar w sklepie (książkę).
 */
@Entity
@Table(name = "Book")
@EqualsAndHashCode(of = "id")
@NamedQueries(value = {
        @NamedQuery(name = Book.FIND_ALL, query = "SELECT b FROM Book b"),
})
public class Book extends Product {
    public static final String FIND_ALL = "Book.FIND_ALL";



    @Getter
    @Setter
    String publisher;

    @Getter
    @Setter
    String author;

}
