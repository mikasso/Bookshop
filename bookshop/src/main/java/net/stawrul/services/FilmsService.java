package net.stawrul.services;

import net.stawrul.model.Film;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;


@Service
public class FilmsService extends EntityService<Film> {

    public FilmsService(EntityManager em) {
        super(em, Film.class, Film::getId);
    }


    public List<Film> findAll() {
        return em.createNamedQuery(Film.FIND_ALL, Film.class).getResultList();
    }

}
