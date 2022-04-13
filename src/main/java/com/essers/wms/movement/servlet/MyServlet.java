package com.essers.wms.movement.servlet;

import com.vaadin.flow.server.*;

import javax.servlet.ServletException;

public class MyServlet extends VaadinServlet
        implements SessionInitListener, SessionDestroyListener {

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event)
            throws ServiceException {
        // Do session start stuff here
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        // Do session end stuff here
    }
}
