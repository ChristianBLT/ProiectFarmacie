package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class FarmacieRepository {
    private EntityManagerFactory emf;

    public FarmacieRepository() {
        this.emf = Persistence.createEntityManagerFactory("ProduseJPA");
    }


    public void save(Farmacie farmacie) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (farmacie.getId() == 0) {
                em.persist(farmacie);
            } else {
                em.merge(farmacie);
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


    public Farmacie findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Farmacie.class, id);
        } finally {
            em.close();
        }
    }


    public List<Farmacie> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT f FROM Farmacie f", Farmacie.class).getResultList();
        } finally {
            em.close();
        }
    }


    public List<Farmacie> findByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT f FROM Farmacie f WHERE f.numeFarmacie LIKE :name", Farmacie.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public void deleteById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Farmacie farmacie = em.find(Farmacie.class, id);
            if (farmacie != null) {
                em.remove(farmacie);
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