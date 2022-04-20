package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Damagereport;
import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Product;
import com.essers.wms.movement.data.service.DamagereportServ;
import com.essers.wms.movement.data.service.ImageService;
import com.essers.wms.movement.data.service.MovementServ;
import com.essers.wms.movement.data.service.ProductServImpl;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@PermitAll
@Route(value="damage/:movementID?", layout = MainView.class)
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
    private  Grid<Damagereport>grid =new Grid(Damagereport.class);

    public PhotoView(ImageService imageService, ProductServImpl productServ, DamagereportServ damagereportServ, MovementServ movementServ) {
        this.imageService=imageService;
        this.damagereportServ = damagereportServ;
        this.movementServ = movementServ;
        this.productServ = productServ;
        damagereport = new Damagereport();
        imageContainer = new VerticalLayout();
        imageContainer.setWidth("200px");
        imageContainer.setHeight("200px");
        imageContainer.getStyle().set("overflow-x", "auto");
        updateList();
        add(imageContainer);
        initUploaderImage();
        configureGrid();
        damagereportServ.getAll().forEach(System.out::println);
        grid.setItems(damagereportServ.getAll());
        add(getContent());
    }

    private void updateList () {
        grid.setItems(damagereportServ.getAll());
    }
    private Component getContent() {
        H5 logo = new H5("Damage Reports ");
        VerticalLayout content = new VerticalLayout(logo, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
    private void configureGrid() {
        grid.setColumns("productName", "productID");
        grid.addColumn(damagereport1 -> {
            Movement movement= movementServ.getById(Long.valueOf(damagereport1.getMovementID()));
            return movement.getLocation();
        }).setHeader("Location");
        grid.addColumn(damagereport1 -> {
            Movement movement= movementServ.getById(Long.valueOf(damagereport1.getMovementID()));
            return movement.getIn_progress_timestamp();
        }).setHeader("Timestamp");
        grid.addColumn(damagereport1 -> {
            Movement movement= movementServ.getById(Long.valueOf(damagereport1.getMovementID()));
            return movement.getIn_progress_user();
        }).setHeader("In_progress_user");
        grid.addComponentColumn(damagereport1 ->{
            Image img= imageService.generateImage(damagereport1);
            img.setHeight("200px");
            img.setWidth("200px");
            return img;
        }
        ).setHeader("Photo");

    }

    private void initUploaderImage() {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg","image/jpg", "image/png", "image/gif");

        upload.addSucceededListener(event -> {
            String attachmentName = event.getFileName();
            try {

                BufferedImage inputImage = ImageIO.read(buffer.getInputStream(attachmentName));
                ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
                ImageIO.write(inputImage, "png", pngContent);
                saveDamagePicture(pngContent.toByteArray(), product.getName(),product.getProduct_ID(), movement);
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
        damagereportServ.savereport(damagereport);

    }

    private void showImage() {
        Image image = imageService.generateImage(damagereport);
        image.setHeight("100%");
        imageContainer.removeAll();
        imageContainer.add(image);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        movement=movementServ.getById(Long.valueOf(beforeEnterEvent.getRouteParameters().get("movementID").get()));
        product=productServ.getByID(movement.getProduct_ID());
    }
}

