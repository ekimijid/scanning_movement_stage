package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.service.CompanyServ;
import com.essers.wms.movement.data.service.PickinglistServ;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value="company",  layout = MainView.class)
@PageTitle("CompanyList")
public class CompanyView extends VerticalLayout {
    private CompanyServ companyServ;
    private PickinglistServ pickinglistServ;

    public CompanyView(CompanyServ companyServ, PickinglistServ pickinglistServ) {
        this.companyServ = companyServ;
        this.pickinglistServ = pickinglistServ;
        ComboBox<Company> companyComboBox = new ComboBox<>("Company");
        companyComboBox.setItems(companyServ.getAll());
        companyComboBox.setItemLabelGenerator(Company::getName);
        Button button=new Button("Select", buttonClickEvent -> {selectCompany(companyComboBox.getValue());});
        add(companyComboBox, button);


    }

    private void selectCompany(Company company) {
        if(pickinglistServ.getByCompany(company).size()!=0){
            UI.getCurrent().navigate("pickinglist/"+company.getId());
        }
        else {
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div text = new Div(new Text("No PickingList"));

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(event -> {
                notification.close();
            });

            HorizontalLayout layout = new HorizontalLayout(text, closeButton);
            layout.setAlignItems(Alignment.CENTER);
            notification.add(layout);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
        }

    }


}
