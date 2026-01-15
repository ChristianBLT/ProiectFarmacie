package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@PageTitle("Medicamente")
@Route(value = "medicamente", layout = MainView.class)

public class MedicamentGridView extends VerticalLayout implements HasUrlParameter<Integer> {
    private static final long serialVersionUID = 1L;

    private EntityManager em;
    private EntityManagerFactory emf;
    private List<Medicament> medicamente = new ArrayList<>();
    private Medicament medicament = null;

    private H1 titluForm = new H1("Lista Medicamente");
    private TextField filterText = new TextField();
    private Button cmdEditMedicament = new Button("Editează Medicament...");
    private Button cmdAdaugaMedicament = new Button("Adaugă Medicament...");
    private Button cmdStergeMedicament = new Button("Șterge Medicament");
    private Grid<Medicament> grid = new Grid<>(Medicament.class);

    public MedicamentGridView() {
        initDataModel();
        initViewLayout();
        initControllerActions();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {
        if (id != null) {
            this.medicament = em.find(Medicament.class, id);
        }
        if (this.medicament == null && !this.medicamente.isEmpty()) {
            this.medicament = this.medicamente.get(0);
        }
        this.refreshForm();
    }

    private void initDataModel() {
        emf = Persistence.createEntityManagerFactory("ProduseJPA");
        em = emf.createEntityManager();
        reloadMedicamente();
    }

    private void initViewLayout() {
        filterText.setPlaceholder("Filtrare după nume...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        HorizontalLayout gridToolbar = new HorizontalLayout(filterText, cmdEditMedicament, cmdAdaugaMedicament, cmdStergeMedicament);

        grid.setColumns("id", "nume", "producator", "pret", "stocDisponibil");
        grid.addComponentColumn(item -> createGridActionsButtons(item)).setHeader("Acțiuni");

        this.add(titluForm, gridToolbar, grid);
    }

    private void initControllerActions() {
        filterText.addValueChangeListener(e -> updateList());
        cmdEditMedicament.addClickListener(e -> editMedicament());
        cmdAdaugaMedicament.addClickListener(e -> adaugaMedicament());
        cmdStergeMedicament.addClickListener(e -> stergeMedicament());
    }

    private Component createGridActionsButtons(Medicament item) {
        Button cmdEditItem = new Button("Editează");
        cmdEditItem.addClickListener(e -> {
            grid.asSingleSelect().setValue(item);
            editMedicament();
        });

        Button cmdDeleteItem = new Button("Șterge");
        cmdDeleteItem.addClickListener(e -> {
            grid.asSingleSelect().setValue(item);
            stergeMedicament();
        });

        return new HorizontalLayout(cmdEditItem, cmdDeleteItem);
    }

    private void editMedicament() {
        this.medicament = this.grid.asSingleSelect().getValue();
        if (this.medicament != null) {
            UI.getCurrent().navigate(Medicament_View.class, this.medicament.getId());
        } else {
            Notification.show("Selectați un medicament pentru a edita.");
        }
    }

    private void updateList() {
        String filter = filterText.getValue() != null ? filterText.getValue().toLowerCase() : "";
        List<Medicament> lstMedicamenteFiltrate = this.medicamente.stream()
                .filter(m -> m.getNume().toLowerCase().contains(filter))
                .toList();

        grid.setItems(lstMedicamenteFiltrate);
    }

    private void refreshForm() {
        reloadMedicamente();
        if (this.medicament != null) {
            grid.setItems(this.medicamente);
            grid.select(this.medicament);
        } else {
            grid.deselectAll();
        }
    }

    private void adaugaMedicament() {
        Medicament nou = new Medicament();
        UI.getCurrent().navigate(Medicament_View.class, -1);
    }

    private void stergeMedicament() {
        this.medicament = this.grid.asSingleSelect().getValue();
        if (this.medicament == null) {
            Notification.show("Selectați un medicament pentru a șterge.");
            return;
        }

        try {
            em.getTransaction().begin();
            if (em.contains(this.medicament)) {
                em.remove(this.medicament);
            }
            em.getTransaction().commit();
            Notification.show("Medicament șters cu succes.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            Notification.show("Eroare la ștergerea medicamentului.");
            e.printStackTrace();
        } finally {
            reloadMedicamente();
        }
    }

    private void reloadMedicamente() {
        medicamente.clear();
        List<Medicament> lst = em.createQuery("SELECT m FROM Medicament m ORDER BY m.id", Medicament.class).getResultList();
        if (lst != null) {
            medicamente.addAll(lst);
            Collections.sort(medicamente, (m1, m2) -> m1.getId().compareTo(m2.getId()));
        }
        grid.setItems(medicamente);
    }


}
