package com.company.jmixtest.view.bank;

import com.company.jmixtest.bank.service.BankFormsService;
import com.company.jmixtest.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Route(value = "transfer", layout = MainView.class)
@ViewController("Bank.transfer")
@ViewDescriptor("transfer-view.xml")
@AnonymousAllowed
public class TransferView extends StandardView {
    @Autowired
    private BankFormsService bank;

    @ViewComponent
    private TextField fromField;

    @ViewComponent
    private TextField toField;

    @ViewComponent
    private BigDecimalField amountField;

    @ViewComponent
    private Span feedback;

    @ViewComponent
    private Button sendButton;

    @Subscribe
    public void onInit(InitEvent event) {
        fromField.setValueChangeMode(ValueChangeMode.EAGER);
        toField.setValueChangeMode(ValueChangeMode.EAGER);
        amountField.setValueChangeMode(ValueChangeMode.EAGER);
        amountField.setHelperText("Сумма целым числом");
    }

    @Subscribe(id = "sendButton", subject = "clickListener")
    public void send(ClickEvent<Button> event) {
        feedback.setText("");
        feedback.setClassName("feedback");
        BigDecimal amount = amountField.getValue();
        if (amount == null) {
            feedback.setText("Укажите сумму перевода");
            feedback.addClassName("error");
            sendButton.setEnabled(true);
            return;
        }
        sendButton.setEnabled(false);
        try {
            bank.transfer(fromField.getValue(), toField.getValue(), amount);
            feedback.setText("Перевод выполнен: " + BankFormsService.money(amount));
            feedback.addClassName("success");
            amountField.clear();
        } catch (BankFormsService.BankException error) {
            feedback.setText(error.getMessage());
            feedback.addClassName("error");
        } finally {
            sendButton.setEnabled(true);
        }
    }
}
