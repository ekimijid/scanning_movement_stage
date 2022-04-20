package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Damagereport;
import com.essers.wms.movement.data.repo.DamagereportRepo;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImageService {

    @Autowired
    private DamagereportRepo damagereportRepo;

    public Image generateImage(Damagereport damagereport) {
        Long id = damagereport.getId();
        StreamResource sr = new StreamResource("report", () ->  {
            Damagereport attached = damagereportRepo.getById(id);
            return new ByteArrayInputStream(attached.getImage());
        });
        sr.setContentType("image/png");
        Image image = new Image(sr, "damage-picture");
        return image;

    }
    public static byte[] getBytesFromFile(String imagePath) throws IOException {
        File file = new File(imagePath);
        return Files.readAllBytes(file.toPath());
    }

}
