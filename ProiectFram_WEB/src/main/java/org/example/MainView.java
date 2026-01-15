package org.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
@Route
public class MainView extends VerticalLayout implements RouterLayout {

    public MainView() {
        setMenuBar();
    }

    private void setMenuBar() {
        MenuBar mainMenu = new MenuBar();

        // Home Menu Item
        mainMenu.addItem("Home", event -> UI.getCurrent().navigate(MainView.class));

        // Medicamente Menu
        MenuItem medicamenteMenu = mainMenu.addItem("Medicamente");
        SubMenu medicamenteSubMenu = medicamenteMenu.getSubMenu();
        medicamenteSubMenu.addItem("Lista Medicamente", event -> UI.getCurrent().navigate(MedicamentGridView.class));
        medicamenteSubMenu.addItem("Form Editare Medicament", event -> UI.getCurrent().navigate(Medicament_View.class));

        MenuItem medicMenu =mainMenu.addItem("Medic");
        SubMenu medicSubmenu=medicMenu.getSubMenu();
        medicSubmenu.addItem("ListaMedici", event -> UI.getCurrent().navigate(MedicGridView.class));
        medicSubmenu.addItem("Form Editare Medicament", event -> UI.getCurrent().navigate(Medic_View.class));

        MenuItem farmacieMenu =mainMenu.addItem("Farmacie");
        SubMenu farmacieSubmenu=farmacieMenu.getSubMenu();
        farmacieSubmenu.addItem("ListaFarmaci", event -> UI.getCurrent().navigate(FarmacieGridView.class));
        farmacieSubmenu.addItem("Form Editare Farmacie", event -> UI.getCurrent().navigate(Farmacie_View.class));

        // Adăugare în layout
        add(new HorizontalLayout(mainMenu));
    }
}
