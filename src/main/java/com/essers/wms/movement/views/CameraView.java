package com.essers.wms.movement.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;

import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

@PermitAll
@Route(value="camera", layout = MainView.class)
public class CameraView extends VerticalLayout {
    private Component previousPhoto;
    private Paragraph photoName;

    public CameraView() {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/*");

        upload.getElement().setAttribute("capture", "environment");

        Div output = new Div();

        upload.addSucceededListener(
                event -> {
                    Component component = createComponent(
                            event.getMIMEType(),
                            event.getFileName(),
                            buffer.getInputStream()
                    );
                    showOutput(event.getFileName(), component, output);
                }
        );

        add(upload, output);
    }

    private Component createComponent(String mimeType, String fileName, InputStream stream) {
        if (mimeType.startsWith("image")) {
            File imagefile=new File(fileName+".png");
            Image image = new Image();
            try {
                byte[] bytes = IOUtils.toByteArray(stream);
                image
                        .getElement()
                        .setAttribute("src", new StreamResource(fileName, () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            image.setMaxWidth("100%");
                            ImageIO.write((RenderedImage) image, "png", imagefile);
                        } finally {
                            reader.dispose();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return image;
        }
        Div content = new Div();
        String text = String.format(
                "Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType,
                Arrays.toString(MessageDigestUtil.sha256(stream.toString()))
        );
        content.setText(text);
        return content;
    }

    private void showOutput(String text, Component content, HasComponents outputContainer) {
        if (photoName != null) {
            outputContainer.remove(photoName);
        }
        if (previousPhoto != null) {
            outputContainer.remove(previousPhoto);
        }
        photoName = new Paragraph(text);
        outputContainer.add(photoName);
        previousPhoto = content;
        outputContainer.add(previousPhoto);
    }
}