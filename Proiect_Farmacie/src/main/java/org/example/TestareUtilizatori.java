package org.example;

import java.util.ArrayList;
import java.util.List;

    class TestUtilizatori {
        public static void main(String[] args) {

            List<Utilizator> utilizatori = new ArrayList<>();
            Utilizator farmacist = new Farmacist(1, "Cornel Talmaci", "corneliu_tm@gmail.com", "parola123", "farmacist", "Ropharma");
            utilizatori.add(farmacist);
            Utilizator medic = new Medic(2, "Roberto Stoica", "@stkRoby@gmail.com", "parola1", "medic", "rezident");
            utilizatori.add(medic);
            Utilizator pacient = new Pacient(2, "Mihai Copacu", "mihai.cpc@gmail.com", "parola2", "pacient", "25.08.2003", "Istoric:obezitate");
            utilizatori.add(pacient);
            for (Utilizator utilizator : utilizatori) {
                System.out.println(utilizator);
                utilizator.descriereRol();
                System.out.println();
            }
            Utilizator utilizatorautentificatA = Autentificare.autentificare("@stkRoby@gmail.com", "parola1", utilizatori);
            if (utilizatorautentificatA != null) {
                System.out.println("Utilizator autentificat " + utilizatorautentificatA);
            } else {
                System.out.println("Autentificare esuata");
            }
            Utilizator utilizatorautentificatF = Autentificare.autentificare("mihai.cpc@gmail.com", "barzi",utilizatori);
            if(utilizatorautentificatF !=null){
                System.out.println("Autentificare reusita pentru " + utilizatorautentificatF);
            }
            else{
                System.out.println("Fail in autentificare");
            }
            //in reteta un articol reteta(asemanator cu articol comanda)


        }
    }


