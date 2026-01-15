package org.example;
import lombok.*;
import java.util.List;
import jakarta.persistence.*;
import org.example.Medicament;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity



public class Farmacie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeFarmacie;
    private String adresa;

    @OneToMany(mappedBy = "farmacie", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Medicament> listaMedicamente;



    public void adaugaMedicament(Medicament medicament)
    {
        medicament.setFarmacie(this);
        this.listaMedicamente.add(medicament);
    }
    public void afiseazaInventar(){
        if(listaMedicamente.isEmpty()){
            System.out.println("Nu exista medicamente");
        }
        else {
            listaMedicamente.forEach(medicament -> System.out.println(medicament.getNume() + " Stoc:" + medicament.getStocDisponibil() + " Pret:" + medicament.getPret()));
        }
    }
    public Medicament cautaMedicament(String nume)
    {
        return listaMedicamente.stream().filter(medicament -> medicament.getNume().equalsIgnoreCase(nume)).findFirst().orElse(null);
    }

    public void actualizeazaStoc(String nume,int cantitatenoua)
    {
        Medicament medicament = cautaMedicament(nume);
        if(medicament !=null){
            medicament.setStocDisponibil(cantitatenoua);
        }
        else{
            System.out.println("Nu exista medicamentul in inventar");
        }
    }
    public Farmacie(String numeFarmacie, String adresa, List<Medicament> listaMedicamente) {
        this.numeFarmacie = numeFarmacie;
        this.adresa = adresa;
        this.listaMedicamente = listaMedicamente;
    }
}
