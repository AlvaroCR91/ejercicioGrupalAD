package repository;

import java.io.Serializable;
import java.util.List;

import es.fempa.acd.NonexistentEntityException;
import es.fempa.acd.PreexistingEntityException;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Periodista;

public class PeriodistaDAO implements Serializable {

    public PeriodistaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Periodista periodista) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(periodista);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPeriodista(periodista.getDni()) != null) {
                throw new PreexistingEntityException("Periodista " + periodista + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Periodista periodista) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            periodista = em.merge(periodista);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = periodista.getDni();
                if (findPeriodista(id) == null) {
                    throw new NonexistentEntityException("The periodista with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Periodista periodista;
            try {
                periodista = em.getReference(Periodista.class, id);
                periodista.getDni();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The periodista with id " + id + " no longer exists.", enfe);
            }
            em.remove(periodista);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Periodista> findPeriodistaEntities() {
        return findPeriodistaEntities(true, -1, -1);
    }

    public List<Periodista> findPeriodistaEntities(int maxResults, int firstResult) {
        return findPeriodistaEntities(false, maxResults, firstResult);
    }

    private List<Periodista> findPeriodistaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Periodista> cq = em.getCriteriaBuilder().createQuery(Periodista.class);
            cq.select(cq.from(Periodista.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }


    public Periodista findPeriodista(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Periodista.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeriodistaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            Root<Periodista> rt = cq.from(Periodista.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Periodista> findPeriodistasSinArticulos_JPQL() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Periodista> query = em.createQuery(
                    "SELECT p FROM Periodista p WHERE p.articulos IS EMPTY",
                    Periodista.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    public List<Periodista> findPeriodistasMasDeDosArticulos_JPQL() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Periodista> query = em.createQuery(
                    "SELECT p FROM Periodista p WHERE SIZE(p.articulos) > 2",
                    Periodista.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
