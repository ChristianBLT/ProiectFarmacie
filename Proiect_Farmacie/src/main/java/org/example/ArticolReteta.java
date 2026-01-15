package org.example;
import lombok.*;
import jakarta.persistence.*;
import org.example.Medicament;
import org.example.Reteta;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class ArticolReteta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idArticol;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "medicament_id", nullable = false)
    private Medicament medicament;

    private int cantitate;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "reteta_id")
    private Reteta reteta;

    public ArticolReteta(Medicament medicament, int cantitate) {
        this.medicament = medicament;
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "ArticolReteta{" +
                "Medicament=" + medicament.getNume() +
                ", Cantitate=" + cantitate +
                '}';
    }
}
