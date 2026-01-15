package org.example;

import java.util.List;
import java.util.ArrayList;

public class Testari {

    public static void main(String[] args) {
    Medicament medicament1 = new Medicament(1, "Paracetamol", "PharmaCorp", 100, 5.0);
    Medicament medicament2 = new Medicament(2, "Ibuprofen", "HealthPlus", 50, 10.0);

    // Inițializare listă medicamente
    List<Medicament> listaMedicamente = new ArrayList<>();
        listaMedicamente.add(medicament1);
        listaMedicamente.add(medicament2);

    // Inițializare farmacie
    Farmacie farmacie = new Farmacie("Farmacia Sănătatea Ta", "Strada Principală, nr. 10", listaMedicamente);

    // Adăugare medicamente (opțional, dacă lista e deja inițializată în constructor)
        farmacie.adaugaMedicament(medicament1);
        farmacie.adaugaMedicament(medicament2);

    // Afișare inventar
        System.out.println("Inventar Farmacie:");
        farmacie.afiseazaInventar();
}



}
