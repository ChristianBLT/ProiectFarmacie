package org.example;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class MedicRepository {
    private EntityManagerFactory emf;

    public MedicRepository() {
        this.emf = Persistence.createEntityManagerFactory("ProduseJPA");
    }


    public void save(Medic medic) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (medic.getId() == 0) {
                em.persist(medic);
            } else {
                em.merge(medic);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


    public Medic findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Medic.class, id);
        } finally {
            em.close();
        }
    }


    public List<Medic> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM Medic m", Medic.class).getResultList();
        } finally {
            em.close();
        }
    }


    public List<Medic> findBySpecializare(String specializare) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM Medic m WHERE m.specializre = :specializare", Medic.class)
                    .setParameter("specializare", specializare)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Reteta> findReteteByMedicId(Integer medicId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Reteta r WHERE r.medic.id = :medicId", Reteta.class)
                    .setParameter("medicId", medicId)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public void deleteById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Medic medic = em.find(Medic.class, id);
            if (medic != null) {
                em.remove(medic);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
