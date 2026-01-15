package org.example;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class Test_BD2 {





        public static void main(String[] args) {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
            EntityManager em = emf.createEntityManager();
            MedicamentRepository medicamentRepository = new MedicamentRepository();


            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            try {

                Farmacie farmacie = new Farmacie("Farmacia Sănătatea Ta", "Strada Principală, nr. 10", new ArrayList<>());
                Farmacie farmacie1 = new Farmacie("Catena", "Strada Loc Stoicescu", new ArrayList<>());
                Farmacie farmacie2 = new Farmacie("Farmacie Dona", "Bulevardul Unirii, nr. 20", new ArrayList<>());
                Farmacie farmacie3 = new Farmacie("HelpNet", "Calea Victoriei, nr. 50", new ArrayList<>());
                Farmacie farmacie4 = new Farmacie("Sensiblu", "Strada Mihai Eminescu, nr. 15", new ArrayList<>());

                // Medicamente
                Medicament medicament1 = new Medicament(1, "Paracetamol", "PharmaCorp", 100, 5.0);
                Medicament medicament2 = new Medicament(2, "Ibuprofen", "MedCorp", 50, 7.5);
                Medicament medicament3 = new Medicament(3, "Aspirina", "Antibiotice", 45, 26);
                Medicament medicament4 = new Medicament(4, "Loperamid", "MedCorp", 50, 23);
                Medicament medicament5 = new Medicament(5, "Nurofen", "PharmaCorp", 60, 8.5);
                Medicament medicament6 = new Medicament(6, "Coldrex", "MedLife", 30, 15.0);
                Medicament medicament7 = new Medicament(7, "Panadol", "PharmaCorp", 40, 12.5);
                Medicament medicament8 = new Medicament(8, "Zyrtec", "AllergyCorp", 25, 18.0);

                farmacie.getListaMedicamente().add(medicament1);
                farmacie.getListaMedicamente().add(medicament2);
                farmacie1.getListaMedicamente().add(medicament3);
                farmacie1.getListaMedicamente().add(medicament4);
                farmacie2.getListaMedicamente().add(medicament5);
                farmacie3.getListaMedicamente().add(medicament6);
                farmacie3.getListaMedicamente().add(medicament7);
                farmacie4.getListaMedicamente().add(medicament8);

                medicament1.setFarmacie(farmacie);
                medicament2.setFarmacie(farmacie);
                medicament3.setFarmacie(farmacie1);
                medicament4.setFarmacie(farmacie1);
                medicament5.setFarmacie(farmacie2);
                medicament6.setFarmacie(farmacie3);
                medicament7.setFarmacie(farmacie3);
                medicament8.setFarmacie(farmacie4);

                // Pacienți
                Pacient pacient1 = new Pacient(1, "Ion Popescu", "ion.popescu@email.com", "parola123", "Pacient", "1990-05-20", "Alergie la penicilină");
                Pacient pacient2 = new Pacient(2, "Adrian Ceapcin", "adisorceapa@gmail.com", "Ioana", "Pacient", "2004-10-01", "Alergic la libertate");
                Pacient pacient3 = new Pacient(3, "Maria Ionescu", "maria.ionescu@gmail.com", "parola789", "Pacient", "1985-03-15", "Hipertensiune");
                Pacient pacient4 = new Pacient(4, "Andrei Georgescu", "andrei.g@gmail.com", "qwerty", "Pacient", "1978-07-10", "Diabet");
                Pacient pacient5 = new Pacient(5, "Ana Vasilescu", "ana.vasilescu@gmail.com", "pass123", "Pacient", "1995-09-25", "Niciuna");

                // Medici
                Medic medic1 = new Medic(1, "Dr. Maria Ionescu", "maria.ionescu@medic.com", "parola456", "Medic", "Medicină generală");
                Medic medic2 = new Medic(2, "Petrea Efros", "petrika@gmail.com", "zaibit", "Medic", "Medicină dentară");
                Medic medic3 = new Medic(3, "Dr. Andrei Popescu", "andrei.popescu@medic.com", "passmedic", "Medic", "Pediatrie");
                Medic medic4 = new Medic(4, "Dr. Elena Vasilescu", "elena.vasilescu@medic.com", "paroladr", "Medic", "Cardiologie");
                Medic medic5 = new Medic(5, "Dr. Mihai Georgescu", "mihai.georgescu@medic.com", "doc2023", "Medic", "Ortopedie");

                // Farmaciști
                Farmacist farmacist1 = new Farmacist(1, "Ana Georgescu", "ana.georgescu@farmacie.com", "parola789", "Farmacist", farmacie.getNumeFarmacie());
                Farmacist farmacist2 = new Farmacist(2, "Silviu Catan", "catanS@gmail.com", "vin", "Farmacist", farmacie1.getNumeFarmacie());
                Farmacist farmacist3 = new Farmacist(3, "Laura Popa", "laura.popa@farmacie.com", "farmacy123", "Farmacist", farmacie2.getNumeFarmacie());
                Farmacist farmacist4 = new Farmacist(4, "Ion Dobre", "ion.dobre@farmacie.com", "pharma2023", "Farmacist", farmacie3.getNumeFarmacie());
                Farmacist farmacist5 = new Farmacist(5, "Maria Vasilescu", "maria.vasilescu@farmacie.com", "securepass", "Farmacist", farmacie4.getNumeFarmacie());

                // Rețete
                Reteta reteta1 = new Reteta(1, pacient1, medic1, "Eliberată", new ArrayList<>());
                Reteta reteta2 = new Reteta(2, pacient2, medic2, "În așteptare", new ArrayList<>());
                Reteta reteta3 = new Reteta(3, pacient3, medic3, "Eliberată", new ArrayList<>());
                Reteta reteta4 = new Reteta(4, pacient4, medic4, "Anulată", new ArrayList<>());
                Reteta reteta5 = new Reteta(5, pacient5, medic5, "În proces", new ArrayList<>());

                // Articole rețete
                ArticolReteta articol1 = new ArticolReteta(1, medicament1, 2, reteta1);
                ArticolReteta articol2 = new ArticolReteta(2, medicament2, 1, reteta2);
                ArticolReteta articol3 = new ArticolReteta(3, medicament3, 3, reteta3);
                ArticolReteta articol4 = new ArticolReteta(4, medicament4, 1, reteta4);
                ArticolReteta articol5 = new ArticolReteta(5, medicament5, 2, reteta5);

                reteta1.getListaArticoleReteta().add(articol1);
                reteta2.getListaArticoleReteta().add(articol2);
                reteta3.getListaArticoleReteta().add(articol3);
                reteta4.getListaArticoleReteta().add(articol4);
                reteta5.getListaArticoleReteta().add(articol5);
                List<Medicament> toateMedicamentele = medicamentRepository.findAll();
                System.out.println("\nTEST: Toate medicamentele din baza de date:");
                toateMedicamentele.forEach(System.out::println);





                em.persist(farmacie);
                em.persist(farmacie1);
                em.persist(farmacie2);
                em.persist(farmacie3);
                em.persist(farmacie4);

                em.persist(medicament1);
                em.persist(medicament2);
                em.persist(medicament3);
                em.persist(medicament4);
                em.persist(medicament5);
                em.persist(medicament6);
                em.persist(medicament7);
                em.persist(medicament8);

                em.persist(pacient1);
                em.persist(pacient2);
                em.persist(pacient3);
                em.persist(pacient4);
                em.persist(pacient5);

                em.persist(medic1);
                em.persist(medic2);
                em.persist(medic3);
                em.persist(medic4);
                em.persist(medic5);

                em.persist(farmacist1);
                em.persist(farmacist2);
                em.persist(farmacist3);
                em.persist(farmacist4);
                em.persist(farmacist5);

                em.persist(reteta1);
                em.persist(reteta2);
                em.persist(reteta3);
                em.persist(reteta4);
                em.persist(reteta5);

                em.persist(articol1);
                em.persist(articol2);
                em.persist(articol3);
                em.persist(articol4);
                em.persist(articol5);


                transaction.commit();
                System.out.println("Datele au fost adăugate cu succes în baza de date!");
               // int idDeSters = 5;
               // medicamentRepository.deleteById(idDeSters);System.out.println("\nTEST: Medicamentul cu ID-ul " + idDeSters + " a fost șters.");

            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally {
                em.close();
                emf.close();
            }
        }
    }


