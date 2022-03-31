package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.service.Movementserv;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value="", layout = MainView.class)
@PageTitle("Movements")
public class MovenmentsView extends VerticalLayout {
    Grid<Movement> grid = new Grid<>(Movement.class);

    private Movementserv movementserv;

    public MovenmentsView (Movementserv movementserv) {
        this.movementserv = movementserv;

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        updateList();
        add(getContent());
    }


    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("movement_ID","wms_company", "wms_site", "wms_warehouse", "movement_type","priority","product_ID",
                "supplier_ID", "quantity", "uom", "location_from", "location_to","in_progress_timestamp", "in_progress_user");
        grid.addColumn(movement -> movement.getStock().getQuantity()).setHeader("Stock");
        grid.addColumn(movement -> movement.getPickinglist().getPicking_list_ID()).setHeader("PickingList");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList () {
        grid.setItems(movementserv.getAll());
    }
    private Component getContent() {
        H1 logo = new H1("Movements");
        VerticalLayout content = new VerticalLayout(logo, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
}
