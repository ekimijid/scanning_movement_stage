package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.service.Movementserv;
import com.essers.wms.movement.data.service.PickinglistServ;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.security.PermitAll;

@PermitAll
@Route(value="pickinglist", layout = MainView.class)
@PageTitle("PickingList")
public class PickinglistView extends VerticalLayout {
        Grid<Pickinglist> grid = new Grid<>(Pickinglist.class);
        private PickinglistServ pickinglistServ;
        private Movementserv movementserv;

    public PickinglistView (PickinglistServ pickinglistServ, Movementserv movementserv) {
        this.pickinglistServ = pickinglistServ;
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
        grid.setColumns("picking_list_ID","wms_company", "wms_site", "wms_warehouse", "product_ID", "supplier_ID", "quantity", "uom", "location");
        grid.addColumn(pickinglist -> pickinglist.getMovement().getMovement_ID()).setHeader("Movement");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList () {
            grid.setItems(pickinglistServ.getAll());
        }
    private Component getContent() {
        H1 logo = new H1("Picking-list");
        VerticalLayout content = new VerticalLayout(logo, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
}
