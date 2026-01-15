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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.grid.Grid;
import java.util.ArrayList;
import java.util.List;


@Route(value = "medic", layout = MainView.class)
@PageTitle("Medici")
public class Medic_View extends VerticalLayout implements HasUrlParameter<Integer> {
    private static final long serialVersionUID = 1L;

    // Model
    private EntityManager em;
    private Medic medic;
    private List<Reteta> retete = new ArrayList<>();
    private Binder<Medic> binder = new Binder<>(Medic.class);


    private VerticalLayout formLayoutToolbar;
    private H1 titluForm = new H1("Formular Medic");
    private IntegerField id = new IntegerField("ID Medic:");
    private TextField denumire = new TextField("Nume Medic:");
    private TextField email = new TextField("Email:");
    private TextField parola = new TextField("Parola");
    private TextField specializare = new TextField("Specializare:");


    private Button cmdAdaugare = new Button("Adaugă");
    private Button cmdSterge = new Button("Șterge");
    private Button cmdAbandon = new Button("Abandon");
    private Button cmdSalveaza = new Button("Salvează");


    private Grid<Reteta> reteteGrid = new Grid<>(Reteta.class, false);
    private Button cmdAdaugaReteta = new Button("Adaugă Rețetă");
    private Button cmdStergeReteta = new Button("Șterge Rețetă");

    public Medic_View() {
        initDataModel();
        initViewLayout();
        initControllerActions();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {
        if (id != null) {
            medic = findMedicById(id);
            if (medic == null) {
                medic = new Medic();
                medic.setId(id);
            }
        } else {
            medic = new Medic();
        }
        refreshForm();
    }

    private void initDataModel() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
        this.em = emf.createEntityManager();


        if (medic != null) {

            this.retete = em.createQuery("SELECT r FROM Reteta r WHERE r.medic.id = :medicId", Reteta.class)
                    .setParameter("medicId", medic.getId())
                    .getResultList();


            System.out.println("Retete încărcate pentru medicul cu ID-ul: " + medic.getId());
            for (Reteta r : retete) {
                System.out.println("Reteta ID: " + r.getIdReteta() + ", Status: " + r.getStatus());
            }
        } else {
            System.out.println("Medic este null!");
        }


        binder.bind(id, "id");
        binder.bind(denumire, "denumire");
        binder.bind(email, "email");
        binder.bind(parola, "parola");
        binder.bind(specializare, "specializare");

        refreshForm();

    }

    private void initViewLayout() {

        FormLayout formLayout = new FormLayout();
        formLayout.add(id, denumire, email, parola, specializare);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formLayout.setMaxWidth("400px");


        HorizontalLayout actionToolbar = new HorizontalLayout(cmdAdaugare, cmdSterge, cmdAbandon, cmdSalveaza);
        actionToolbar.setPadding(false);


        reteteGrid.addColumn(Reteta::getIdReteta).setHeader("ID");
        reteteGrid.addColumn(Reteta::getStatus).setHeader("Status");
        reteteGrid.addColumn(reteta -> reteta.getPacient() != null ? reteta.getPacient().getDenumire() : "").setHeader("Pacient");
        reteteGrid.addColumn(reteta -> reteta.getMedic() != null ? reteta.getMedic().getDenumire() : "").setHeader("Medic");
        reteteGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        HorizontalLayout gridDetailsToolbar = new HorizontalLayout(cmdAdaugaReteta, cmdStergeReteta);
        VerticalLayout releaseDetailGridLayout = new VerticalLayout(reteteGrid, gridDetailsToolbar);


        this.formLayoutToolbar = new VerticalLayout(formLayout, actionToolbar, releaseDetailGridLayout);
        this.add(titluForm, formLayoutToolbar);

        this.id.setEnabled(false);
    }

    private void initControllerActions() {
        cmdAdaugare.addClickListener(e -> {
            medic = new Medic();
            refreshForm();
        });

        cmdSterge.addClickListener(e -> {
            if (medic != null && medic.getId() != null) {
                deleteMedic(medic);
                medic = new Medic();
                refreshForm();
                Notification.show("Medic șters cu succes.");
            } else {
                Notification.show("Nu există un medic selectat pentru ștergere.");
            }
        });

        cmdAbandon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(MedicGridView.class)));

        cmdSalveaza.addClickListener(e -> {
            try {
                binder.writeBean(medic);
                saveMedic(medic);
                refreshForm();
                Notification.show("Medic salvat cu succes.");
            } catch (ValidationException ex) {
                Notification.show("Eroare la validare: " + ex.getMessage());
            }
        });

        cmdAdaugaReteta.addClickListener(e -> adaugaReteta());
        cmdStergeReteta.addClickListener(e -> stergeReteta());
    }

    private void refreshForm() {
        if (medic != null) {
            binder.setBean(medic);
            updateReteteGrid();
        }
    }

    private Medic findMedicById(Integer id) {
        try {
            em.getTransaction().begin();
            Medic found = em.find(Medic.class, id);
            em.getTransaction().commit();
            return found;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        }
    }

    private void saveMedic(Medic medic) {
        try {
            em.getTransaction().begin();
            medic = em.merge(medic);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    private void deleteMedic(Medic medic) {
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            Medic managedMedic = em.contains(medic) ? medic : em.merge(medic);
            em.remove(managedMedic);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    private void updateReteteGrid() {
        if (medic != null && medic.getListaRetete() != null) {
            reteteGrid.setItems(medic.getListaRetete());
        } else {
            reteteGrid.setItems(new ArrayList<>());
        }
    }

    private void adaugaReteta() {
        if (medic != null) {
            Reteta newReteta = new Reteta();
            newReteta.setMedic(medic);
            medic.getListaRetete().add(newReteta);
            updateReteteGrid();
            reteteGrid.asSingleSelect().setValue(newReteta);
        }
    }

    private void stergeReteta() {
        Reteta selectedReteta = reteteGrid.asSingleSelect().getValue();
        if (selectedReteta != null) {
            medic.getListaRetete().remove(selectedReteta);
            updateReteteGrid();
        }
    }
}