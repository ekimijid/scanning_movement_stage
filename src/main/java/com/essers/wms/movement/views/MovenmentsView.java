package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Movementtype;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.service.Movementserv;
import com.essers.wms.movement.data.service.PickinglistServ;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import javax.annotation.security.PermitAll;
import java.util.Optional;
import java.util.UUID;

@PermitAll
@Route(value="movements/:pickinglistID?", layout = MainView.class)
@PageTitle("Movements")
public class MovenmentsView extends VerticalLayout implements BeforeEnterObserver {
    Grid<Movement> grid = new Grid<>(Movement.class);
    private PickinglistServ pickinglistServ;
    private Movementserv movementserv;

    public MovenmentsView (PickinglistServ pickinglistServ, Movementserv movementserv) {
        this.pickinglistServ = pickinglistServ;
        this.movementserv = movementserv;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getContent());
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("movement_ID","wms_company", "wms_site", "wms_warehouse", "movement_type","priority","product_ID",
                "supplier_ID", "quantity", "uom", "location_from", "location_to","in_progress_timestamp", "in_progress_user");
      //  grid.addColumn(movement -> movement.getStock().getQuantity()).setHeader("Stock");
        grid.addColumn(movement -> movement.getPickinglist().getPicking_list_ID()).setHeader("PickingList");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                routerLink(event.getValue()));
    }


    private Component getContent() {
        H1 logo = new H1("Movements");
        VerticalLayout content = new VerticalLayout(logo, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Pickinglist pl= pickinglistServ.getById(Long.valueOf(event.getRouteParameters().get("pickinglistID").get()));
        grid.setItems(movementserv.getByPickinglist(pl));
    }

    private void routerLink(Movement value) {
        UI.getCurrent().navigate("scanner/"+value.getMovement_ID());
    }
}
