package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SistemFarmacie {
    private List<Utilizator> utilizatori;
    private List<Reteta> retete;
    private List<Farmacie> farmacii;


    public SistemFarmacie() {
        this.utilizatori = new ArrayList<>();
        this.retete = new ArrayList<>();
        this.farmacii = new ArrayList<>();
    }


    public void adaugaUtilizator(Utilizator utilizator) {
        utilizatori.add(utilizator);
        System.out.println("Utilizator adăugat: " + utilizator);
    }

    public void adaugaFarmacie(Farmacie farmacie) {
        farmacii.add(farmacie);
        System.out.println("Farmacie adăugată: " + farmacie);
    }
    public void adaugaReteta(Reteta reteta) {
        retete.add(reteta);
        System.out.println("Rețetă adăugată: " + reteta);
    }


    public Utilizator autentificare(String email, String parola) {
        for (Utilizator utilizator : utilizatori) {
            if (utilizator.getEmail().equalsIgnoreCase(email) && utilizator.getParola().equals(parola)) {
                System.out.println("Autentificare reușită pentru: " + utilizator.getDenumire());
                return utilizator;
            }
        }
        System.out.println("Autentificare eșuată. Email sau parolă incorecte.");
        return null;
    }


    public List<Medicament> cautaMedicament(String nume) {
        List<Medicament> rezultate = new ArrayList<>();
        for (Farmacie farmacie : farmacii) {
            List<Medicament> medicamenteGasite = farmacie.getListaMedicamente().stream()
                    .filter(med -> med.getNume().equalsIgnoreCase(nume))
                    .collect(Collectors.toList());
            rezultate.addAll(medicamenteGasite);
        }
        if (rezultate.isEmpty()) {
            System.out.println("Medicamentul " + nume + " nu a fost găsit.");
        } else {
            System.out.println("Medicamentul " + nume + " găsit în farmaciile disponibile.");
        }
        return rezultate;
    }

    
    public void raportActivitate() {
        System.out.println("Raport de activitate:");
        System.out.println("- Număr utilizatori: " + utilizatori.size());
        System.out.println("- Număr rețete: " + retete.size());
        System.out.println("- Număr farmacii: " + farmacii.size());
        System.out.println();
        System.out.println("Lista utilizatorilor:");
        utilizatori.forEach(System.out::println);

        System.out.println("\nLista rețetelor:");
        retete.forEach(System.out::println);

        System.out.println("\nLista farmaciilor:");
        farmacii.forEach(System.out::println);
    }
}