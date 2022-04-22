package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.service.MovementServ;
import com.essers.wms.movement.data.service.PickinglistServ;
import com.essers.wms.movement.data.service.StockServ;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.*;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value="movements/:pickinglistID?", layout = MainView.class)
@PageTitle("Movements")
public class MovenmentsView extends VerticalLayout implements BeforeEnterObserver {
    Grid<Movement> grid = new Grid<>(Movement.class);
    private PickinglistServ pickinglistServ;
    private MovementServ movementserv;
    private StockServ stockServ;
    private Pickinglist pickinglist;

    public MovenmentsView (PickinglistServ pickinglistServ, MovementServ movementserv, StockServ stockServ) {
        this.pickinglistServ = pickinglistServ;
        this.movementserv = movementserv;
        this.stockServ = stockServ;
        Button button=new Button("Back to pickinglist", buttonClickEvent -> {
            routerLinkBack(pickinglist);
        });
        add(button);
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getContent());
    }

    private void routerLinkBack(Pickinglist pickinglist) {
        UI.getCurrent().navigate("pickinglist/"+pickinglist.getCompany().getId());
    }

    private void configureGrid() {
        grid.setColumns("product_ID", "movement_type","wms_company", "wms_site", "wms_warehouse","in_progress_user","state","handled_user", "quantity", "uom",
                "location_from", "location_to");
        grid.addColumn(new LocalDateTimeRenderer<>(Movement::getIn_progress_timestamp, "yyyy.MM.dd 'at' hh:mm")
                ).setHeader("Timestamp");
        grid.addColumn(movement -> movement.getPickinglist().getPicking_list_ID()).setHeader("PickingList");
        grid.addColumn(movement -> movement.getStock(movement.getProduct_ID())).setHeader("Stock");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                routerLink(event.getValue()));
    }
    private Component getContent() {
        H5 logo = new H5("Movements");
        VerticalLayout content = new VerticalLayout(logo, grid);
        content.setSizeFull();
        content.addClassNames("content");
        return content;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        pickinglist= pickinglistServ.getById(Long.valueOf(event.getRouteParameters().get("pickinglistID").get()));
        grid.setItems(movementserv.getByPickinglist(pickinglist));
    }

    private void routerLink(Movement value) {
        UI.getCurrent().navigate("scanner/"+value.getMovement_ID());
    }
}
