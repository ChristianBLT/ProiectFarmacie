package org.example;
import jakarta.persistence.*;
import org.example.Reteta;
import org.example.Utilizator;

import java.util.*;
@Entity

public class Pacient extends Utilizator {


    private String dataNasterii;
    private String istoricMedical;

    @OneToMany(mappedBy = "pacient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reteta> listaRetete = new ArrayList<>();

    public Pacient() {
    }

    public String getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(String dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public String getIstoricMedical() {
        return istoricMedical;
    }

    public void setIstoricMedical(String istoricMedical) {
        this.istoricMedical = istoricMedical;
    }

    public List<Reteta> getListaRetete() {
        return listaRetete;
    }

    public void setListaRetete(List<Reteta> listaRetete) {
        this.listaRetete = listaRetete;
    }

    public Pacient(int id, String denumire, String email, String parola, String rol, String dataNasterii, String istoricMedical) {
        super(id, denumire, email, parola, rol);
        this.dataNasterii = dataNasterii;
        this.istoricMedical = istoricMedical;
    }

    @Override
    public void descriereRol()
    {
        System.out.println("Rol: Pacient - Data nașterii: " + dataNasterii + " - Istoric Meddical " + istoricMedical);
    }
    public void adaugaReteta(Reteta reteta) {
        reteta.setPacient(this);
        this.listaRetete.add(reteta);
    }
}
