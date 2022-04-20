package com.essers.wms.movement.views;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value="barcode", layout = MainView.class)
public class Barcodescanner extends VerticalLayout {
    private TextField textFieldScanner = new TextField("Scan barcodezzz: ");
    public Barcodescanner() {
        handleKeyPress("key");
        add(textFieldScanner);

    }
    @ClientCallable
    public void scanner(){
        getElement().executeJs("$(document).ready(() => {$(document).keydown(e => {" +
                "if(e.key === 'Shift' || e.key === 'Unidentified'){return;}"+
                "this.$server.handleKeyPress(e.key)" +
                "})});");
    }

    private void handleKeyPress(String key){
        TextField textField=new TextField("Scan barcodeaaaa");
        textFieldScanner.focus();
        textFieldScanner.setLabel(textField.getValue());
        add(textField);
        System.out.println(key);
    }
}
