package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.service.Movementserv;
import com.essers.wms.movement.security.SecurityServ;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.security.PermitAll;
import java.util.List;


@PermitAll
@Route(value="scanner/:movementID?", layout = MainView.class)
@PageTitle("ScannerWMS")
public class ScannerWMSView extends VerticalLayout implements BeforeEnterObserver {

    private Movementserv movementserv;
    private SecurityServ securityServ;

    public ScannerWMSView(Movementserv movementserv, SecurityServ securityServ) {
        this.movementserv = movementserv;
        this.securityServ = securityServ;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        details(movementserv.getById(Long.valueOf(beforeEnterEvent.getRouteParameters().get("movementID").get())));

    }
    private void details(Movement movement){
        movement.setIn_progress_user(securityServ.getAuthenticatedUser().getUsername());
        Span user=new Span(movement.getIn_progress_user()+ " progress");
        Span name = new Span( movement.getWms_company());
        Span site = new Span(movement.getWms_site()+ "-"+movement.getWms_warehouse());
        Span productId = new Span(movement.getProduct_ID());
        VerticalLayout content = new VerticalLayout(user, name, site, productId);
        content.setSpacing(false);
        content.setPadding(false);
        Details details = new Details("Movement information", content);
        details.setOpened(true);
        Span from=new Span("From: " + movement.getLocation_from());
        Span to=new Span("To: "+ movement.getLocation_to());
        TextField textField = new TextField();
        textField.setLabel("Pallet ID");
        textField.setValue("Pallet ID");
        textField.setClearButtonVisible(true);
        Button button=new Button("Enter", buttonClickEvent -> {
            scanProductCode(movement, textField.getValue());
        });
        VerticalLayout content1 = new VerticalLayout(from, to, textField, button);
        Details details1 = new Details("Scan PalletID", content1);
        details1.setOpened(true);
        add(details, details1);
    }
    private void scanProductCode(Movement movement, String str) {
        if(movement.getProduct_ID().equals(str)){
            Span loc=new Span("Shiping location: "+ movement.getLocation());
            TextField textField = new TextField();
            textField.setLabel("Shiping location");
            textField.setValue("shiping location");
            textField.setClearButtonVisible(true);
            Button button=new Button("Enter", buttonClickEvent -> {
                updatePickinglist(movement, textField.getValue());
            });
            VerticalLayout content= new VerticalLayout(loc, textField, button);
            Details details = new Details("Scan PalletID", content);
            details.setOpened(true);
            add(details);
        }
        else {
            message("Pallet ID is not correct");
        }
    }

    private void updatePickinglist(Movement movement, String str) {
        if(movement.getLocation().equals(str)){
            movement.setState("picked");
            movementserv.remove(movement);
            movement.setIn_progress_user(null);
            UI.getCurrent().navigate("movements/"+movement.getPickinglist().getPicking_list_ID());
        }
        else{
            message("Shipping location is not correct");
        }
    }
        private void message(String str){
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div text = new Div(new Text(str));

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(event -> {
                notification.close();
            });

            HorizontalLayout layout = new HorizontalLayout(text, closeButton);
            layout.setAlignItems(Alignment.CENTER);
            notification.add(layout);
            notification.open();
        }
}
