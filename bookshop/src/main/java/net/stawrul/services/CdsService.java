package net.stawrul.services;

import net.stawrul.model.Cd;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class CdsService extends EntityService<Cd> {

    public CdsService(EntityManager em) {

        super(em, Cd.class, Cd::getId);
    }

    public List<Cd> findAll() {
        return em.createNamedQuery(Cd.FIND_ALL, Cd.class).getResultList();
    }

}
