package org.example;
import java.util.List;
public class MedicRepository_Test {
    public static void main(String[] args) {
        MedicRepository medicRepository = new MedicRepository();

        // Test: Salvare medic nou
        Medic medicNou = new Medic();
        medicNou.setId(6); // ID-ul manual
        medicNou.setDenumire("Balta Christian");
        medicNou.setEmail("balta.christian@example.com");
        medicNou.setParola("parola123");
        medicNou.setRol("Medic");
        medicNou.setSpecializare("Cardiologie");

        medicRepository.save(medicNou);
        System.out.println("TEST: Medic salvat cu succes!");

        // Test: Afișare toți medicii
        List<Medic> totiMedicii = medicRepository.findAll();
        System.out.println("\nTEST: Toți medicii din baza de date:");
        totiMedicii.forEach(System.out::println);

        // Test: Găsire medic după ID
        Medic medicGasit = medicRepository.findById(6);
        System.out.println("\nTEST: Medic găsit după ID:");
        System.out.println(medicGasit);
    }
}
