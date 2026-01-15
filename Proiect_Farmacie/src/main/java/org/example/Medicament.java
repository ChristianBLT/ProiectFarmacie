package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity


public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private int idMedicament;
    private String nume;
    private String producator;
    private int stocDisponibil;
    private double pret;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "farmacie_id", nullable = false)
    private Farmacie farmacie;

    public boolean esteinstoc(){
        return stocDisponibil>0;
    }

    public String toString() {
        return "Medicament {" +
                "ID=" + idMedicament +
                ", Nume='" + nume + '\'' +
                ", Producător='" + producator + '\'' +
                ", Stoc=" + stocDisponibil +
                ", Preț=" + pret +
                '}';
    }

    public Medicament(int idMedicament, String nume, String producator, int stocDisponibil, double pret) {
        this.idMedicament = idMedicament;
        this.nume = nume;
        this.producator = producator;
        this.stocDisponibil = stocDisponibil;
        this.pret = pret;
    }






}
