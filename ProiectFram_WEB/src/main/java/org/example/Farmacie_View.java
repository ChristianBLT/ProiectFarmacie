package org.example;



import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.ValidationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "farmacie", layout = MainView.class)
@PageTitle("Farmacie")
public class Farmacie_View extends VerticalLayout implements HasUrlParameter<Long> {

    private static final long serialVersionUID = 1L;

    // Model
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
    private EntityManager em;
    private Farmacie farmacie;
    private final Binder<Farmacie> binder = new Binder<>(Farmacie.class);

    // View components
    private final H1 titluForm = new H1("Formular Farmacie");
    private final TextField numeFarmacie = new TextField("Nume farmacie:");
    private final TextField adresa = new TextField("Adresă:");

    private final Button cmdAdaugare = new Button("Adaugă");
    private final Button cmdSterge = new Button("Șterge");
    private final Button cmdAbandon = new Button("Abandon");
    private final Button cmdSalveaza = new Button("Salvează");

    public Farmacie_View() {
        initEntityManager();
        initBinder();
        initViewLayout();
        initControllerActions();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long id) {
        if (id != null) {
            farmacie = findFarmacieById(id);
            if (farmacie == null) {
                farmacie = new Farmacie();
                farmacie.setId(id);
            }
        } else {
            farmacie = new Farmacie();
        }
        refreshForm();
    }

    private void initEntityManager() {
        if (em == null || !em.isOpen()) {
            this.em = emf.createEntityManager();
        }
    }

    private void initBinder() {
        binder.forField(numeFarmacie).bind(Farmacie::getNumeFarmacie, Farmacie::setNumeFarmacie);
        binder.forField(adresa).bind(Farmacie::getAdresa, Farmacie::setAdresa);
    }

    private void initViewLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(numeFarmacie, adresa);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formLayout.setMaxWidth("400px");

        HorizontalLayout actionToolbar = new HorizontalLayout(cmdAdaugare, cmdSterge, cmdAbandon, cmdSalveaza);

        add(titluForm, formLayout, actionToolbar);
    }

    private void initControllerActions() {
        cmdAdaugare.addClickListener(e -> {
            farmacie = new Farmacie();
            refreshForm();
            Notification.show("Formularul a fost resetat.");
        });

        cmdSterge.addClickListener(e -> {
            if (farmacie != null && farmacie.getId() != null) {
                deleteFarmacie(farmacie);
                farmacie = new Farmacie();
                refreshForm();
                Notification.show("Farmacie ștersă cu succes.");
            } else {
                Notification.show("Nu există o farmacie selectată pentru ștergere.");
            }
        });

        cmdAbandon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(FarmacieGridView.class)));

        cmdSalveaza.addClickListener(e -> {
            try {
                binder.writeBean(farmacie);
                saveFarmacie(farmacie);
                refreshForm();
                Notification.show("Farmacie salvată cu succes.");
            } catch (ValidationException ex) {
                Notification.show("Eroare la validare: " + ex.getMessage());
            } catch (Exception ex) {
                Notification.show("Eroare la salvarea farmaciei: " + ex.getMessage());
            }
        });
    }

    private void refreshForm() {
        if (farmacie != null) {
            binder.setBean(farmacie);
        }
    }

    private Farmacie findFarmacieById(Long id) {
        try {
            em.getTransaction().begin();
            Farmacie found = em.find(Farmacie.class, id);
            em.getTransaction().commit();
            return found;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        }
    }

    private void saveFarmacie(Farmacie farmacie) {
        try {
            em.getTransaction().begin();


            farmacie = em.merge(farmacie);


            em.getTransaction().commit();

            System.out.println("Farmacie Salvată");

        } catch (Exception ex) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }


            System.out.println("*** EntityManager Validation ex: " + ex.getMessage());


            throw new RuntimeException(ex.getMessage());
        }
    }

    private void deleteFarmacie(Farmacie farmacie) {
        try {

            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }


            Farmacie managedFarmacie = em.contains(farmacie) ? farmacie : em.merge(farmacie);
            em.remove(managedFarmacie);


            em.getTransaction().commit();
        } catch (Exception ex) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }
}


