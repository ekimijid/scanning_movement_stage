package com.essers.wms.movement.views;

import com.essers.wms.movement.security.SecurityServ;
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


public class MainView extends AppLayout {

    private SecurityServ securityServ;

    public MainView(SecurityServ securityServ) {
        this.securityServ = securityServ;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("WMS Scanner");
        logo.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("color", "blue")
                .set("font-weight", "bold")
                .set("margin", "var(--lumo-space-m) var(--lumo-space-l)");
        logo.addClassNames("text-l", "m-m");
        Button logout = new Button("Log out", e->securityServ.logout());

        Avatar user = new Avatar(securityServ.getAuthenticatedUser().getUsername());
        DrawerToggle dt= new DrawerToggle();
        dt.setIcon(VaadinIcon.MENU.create());
        HorizontalLayout header = new HorizontalLayout(
                dt,
                logo,  user, logout
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");
        addToNavbar(header);

    }

   private void createDrawer() {
        RouterLink listLink = new RouterLink("Home", PortalView.class);
     //   RouterLink homelink = new RouterLink("Movements", MovenmentsView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        VerticalLayout layout= new VerticalLayout( listLink );
        layout.setWidth("20px");
        layout.getStyle()
               .set("font-size", "var(--lumo-font-size-l)")
               .set("font-weight", "bold")
                .set("text-decoration","underline")
               .set("margin", "var(--lumo-space-m) var(--lumo-space-l)");
        addToDrawer(layout);
    }
}
