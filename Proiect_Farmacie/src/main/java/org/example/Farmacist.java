package org.example;
import jakarta.persistence.*;
import org.example.Utilizator;

@Entity

public class Farmacist extends Utilizator {
    private String numeFarmacie;

    public Farmacist() {
    }

    public String getNumeFarmacie() {
        return numeFarmacie;
    }

    public void setNumeFarmacie(String numeFarmacie) {
        this.numeFarmacie = numeFarmacie;
    }

    public Farmacist(int id, String denumire, String email, String parola, String rol, String numeFarmacie) {
        super(id, denumire, email, parola, rol);
        this.numeFarmacie = numeFarmacie;
    }

    @Override
    public void descriereRol() {
        System.out.println("Rol: Farmacist - Lucrează la: " + numeFarmacie);
    }
}
