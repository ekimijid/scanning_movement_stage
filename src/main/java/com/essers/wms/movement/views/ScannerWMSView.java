package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.service.MovementServ;
import com.essers.wms.movement.security.SecurityServ;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
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


@PermitAll
@Route(value="scanner/:movementID?", layout = MainView.class)
@PageTitle("ScannerWMS")
public class ScannerWMSView extends Div implements BeforeEnterObserver {

    private MovementServ movementserv;
    private SecurityServ securityServ;

    public ScannerWMSView(MovementServ movementserv, SecurityServ securityServ) {
        this.movementserv = movementserv;
        this.securityServ = securityServ;
        setSizeFull();

    }

    private void imageSend() {
        Notification.show("Image maken");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        details(movementserv.getById(Long.valueOf(beforeEnterEvent.getRouteParameters().get("movementID").get())));

    }
    private void details(Movement movement){
        if(movement.getState().equals("picked")){
            message("Barcode has allready been scaned!");
            Button button=new Button("Back to movements", buttonClickEvent -> {
                routerLink(movement);
            });
            add(button);
        }
        else {
            Button alertbutton=new Button("Damage", buttonClickEvent -> { imageSend();});
            alertbutton.setIcon(VaadinIcon.INFO.create());
            alertbutton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
            add(alertbutton);
            alertbutton.getElement().getStyle().set("margin-right", "auto");
            movement.setIn_progress_user(securityServ.getAuthenticatedUser().getUsername());
            movement.setState("in_process");
            movementserv.save(movement);
            Span user=new Span("User:  "+movement.getIn_progress_user()+ " progress");
            Span name = new Span("Company:  " + movement.getWms_company());
            Span site = new Span( "Site:   "+ movement.getWms_site()+ "-"+movement.getWms_warehouse());
            Span stock=new Span("Stock:   " + movement.getStock(movement.getProduct_ID()));
            Span productId = new Span( movement.getProduct_ID());
            VerticalLayout contentInfo = new VerticalLayout(user, name, site, stock, productId);
            contentInfo.setPadding(false);
            contentInfo.setSpacing(false);
            Details detailsInfo = new Details("Movement information", contentInfo);
            detailsInfo.addThemeVariants(DetailsVariant.FILLED);
            detailsInfo.setOpened(false);

            Span from=new Span("From: " + movement.getLocation_from());
            Span to=new Span("To: "+ movement.getLocation_to());
            TextField textField = new TextField();
            textField.setLabel("Pallet ID    ");
            textField.setValue("Pallet ID"   );
            textField.setClearButtonVisible(true);
            Button button=new Button("Enter", buttonClickEvent -> {
                scanProductCode(movement, textField.getValue());
                detailsInfo.setOpened(false);
            });
            VerticalLayout contentPalletID = new VerticalLayout(from, to, textField, button);
            contentPalletID.setPadding(false);
            contentPalletID.setSpacing(false);
            Details detailsPalletID= new Details("Scan PalletID", contentPalletID);
            detailsPalletID.setOpened(false);
            detailsPalletID.addThemeVariants(DetailsVariant.FILLED);
            add(detailsInfo, detailsPalletID);
        }

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
            content.setSpacing(false);
            content.setPadding(false);
            Details details = new Details("Scan shiping location", content);
            details.setOpened(false);
            details.addThemeVariants(DetailsVariant.FILLED);
            add(details);
        }
        else {
            message("Pallet ID is not correct");
        }

    }

    private void updatePickinglist(Movement movement, String str) {
        if(movement.getLocation().equals(str)){
            movement.setState("picked");
            movement.setIn_progress_user("");
            movement.setHandled_user(securityServ.getAuthenticatedUser().getUsername());
            movementserv.save(movement);
            routerLink(movement);
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
            notification.add(layout);
            notification.open();
            notification.setPosition(Notification.Position.MIDDLE);
        }
        private void routerLink(Movement movement){
            UI.getCurrent().navigate("movements/"+movement.getPickinglist().getPicking_list_ID());

        }
}