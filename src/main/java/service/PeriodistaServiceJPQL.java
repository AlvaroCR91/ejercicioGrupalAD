package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import model.Periodista;

import java.util.List;

public class PeriodistaServiceJPQL {
    private EntityManagerFactory emf;

    public PeriodistaServiceJPQL(){
        emf = Persistence.createEntityManagerFactory("PeriodistaPU");
    }

    public void CrearInformeNingunArticulo(){
        EntityManager em = emf.createEntityManager();

        try{
            TypedQuery<Periodista> query = em.createQuery("SELECT p FROM Periodista p LEFT JOIN p.articulos a GROUP BY p.nombre ", Periodista.class);
            List<Periodista> periodistas = query.getResultList();

        }
    }
}
