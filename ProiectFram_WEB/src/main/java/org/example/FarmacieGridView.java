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

@PageTitle("Farmacii")
@Route(value = "farmacii", layout = MainView.class)
public class FarmacieGridView extends VerticalLayout implements HasUrlParameter<String> { // Modificat tipul parametrului pentru ID-ul farmaciei
    private static final long serialVersionUID = 1L;

    private EntityManager em;
    private EntityManagerFactory emf;
    private List<Farmacie> farmacii = new ArrayList<>();
    private Farmacie farmacie = null;

    private H1 titluForm = new H1("Lista Farmacii");
    private TextField filterText = new TextField();
    private Button cmdEditFarmacie = new Button("Editează Farmacie...");
    private Button cmdAdaugaFarmacie = new Button("Adaugă Farmacie...");
    private Button cmdStergeFarmacie = new Button("Șterge Farmacie");
    private Grid<Farmacie> grid = new Grid<>(Farmacie.class);

    public FarmacieGridView() {
        initDataModel();
        initViewLayout();
        initControllerActions();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String id) { // Modificat tipul ID-ului
        if (id != null && !id.isEmpty()) {
            this.farmacie = em.find(Farmacie.class, Long.parseLong(id)); // Conversie la Long pentru a potrivi tipul ID-ului din clasa Farmacie
        }
        if (this.farmacie == null && !this.farmacii.isEmpty()) {
            this.farmacie = this.farmacii.get(0);
        }
        this.refreshForm();
    }

    private void initDataModel() {
        emf = Persistence.createEntityManagerFactory("ProduseJPA");
        em = emf.createEntityManager();
        reloadFarmacii();
    }

    private void initViewLayout() {
        filterText.setPlaceholder("Filtrare după nume...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        HorizontalLayout gridToolbar = new HorizontalLayout(filterText, cmdEditFarmacie, cmdAdaugaFarmacie, cmdStergeFarmacie);

        grid.setColumns("id", "numeFarmacie", "adresa");
        grid.addComponentColumn(item -> createGridActionsButtons(item)).setHeader("Acțiuni");

        this.add(titluForm, gridToolbar, grid);
    }

    private void initControllerActions() {
        filterText.addValueChangeListener(e -> updateList());
        cmdEditFarmacie.addClickListener(e -> editFarmacie());
        cmdAdaugaFarmacie.addClickListener(e -> adaugaFarmacie());
        cmdStergeFarmacie.addClickListener(e -> stergeFarmacie());
    }

    private Component createGridActionsButtons(Farmacie item) {
        Button cmdEditItem = new Button("Editează");
        cmdEditItem.addClickListener(e -> {
            grid.asSingleSelect().setValue(item);
            editFarmacie();
        });

        Button cmdDeleteItem = new Button("Șterge");
        cmdDeleteItem.addClickListener(e -> {
            grid.asSingleSelect().setValue(item);
            stergeFarmacie();
        });

        return new HorizontalLayout(cmdEditItem, cmdDeleteItem);
    }

    private void editFarmacie() {
        this.farmacie = this.grid.asSingleSelect().getValue();
        if (this.farmacie != null) {
            UI.getCurrent().navigate(Farmacie_View.class, this.farmacie.getId());
        } else {
            Notification.show("Selectați o farmacie pentru a edita.");
        }
    }

    private void updateList() {
        String filter = filterText.getValue() != null ? filterText.getValue().toLowerCase() : "";
        List<Farmacie> lstFarmaciiFiltrate = this.farmacii.stream()
                .filter(f -> f.getNumeFarmacie().toLowerCase().contains(filter))
                .toList();

        grid.setItems(lstFarmaciiFiltrate);
    }

    private void refreshForm() {
        reloadFarmacii();
        if (this.farmacie != null) {
            grid.setItems(this.farmacii);
            grid.select(this.farmacie);
        } else {
            grid.deselectAll();
        }
    }

    private void adaugaFarmacie() {
        Farmacie nou = new Farmacie();
        UI.getCurrent().navigate(Farmacie_View.class);
    }

    private void stergeFarmacie() {
        this.farmacie = this.grid.asSingleSelect().getValue();
        if (this.farmacie == null) {
            Notification.show("Selectați o farmacie pentru a șterge.");
            return;
        }

        try {
            em.getTransaction().begin();
            if (em.contains(this.farmacie)) {
                em.remove(this.farmacie);
            }
            em.getTransaction().commit();
            Notification.show("Farmacie ștersă cu succes.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            Notification.show("Eroare la ștergerea farmaciei.");
            e.printStackTrace();
        } finally {
            reloadFarmacii();
        }
    }

    private void reloadFarmacii() {
        farmacii.clear();
        List<Farmacie> lst = em.createQuery("SELECT f FROM Farmacie f ORDER BY f.id", Farmacie.class).getResultList();
        if (lst != null) {
            farmacii.addAll(lst);
            Collections.sort(farmacii, (f1, f2) -> f1.getId().compareTo(f2.getId()));
        }
        grid.setItems(farmacii);
    }
}