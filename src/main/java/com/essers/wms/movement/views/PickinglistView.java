package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Company;
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
@Route(value="pickinglist/:companyID", layout = MainView.class)
@PageTitle("PickingList")
public class PickinglistView extends VerticalLayout implements BeforeEnterObserver{
        Grid<Pickinglist> grid = new Grid<>(Pickinglist.class);
        private PickinglistServ pickinglistServ;
        private MovementServ movementserv;
        private CompanyServ companyServ;
        private Company company;

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
        grid.setColumns("picking_list_ID");
        grid.addColumn(pickinglist -> pickinglist.getCompany().getName()).setHeader("Company");
        grid.addColumn(pickinglist -> pickinglist.getWms_site().getName()).setHeader("Site");
        grid.addColumn(pickinglist -> pickinglist.getWms_warehouse().getName()).setHeader("Warehouse");
       // grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                routerLink(event.getValue()));
    }

    private void updateList () {
            grid.setItems(pickinglistServ.getByCompany(company));
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

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
      company=companyServ.getById(Long.valueOf(beforeEnterEvent.getRouteParameters().get("companyID").get()));
        System.out.println("Company: "+ company);
      updateList();
        System.out.println(pickinglistServ.getByCompany(company).size());
    }
}
