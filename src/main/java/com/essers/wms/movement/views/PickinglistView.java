package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.service.CompanyServ;
import com.essers.wms.movement.data.service.MovementServ;
import com.essers.wms.movement.data.service.PickinglistServ;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value="", layout = MainView.class)
@PageTitle("PickingList")
public class PickinglistView extends VerticalLayout {
        Grid<Pickinglist> grid = new Grid<>(Pickinglist.class);
        private PickinglistServ pickinglistServ;
        private MovementServ movementserv;
    private CompanyServ companyServ;

    public PickinglistView (PickinglistServ pickinglistServ, MovementServ movementserv, CompanyServ companyServ) {
        this.pickinglistServ = pickinglistServ;
        this.movementserv = movementserv;
        this.companyServ = companyServ;

        addClassName("list-view");
            setSizeFull();
            configureGrid();
            updateList();
            add(getContent());
        }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();

        grid.setColumns("quantity", "uom", "location");
        grid.addColumn(Pickinglist::getPicking_list_ID).setHeader("ID");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                routerLink(event.getValue()));
    }

    private void updateList () {
            grid.setItems(pickinglistServ.getAll());
        }
    private Component getContent() {
        H5 logo = new H5("Picking-list");
        VerticalLayout content = new VerticalLayout(logo, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
    private void routerLink(Pickinglist value) {
        UI.getCurrent().navigate("movements/"+value.getPicking_list_ID());
    }
}
