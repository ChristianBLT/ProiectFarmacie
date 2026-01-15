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

@PageTitle("Medici")
@Route(value = "medici", layout = MainView.class)
public class MedicGridView extends VerticalLayout implements HasUrlParameter<Integer> {
    private static final long serialVersionUID = 1L;

    private EntityManager em;
    private EntityManagerFactory emf;

    private MedicRepository medicRepository = new MedicRepository();
    private List<Medic> medici = new ArrayList<>();
    private Medic medic = null;

    private H1 titluForm = new H1("Lista Medici");
    private TextField filterText = new TextField();
    private Button cmdEditMedic = new Button("Editează Medic...");
    private Button cmdAdaugaMedic = new Button("Adaugă Medic...");
    private Button cmdStergeMedic = new Button("Șterge Medic");
    private Grid<Medic> grid = new Grid<>(Medic.class);

    public MedicGridView() {
        initDataModel();
        initViewLayout();
        initControllerActions();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {
        if (id != null) {
            this.medic = em.find(Medic.class, id);
        }
        if (this.medic == null && !this.medici.isEmpty()) {
            this.medic = this.medici.get(0);
        }
        this.refreshForm();
    }

    private void initDataModel() {
        emf = Persistence.createEntityManagerFactory("ProduseJPA");
        em = emf.createEntityManager();
        reloadMedici();
    }

    private void initViewLayout() {
        filterText.setPlaceholder("Filtrare după nume...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        HorizontalLayout gridToolbar = new HorizontalLayout(filterText, cmdEditMedic, cmdAdaugaMedic, cmdStergeMedic);

        grid.setColumns("id", "denumire", "email", "parola" , "specializare");
        grid.addComponentColumn(item -> createGridActionsButtons(item)).setHeader("Acțiuni");

        this.add(titluForm, gridToolbar, grid);
    }

    private void initControllerActions() {
        filterText.addValueChangeListener(e -> updateList());
        cmdEditMedic.addClickListener(e -> editMedic());
        cmdAdaugaMedic.addClickListener(e -> adaugaMedic());
        cmdStergeMedic.addClickListener(e -> stergeMedic());
    }

    private Component createGridActionsButtons(Medic item) {
        Button cmdEditItem = new Button("Editează");
        cmdEditItem.addClickListener(e -> {
            grid.asSingleSelect().setValue(item);
            editMedic();
        });

        Button cmdDeleteItem = new Button("Șterge");
        cmdDeleteItem.addClickListener(e -> {
            grid.asSingleSelect().setValue(item);
            stergeMedic();
        });

        return new HorizontalLayout(cmdEditItem, cmdDeleteItem);
    }

    private void editMedic() {
        this.medic = this.grid.asSingleSelect().getValue();
        if (this.medic != null) {
            UI.getCurrent().navigate(Medic_View.class, this.medic.getId());
        } else {
            Notification.show("Selectați un medic pentru a edita.");
        }
    }

    private void updateList() {
        String filter = filterText.getValue() != null ? filterText.getValue().toLowerCase() : "";
        List<Medic> lstMediciFiltrati = this.medici.stream()
                .filter(m -> m.getDenumire().toLowerCase().contains(filter))
                .toList();

        grid.setItems(lstMediciFiltrati);
    }

    private void refreshForm() {
        reloadMedici();
        if (this.medic != null) {
            grid.setItems(this.medici);
            grid.select(this.medic);
        } else {
            grid.deselectAll();
        }
    }

    private void adaugaMedic() {
        Medic nou = new Medic();
        UI.getCurrent().navigate(Medic_View.class, -1);
    }

    private void stergeMedic() {
        this.medic = this.grid.asSingleSelect().getValue();
        if (this.medic == null) {
            Notification.show("Selectați un medic pentru a șterge.");
            return;
        }

        try {
            em.getTransaction().begin();
            if (em.contains(this.medic)) {
                em.remove(this.medic);
            }
            em.getTransaction().commit();
            Notification.show("Medic șters cu succes.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            Notification.show("Eroare la ștergerea medicului.");
            e.printStackTrace();
        } finally {
            reloadMedici();
        }
    }

    private void reloadMedici() {
        medici.clear();
        List<Medic> lst = em.createQuery("SELECT m FROM Medic m ORDER BY m.id", Medic.class).getResultList();
        if (lst != null) {
            medici.addAll(lst);
            Collections.sort(medici, (m1, m2) -> Integer.compare(m1.getId(), m2.getId()));
        }
        grid.setItems(medici);
    }
}
