package org.example;

import org.example.Utilizator;

import java.util.List;

public class Autentificare {
    public static Utilizator autentificare(String email, String parola, List<Utilizator> utilizatori)
    {
        for(Utilizator utilizator:utilizatori){
            if(utilizator.getEmail().equalsIgnoreCase(email) && utilizator.getParola().equalsIgnoreCase(parola)){
                System.out.println("Autentificare reușită! Bine ai venit, " + utilizator.getDenumire());
                return utilizator;
            }

        }
        System.out.println("Autentificare esuata");
        return null;
    }
}
