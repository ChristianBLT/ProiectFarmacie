# +++ Sistem de Gestiune Farmacie & Prescripții

Aceasta este o aplicație Java Full-Stack dezvoltată pentru digitalizarea fluxului de lucru dintr-o farmacie și gestionarea relației dintre medici, pacienți și medicamente. Proiectul include o interfață web modernă realizată cu framework-ul Vaadin.

## Funcționalități Principale

Aplicația gestionează un flux complet de prescripție medicală:

* **Gestiune Medici & Spitale:** Medicii sunt înregistrați în sistem și asociați unei unități medicale (Spital).
* **Evidența Pacienților:** Fiecare medic are arondați pacienți, putând vizualiza istoricul acestora.
* **Emiterea Rețetelor:**
    * Medicul creează o rețetă pentru un pacient.
    * **Logica de Rețetă:** O rețetă conține o listă de `Articole` (medicamente specifice + cantități/dozaj).
    * Sistemul verifică disponibilitatea medicamentelor.
* **Modul Farmacie:** Farmaciștii pot vizualiza rețetele emise, pot verifica stocul de medicamente și pot elibera produsele către pacienți.
* **Autentificare:** Sistem de login securizat pentru diferiți utilizatori (Medic, Farmacist, Admin).

## Tehnologii Utilizate

* **Limbaj:** Java (JDK 17+)
* **Frontend:** Vaadin (Interfață Web Reactivă)
* **Backend:** Java SE + JPA (Java Persistence API)
* **Bază de Date:** Relational Database (configurat prin `persistence.xml`)
* **Build Tool:** Maven

## 📂 Structura Proiectului

Proiectul este organizat în două module principale (Monorepo):

1.  **`Proiect_Farmacie` (Backend Logic):**
    * Conține clasele entitate: `Medic`, `Pacient`, `Medicament`, `Reteta`, `ArticolReteta`.
    * Gestionează regulile de business și accesul la date (Repositories).

2.  **`ProiectFram_WEB` (Frontend UI):**
    * Implementează interfața grafică folosind Vaadin.
    * Conține vederile (Views) pentru tabelele de date, formularele de adăugare și dashboard-ul principal.

##  Cum se rulează

1.  Deschideți folderul rădăcină în **IntelliJ IDEA**.
2.  Lăsați Maven să descarce dependențele (Load Maven Changes).
3.  Rulați configurația de pornire din modulul Web (clasa `Application` sau `Main`).
4.  Accesați în browser: `http://localhost:8080`.

---
*Proiect realizat pentru cursul de Programare Java / Dezvoltare Web.*
