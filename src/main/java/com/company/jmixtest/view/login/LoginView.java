package com.company.jmixtest.view.login;

import com.company.jmixtest.view.main.MainView;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route("login")
@ViewController("LoginView")
@ViewDescriptor("login-view.xml")
@AnonymousAllowed
public class LoginView extends StandardView {
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.forwardTo(MainView.class);
    }
}
