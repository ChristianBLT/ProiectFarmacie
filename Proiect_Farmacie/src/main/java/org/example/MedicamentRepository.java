package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class MedicamentRepository {
    private EntityManagerFactory emf;

    public MedicamentRepository() {
        this.emf = Persistence.createEntityManagerFactory("ProduseJPA");
    }


    public void save(Medicament medicament) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(medicament);
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


    public Medicament findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Medicament.class, id);
        } finally {
            em.close();
        }
    }


    public List<Medicament> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM Medicament m", Medicament.class).getResultList();
        } finally {
            em.close();
        }
    }


    public List<Medicament> findByFarmacie(String numeFarmacie) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM Medicament m WHERE m.farmacie.numeFarmacie = :numeFarmacie", Medicament.class)
                    .setParameter("numeFarmacie", numeFarmacie)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public List<Medicament> findLowStock(int prag) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM Medicament m WHERE m.stocdisponibil < :prag", Medicament.class)
                    .setParameter("prag", prag)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public void deleteById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Medicament medicament = em.find(Medicament.class, id);
            if (medicament != null) {
                em.remove(medicament);
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
