package com.company.jmixtest.view.main;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.view.*;
import io.jmix.flowui.ViewNavigators;
import org.springframework.beans.factory.annotation.Autowired;
import com.company.jmixtest.view.bank.*;
@Route("") @ViewController("MainView") @ViewDescriptor("main-view.xml") @AnonymousAllowed
public class MainView extends StandardMainView {
    @Autowired private ViewNavigators navigators;
    @Subscribe(id="balanceButton",subject="clickListener")
    public void balance(com.vaadin.flow.component.ClickEvent<com.vaadin.flow.component.button.Button> e){navigators.view(this,BalanceView.class).navigate();}
    @Subscribe(id="transferButton",subject="clickListener")
    public void transfer(com.vaadin.flow.component.ClickEvent<com.vaadin.flow.component.button.Button> e){navigators.view(this,TransferView.class).navigate();}
    @Subscribe(id="cardsButton",subject="clickListener")
    public void cards(com.vaadin.flow.component.ClickEvent<com.vaadin.flow.component.button.Button> e){navigators.view(this,CardsView.class).navigate();}
}
