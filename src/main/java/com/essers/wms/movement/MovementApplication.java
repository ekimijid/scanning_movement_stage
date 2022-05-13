package com.essers.wms.movement;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@PWA(name = "Scanner WMS", shortName = "WMS", offlinePath = "offline.html", offlineResources = {"images/logo.png", "images/offlinelogo.png"})
public class MovementApplication extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(MovementApplication.class, args);
    }

}
