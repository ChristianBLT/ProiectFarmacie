package org.example;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Reteta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReteta;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pacient_id")
    private Pacient pacient;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "medic_id")
    private Medic medic;

    private String status;

    @OneToMany(mappedBy = "reteta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticolReteta> listaArticoleReteta;

    // Constructor personalizat (nu mai e nevoie de acest constructor dacă folosești @Data)
    public Reteta(int idReteta, Pacient pacient, Medic medic, String status, List<ArticolReteta> listaArticoleReteta) {
        this.idReteta = idReteta;
        this.pacient = pacient;
        this.medic = medic;
        this.status = status;
        this.listaArticoleReteta = listaArticoleReteta;
    }

    // Adăugarea unui articol de rețetă
    public void adaugaArticolReteta(ArticolReteta articolReteta) {
        if (this.status.equalsIgnoreCase("ACTIVA")) {
            listaArticoleReteta.add(articolReteta);
            System.out.println("Articolul de rețetă a fost adăugat: " + articolReteta);
        } else {
            System.out.println("Pentru a adăuga un articol de rețetă, rețeta trebuie să fie activă.");
        }
    }

    // Schimbarea statusului rețetei
    public void schimbastatus(String status) {
        if (status.equals("ACTIVA") || status.equals("ONORATA") || status.equals("ANULATA")) {
            this.status = status;
            System.out.println("Statusul rețetei a fost schimbat la: " + status);
        } else {
            System.out.println("Status invalid. Permis doar: ACTIVA, ONORATA, ANULATA.");
        }
    }
}
