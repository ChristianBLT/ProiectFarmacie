package org.example;
import jakarta.persistence.*;

import java.util.*;
@Entity

public class Medic extends Utilizator {
    private String specializare;

    @OneToMany(mappedBy = "medic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reteta> listaRetete = new ArrayList<>();

    public Medic() {
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public List<Reteta> getListaRetete() {
        return listaRetete;
    }


    public void setListaRetete(List<Reteta> listaRetete) {
        this.listaRetete = listaRetete;
    }

    public Medic(Integer id, String denumire, String email, String parola, String rol, String specializare) {
        super(id, denumire, email, parola, rol);
        this.specializare = specializare;
    }

    @Override
    public void descriereRol(){
        System.out.println("Rol: Medic - Specializare: " + specializare);
    }
}
