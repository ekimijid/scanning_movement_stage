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

    @Test
    public void testGridPopulated() {
        Grid grid=new Grid(Pickinglist.class);
        Assert.assertEquals(Pickinglist.class, grid.getBeanType());
    }

}