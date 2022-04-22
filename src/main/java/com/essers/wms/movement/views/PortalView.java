package com.essers.wms.movement.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;


@PermitAll
@Route(value="", layout = MainView.class)
@PageTitle("WMS Scanner")
public class PortalView extends VerticalLayout {

    public PortalView() {
        Button fp=new Button("FULL PALLETTE", buttonClickEvent -> { fullpallete(); });
        fp.setHeight("80px");
        fp.setWidth("300px");
        fp.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("background", "blue");

        Button pp=new Button("PART PALLETTE", buttonClickEvent -> { partpallete(); });
        pp.setHeight("80px");
        pp.setWidth("300px");
        pp.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("background", "blue")
                .set("margin", "var(--lumo-space-m) var(--lumo-space-l)");
        Button ip=new Button("INTERRACK", buttonClickEvent -> { interrack(); });
        ip.setHeight("80px");
        ip.setWidth("300px");
        ip.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("background", "blue");
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        add(fp, pp, ip);

    }

    private void interrack() {
        UI.getCurrent().navigate("company");
    }

    private void partpallete() {
        UI.getCurrent().navigate("company");
    }

    private void fullpallete() {
        UI.getCurrent().navigate("company");
    }


}
