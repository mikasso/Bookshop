package net.stawrul.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Film")
@EqualsAndHashCode(of = "id")
@NamedQueries(value = {
        @NamedQuery(name = Film.FIND_ALL, query = "SELECT b FROM Film b")
})
public class Film extends Product {
    public static final String FIND_ALL = "Film.FIND_ALL";

    @Getter
    @Setter
    String director;

    @Getter
    @Setter
    String duration;

    @Getter
    @Setter
    String genre;

}
