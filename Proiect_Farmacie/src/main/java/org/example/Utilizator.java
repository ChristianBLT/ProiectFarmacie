package org.example;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
@MappedSuperclass




public    class Utilizator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String denumire;
    private String email;
    private String parola;
    private String rol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Utilizator() {
    }

    public Utilizator(Integer id, String denumire, String email, String parola, String rol) {
        this.id = id;
        this.denumire = denumire;
        this.email = email;
        this.parola = parola;
        this.rol = rol;
    }

    public void descriereRol() {

    }

    @Override
    public String toString() {
        return "Utilizator {" +
                "ID='" + id + '\'' +
                ", Nume='" + denumire + '\'' +
                ", Email='" + email + '\'' +
                '}';
    }



}
