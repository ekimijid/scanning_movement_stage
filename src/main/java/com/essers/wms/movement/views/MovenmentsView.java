package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.service.MovementServ;
import com.essers.wms.movement.data.service.PickinglistServ;
import com.essers.wms.movement.data.service.StockServ;
import com.essers.wms.movement.security.SecurityServ;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.*;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route(value="movements/:pickinglistID?", layout = MainView.class)
@PageTitle("Movements")
public class MovenmentsView extends VerticalLayout implements BeforeEnterObserver {
    private PickinglistServ pickinglistServ;
    private MovementServ movementserv;
    private StockServ stockServ;
    private SecurityServ securityServ;
    private Pickinglist pickinglist;

    private List<Movement> movements=new ArrayList<>();

    public MovenmentsView (PickinglistServ pickinglistServ, MovementServ movementserv, StockServ stockServ, SecurityServ securityServ) {
        this.pickinglistServ = pickinglistServ;
        this.movementserv = movementserv;
        this.stockServ = stockServ;
        this.securityServ = securityServ;
        Button button=new Button("Back to pickinglist", buttonClickEvent -> {
            routerLinkBack(pickinglist);
        });
        H5 logo = new H5("Movements");
        add(button, logo);
        addClassName("list-view");
        setSizeFull();
    }


    private void routerLinkBack(Pickinglist pickinglist) {
        UI.getCurrent().navigate("pickinglist/"+pickinglist.getCompany().getId());
    }

    private Component getContent(Movement movement) {
        boolean isNotPicked=true;
        movement.setIn_progress_user(securityServ.getAuthenticatedUser().getUsername());
        movementserv.save(movement);
        String username=movement.getIn_progress_user();
        if(movement.getState().equals("picked")){
            username=movement.getHandled_user();
            isNotPicked=false;
        }
        TextArea info = new TextArea(
                null,
                "Movement" + movement.getMovement_ID()+ "                  "+ movement.getState()+
                        "\n" + movement.getMovement_type() + "                                       "+username+
                        "\n" + movement.getWms_company() +
                        "\n" +
                        movement.getWms_site() + " - "+  movement.getWms_warehouse()
                       ,
                (String) null
        );
        info.setWidth("300px");
        info.setReadOnly(true);
        Button scanButton = new Button("Scan barcode ", VaadinIcon.BARCODE.create(), buttonClickEvent -> routerLink(movement));
        scanButton.setEnabled(isNotPicked);
        Icon prodicon=new Icon(VaadinIcon.PACKAGE);
        Span quantity = new Span(movement.getQuantity()+" "+movement.getMovement_type());
        HorizontalLayout layout = new HorizontalLayout(prodicon, quantity);
        Icon locIcon= new Icon(VaadinIcon.MAP_MARKER);
        Icon locIcon1= new Icon(VaadinIcon.MAP_MARKER);
        Span from=new Span("From: " + movement.getLocation_from());
        Span to=new Span("To: "+movement.getLocation_to());
        HorizontalLayout layout1=new HorizontalLayout(locIcon, from);
        HorizontalLayout layout2=new HorizontalLayout(locIcon1, to);
        VerticalLayout content = new VerticalLayout(info, layout,layout1,layout2,scanButton );
        content.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("font-weight", "bold")
                .set("margin", "var(--lumo-space-m) var(--lumo-space-l)");
        content.setSizeFull();
        content.addClassNames("content");
        return content;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        pickinglist= pickinglistServ.getById(Long.valueOf(event.getRouteParameters().get("pickinglistID").get()));
        movements=movementserv.getByPickinglist(pickinglist);
        System.out.println("from beforeEnter"+ movements.size());
        for (Movement m:movements
        ) {
            add(getContent(m));
        }
    }

    private void routerLink(Movement value) {
        UI.getCurrent().navigate("scanner/"+value.getMovement_ID());
    }
}
