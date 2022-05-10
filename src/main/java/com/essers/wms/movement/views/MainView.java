package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.service.MovementService;
import com.essers.wms.movement.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

import java.util.List;


public class MainView extends AppLayout {

    private final transient SecurityService securityService;
    private final transient MovementService movementService;

    public MainView(SecurityService securityService, MovementService movementService) {
        this.securityService = securityService;
        this.movementService = movementService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("WMS Scanner");
        logo.getStyle().set("font-size", "var(--lumo-font-size-l)").set("color", "blue").set("font-weight", "bold").set("margin", "var(--lumo-space-m) var(--lumo-space-l)");
        Button logout = new Button("Log out", e -> {
            movementsStatusChange();
            securityService.logout();
        });

        Avatar user = new Avatar(securityService.getAuthenticatedUser().getUsername());
        DrawerToggle dt = new DrawerToggle();
        dt.setIcon(VaadinIcon.MENU.create());
        HorizontalLayout header = new HorizontalLayout(dt, logo, user, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Home", PortalView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        VerticalLayout layout = new VerticalLayout(listLink);
        layout.setWidth("20px");

        layout.getStyle().set("font-size", "var(--lumo-font-size-l)").set("font-weight", "bold").set("text-decoration", "underline").set("margin", "var(--lumo-space-m) var(--lumo-space-l)");
        addToDrawer(layout);
    }

    private void movementsStatusChange() {
        List<Movement> movements = movementService.getAll();
        for (Movement mov : movements) {
            if (mov.getState().equals("in_process") && (mov.getInProgressUser().equals(securityService.getAuthenticatedUser().getUsername()))) {
                mov.setState("pick");
                mov.setInProgressUser(null);
                movementService.save(mov);
            }
        }
    }
}
