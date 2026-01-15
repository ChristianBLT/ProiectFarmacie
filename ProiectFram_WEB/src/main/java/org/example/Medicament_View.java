package org.example;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.ValidationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.example.MainView;
import org.example.Medicament;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "medicament", layout = MainView.class)
@PageTitle("Medicamente")
public class Medicament_View extends VerticalLayout implements HasUrlParameter<Integer>  {
    private static final long serialVersionUID = 1L;

    // Model
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
    private EntityManager em;
    private Medicament medicament;
    private final Binder<Medicament> binder = new Binder<>(Medicament.class);

    // View components
    private final H1 titluForm = new H1("Formular Medicament");
    private final IntegerField id = new IntegerField("ID medicament:");
    private final TextField nume = new TextField("Nume medicament:");
    private final TextField producator = new TextField("Producător:");
    private final IntegerField stocDisponibil = new IntegerField("Stoc disponibil:");
    private final NumberField pret = new NumberField("Preț:");

    private final Button cmdAdaugare = new Button("Adaugă");
    private final Button cmdSterge = new Button("Șterge");
    private final Button cmdAbandon = new Button("Abandon");
    private final Button cmdSalveaza = new Button("Salvează");

    public Medicament_View() {
        initEntityManager();
        initBinder();
        initViewLayout();
        initControllerActions();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {
        if (id != null) {
            medicament = findMedicamentById(id);
            if (medicament == null) {
                medicament = new Medicament();
                medicament.setId(id);
            }
        } else {
            medicament = new Medicament();
        }
        refreshForm();
    }

    private void initEntityManager() {
        if (em == null || !em.isOpen()) {
            this.em = emf.createEntityManager();
        }
    }

    private void initBinder() {
        binder.forField(id).bind(Medicament::getId, Medicament::setId);
        binder.forField(nume).bind(Medicament::getNume, Medicament::setNume);
        binder.forField(producator).bind(Medicament::getProducator, Medicament::setProducator);
        binder.forField(stocDisponibil).bind(Medicament::getStocDisponibil, Medicament::setStocDisponibil);
        binder.forField(pret).bind(Medicament::getPret, Medicament::setPret);
    }

    private void initViewLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(id, nume, producator, stocDisponibil, pret);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formLayout.setMaxWidth("400px");

        HorizontalLayout actionToolbar = new HorizontalLayout(cmdAdaugare, cmdSterge, cmdAbandon, cmdSalveaza);

        add(titluForm, formLayout, actionToolbar);


    }

    private void initControllerActions() {
        cmdAdaugare.addClickListener(e -> {
            medicament = new Medicament();
            refreshForm();
            Notification.show("Formularul a fost resetat.");
        });

        cmdSterge.addClickListener(e -> {
            if (medicament != null && medicament.getId() != null) {
                deleteMedicament(medicament);
                medicament = new Medicament();
                refreshForm();
                Notification.show("Medicament șters cu succes.");
            } else {
                Notification.show("Nu există un medicament selectat pentru ștergere.");
            }
        });

        cmdAbandon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(MedicamentGridView.class)));

        cmdSalveaza.addClickListener(e -> {
            try {
                binder.writeBean(medicament);
                saveMedicament(medicament);
                refreshForm();
                Notification.show("Medicament salvat cu succes.");
            } catch (ValidationException ex) {
                Notification.show("Eroare la validare: " + ex.getMessage());
            } catch (Exception ex) {
                Notification.show("Eroare la salvarea medicamentului: " + ex.getMessage());
            }
        });
    }

    private void refreshForm() {
        if (medicament != null) {
            binder.setBean(medicament);
        }
    }

    private Medicament findMedicamentById(Integer id) {
        try {
            em.getTransaction().begin();
            Medicament found = em.find(Medicament.class, id);
            em.getTransaction().commit();
            return found;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        }
    }

    private void saveMedicament(Medicament medicament) {
        try {

            if (this.medicament.getFarmacie() == null) {

                Farmacie farmacie = em.find(Farmacie.class, Long.valueOf(1));
                if (farmacie != null) {
                    this.medicament.setFarmacie(farmacie);
                } else {
                    throw new RuntimeException("Farmacie invalidă pentru medicament");
                }
            }


            this.em.getTransaction().begin();


            this.medicament = this.em.merge(this.medicament);


            this.em.getTransaction().commit();

            System.out.println("Medicament Salvat");

        } catch (Exception ex) {

            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }


            System.out.println("*** EntityManager Validation ex: " + ex.getMessage());


            throw new RuntimeException(ex.getMessage());
        }
    }



    private void deleteMedicament(Medicament medicament) {
        try {
            // Începe tranzacția dacă nu este activă
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }


            Medicament managedMedicament = em.contains(medicament) ? medicament : em.merge(medicament);
            em.remove(managedMedicament);


            em.getTransaction().commit();
        } catch (Exception ex) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }


}
