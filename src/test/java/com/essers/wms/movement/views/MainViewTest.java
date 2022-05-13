package com.essers.wms.movement.views;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MainViewTest {
    private MainView mainView = mock(MainView.class);

    @Test
    void createHeader() {
        mainView.createHeader();
        verify(mainView, times(1)).createHeader();
    }

    @Test
    void createDrawer() {
        mainView.createDrawer();
        verify(mainView, times(1)).createDrawer();
    }

    @Test
    void movementsStatusChange() {
        mainView.movementsStatusChange();
        verify(mainView, times(1)).movementsStatusChange();
    }
}