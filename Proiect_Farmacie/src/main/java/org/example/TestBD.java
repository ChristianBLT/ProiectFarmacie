package org.example;

import jakarta.persistence.*;
import java.util.List;

public class TestBD {
    public static void main(String[] args) {
        // Creăm EntityManagerFactory și EntityManager pentru a lucra cu baza de date
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
        EntityManager em = emf.createEntityManager();

        // Începem o tranzacție
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            // Creăm un medicament
            Medicament medicament1 = new Medicament(1, "Paracetamol", "PharmaCorp", 100, 5.0);

            // Creăm o farmacie
            Farmacie farmacie = new Farmacie("Farmacia Sănătatea Ta", "Strada Principală, nr. 10", List.of(medicament1));

            // Asociem medicamentele cu farmacia
            medicament1.setFarmacie(farmacie);  // Setăm farmacia pentru medicament

            // Persistăm farmacie și medicament în baza de date
            em.persist(farmacie);
            em.persist(medicament1);

            // Confirmăm tranzacția (se va salva în baza de date)
            transaction.commit();
            System.out.println("Farmacie și medicament adăugate cu succes!");

            // Citește și afișează farmaciile din baza de date
            List<Farmacie> farmacii = em.createQuery("SELECT f FROM Farmacie f", Farmacie.class).getResultList();
            farmacii.forEach(f -> System.out.println(f.getNumeFarmacie() + ", " + f.getAdresa()));

            // Căutăm medicamentele
            Medicament medicamentGasit = em.find(Medicament.class, 1);
            if (medicamentGasit != null) {
                System.out.println("Medicament găsit: " + medicamentGasit.getNume());
            } else {
                System.out.println("Medicamentul nu a fost găsit.");
            }

            // Șterge medicamentul și farmacia

            System.out.println("Medicament și farmacie șterse cu succes!");

            // Confirmă operațiile
            transaction.begin();  // Reîncepem tranzacția pentru ștergere
            transaction.commit();

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Dacă apare o eroare, facem rollback
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
