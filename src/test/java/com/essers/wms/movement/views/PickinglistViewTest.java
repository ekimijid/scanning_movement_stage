package com.essers.wms.movement.views;

import com.essers.wms.movement.TestDataBuilder;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.vaadin.flow.component.grid.Grid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PickinglistViewTest {
    private TestDataBuilder testDataBuilder;
    @Autowired
    PickinglistView pickinglistView;

    @Before
    public void setupData() {
        testDataBuilder = new TestDataBuilder();
    }

    @Test
    public void testGridPopulated() {
        Grid<Pickinglist> grid = pickinglistView.grid;
        Assert.assertEquals(Pickinglist.class, grid.getBeanType());
    }

    @Test
    public void testDoNotRouteToMovementPage() {
        PickinglistView view = mock(PickinglistView.class);
        view.grid = new Grid<>(Pickinglist.class);
        view.grid.setItems(testDataBuilder.getPickinglists());

        verify(view, never()).routerLink(testDataBuilder.getPickinglist());
    }

    @Test
    public void testRouteToMovementPageWhenPickingListSelected() {
        PickinglistView view = mock(PickinglistView.class);
        view.grid = new Grid<>(Pickinglist.class);
        view.grid.setItems(testDataBuilder.getPickinglists());
        view.grid.asSingleSelect().setValue(testDataBuilder.getPickinglist());
        view.routerLink(testDataBuilder.getPickinglist());
        verify(view, times(1)).routerLink(testDataBuilder.getPickinglist());
    }
}