package org.example;

import java.util.List;
public class MedicamentRepository_Test {
    public static void main(String[] args) {
        MedicamentRepository medicamentRepository = new MedicamentRepository();

        // Test: Salvare medicament nou
        Medicament medicamentNou = new Medicament(9, "Antibiotic", "PharmaPlus", 80, 25.0);

        medicamentRepository.save(medicamentNou);
        System.out.println("TEST: Medicament salvat cu succes!");

        // Test: Afișare toate medicamentele
        List<Medicament> toateMedicamentele = medicamentRepository.findAll();
        System.out.println("\nTEST: Toate medicamentele din baza de date:");
        toateMedicamentele.forEach(System.out::println);


        String farmacieCautata = "Farmacia Sănătatea Ta";
        List<Medicament> medicamenteFarmacie = medicamentRepository.findByFarmacie(farmacieCautata);
        System.out.println("\nTEST: Medicamente din " + farmacieCautata + ":");
        medicamenteFarmacie.forEach(System.out::println);


        //int pragStoc = 50;
        //List<Medicament> medicamenteLowStock = medicamentRepository.findLowStock(pragStoc);
        //System.out.println("\nTEST: Medicamente cu stoc mai mic de " + pragStoc + ":");
        //medicamenteLowStock.forEach(System.out::println);

        // Test: Ștergere medicament după ID
        int idDeSters = 3;
        medicamentRepository.deleteById(idDeSters);
        System.out.println("\nTEST: Medicamentul cu ID-ul " + idDeSters + " a fost șters.");
    }
}
