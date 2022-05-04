package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Damagereport;
import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Product;
import com.essers.wms.movement.data.service.DamagereportServ;
import com.essers.wms.movement.data.service.ImageService;
import com.essers.wms.movement.data.service.MovementServ;
import com.essers.wms.movement.data.service.ProductServImpl;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@PermitAll
@Route(value = "damage/:movementID?", layout = MainView.class)
@PageTitle("WMS Scanner")
public class PhotoView extends VerticalLayout implements BeforeEnterObserver {
    private Upload upload;
    private Damagereport damagereport;
    private Product product;
    private MovementServ movementServ;
    private ImageService imageService;
    private VerticalLayout imageContainer;
    private DamagereportServ damagereportServ;
    private ProductServImpl productServ;
    private Movement movement;

    public PhotoView(ImageService imageService, ProductServImpl productServ, DamagereportServ damagereportServ, MovementServ movementServ) {
        this.imageService = imageService;
        this.damagereportServ = damagereportServ;
        this.movementServ = movementServ;
        this.productServ = productServ;
        damagereport = new Damagereport();
        imageContainer = new VerticalLayout();
        imageContainer.setWidth("200px");
        imageContainer.setHeight("200px");
        imageContainer.getStyle().set("overflow-x", "auto");
        Button button = new Button("Back to movements", buttonClickEvent -> {
            routerLink(movement);
        });
        add(button);

        for (Damagereport d:damagereportServ.getAll()
             ) {
            add(getContent(d));
        }

        initUploaderImage();
        add(imageContainer);
    }

    private Component getContent(Damagereport damagereport) {
        Movement mov=movementServ.getById(Long.valueOf(damagereport.getMovementID()));

        TextArea info = new TextArea(
                null,
                "Product: " + damagereport.getProductName()+ "" +
                        "\nProductID: " + damagereport.getProductID() +
                        "\nLocation: " + mov.getLocation_from() + "  " +
                        "\nTimestamp: " + damagereport.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME ) +
                        "\nOperator: " + mov.getIn_progress_user()
                ,
                (String) null
        );
        info.setWidth("300px");
        info.setReadOnly(true);
        Image img = imageService.generateImage(damagereport);
        img.setHeight("200px");
        img.setWidth("200px");
        VerticalLayout content = new VerticalLayout(info, img);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }


    private void initUploaderImage() {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/jpg", "image/png", "image/gif");
        Button uploadButton = new Button("Take Photo...");
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        upload.setUploadButton(uploadButton);
        upload.addSucceededListener(event -> {
            String attachmentName = event.getFileName();
            try {

                BufferedImage inputImage = ImageIO.read(buffer.getInputStream(attachmentName));
                ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
                ImageIO.write(inputImage, "png", pngContent);
                saveDamagePicture(pngContent.toByteArray(), product.getName(), product.getProduct_ID(), movement);
                showImage();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        add(upload);
    }

    private void saveDamagePicture(byte[] imageBytes, String filename, String productID, Movement movement) {
        damagereport.setProductName(filename);
        damagereport.setProductID(productID);
        damagereport.setMovementID(movement.getMovement_ID().toString());
        damagereport.setImage(imageBytes);
        damagereport.setTimestamp(LocalDateTime.now());
        damagereportServ.savereport(damagereport);

    }

    private void showImage() {
        Image image = imageService.generateImage(damagereport);
        image.setHeight("100%");
        imageContainer.removeAll();
        imageContainer.add(image);

    }

    private void routerLink(Movement movement) {
        UI.getCurrent().navigate("movements/" + movement.getPickinglist().getPicking_list_ID());

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        movement = movementServ.getById(Long.valueOf(beforeEnterEvent.getRouteParameters().get("movementID").get()));
        product = productServ.getByID(movement.getProduct_ID());
    }
}

