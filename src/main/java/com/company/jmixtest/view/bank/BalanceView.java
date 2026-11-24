package com.company.jmixtest.view.bank;
import com.company.jmixtest.bank.service.BankFormsService;
import com.company.jmixtest.view.main.MainView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
@Route(value="balance",layout=MainView.class) @ViewController("Bank.balance") @ViewDescriptor("balance-view.xml") @AnonymousAllowed
public class BalanceView extends StandardView {
    @Autowired private BankFormsService bank;
    @ViewComponent
    private TextField numberField;

    @ViewComponent
    private TextField fioField;

    @ViewComponent
    private Span balanceValue;

    @ViewComponent
    private Span statusValue;

    @ViewComponent
    private Span feedback;

    @ViewComponent
    private Button blockButton;

    @ViewComponent
    private Button unblockButton;

    private
    BankFormsService.Card selected;

    @Subscribe public void init(InitEvent e){
        numberField.setValueChangeMode(com.vaadin.flow.data.value.ValueChangeMode.EAGER);
    }
    @Subscribe("numberField")
    public void numberChanged(com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent<TextField,String> e){
        selected=null;
        fioField.clear();
        balanceValue.setText("");
        statusValue.setText("");
        blockButton.setEnabled(false);
        unblockButton.setEnabled(false);
        feedback.setText("");
    }
    private void display(BankFormsService.Card c){
        selected=c;fioField.setValue(c.fio());
        balanceValue.setText("Баланс: "+BankFormsService.money(c.balance()));
        statusValue.setText(c.active()?"Карта активна":"Карта заблокирована");
        statusValue.setClassName(c.active()?"card-status active":"card-status blocked");
        blockButton.setEnabled(c.active());
        unblockButton.setEnabled(!c.active());
    }
    @Subscribe(id="lookupButton",subject="clickListener")
    public void lookup(com.vaadin.flow.component.ClickEvent<Button> e){
        feedback.setText("");
        try{display(bank.card(numberField.getValue()));}
        catch(BankFormsService.BankException ex){feedback.setText(ex.getMessage());}
    }
    private void change(boolean active){
        feedback.setText("");
        if(selected==null){feedback.setText("Сначала найдите карту");
            return;
        }
        try{display(bank.active(selected.number(),active));
            feedback.setText(active?"Карта разблокирована":"Карта заблокирована");}
        catch(BankFormsService.BankException ex){
            feedback.setText(ex.getMessage());
        }
    }
    @Subscribe(id="blockButton",subject="clickListener")
    public void block(com.vaadin.flow.component.ClickEvent<Button> e){
        change(false);
    }
    @Subscribe(id="unblockButton",subject="clickListener")
    public void unblock(com.vaadin.flow.component.ClickEvent<Button> e){
        change(true);
    }
}
