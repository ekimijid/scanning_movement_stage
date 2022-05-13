package com.essers.wms.movement.views;

import com.vaadin.flow.component.login.LoginForm;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class LoginViewTest {

   @Test
    void loginFormErrorTest() {
        LoginForm loginForm = new LoginForm();
        loginForm.setError(true);
        assertTrue(loginForm.isError());
    }


}