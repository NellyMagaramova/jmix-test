package com.company.jmixtest.view.bank;
import com.company.jmixtest.bank.service.BankFormsService;
import com.company.jmixtest.view.main.MainView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
@Route(value="cards",layout=MainView.class) @ViewController("Bank.cards") @ViewDescriptor("cards-view.xml") @AnonymousAllowed
public class CardsView extends StandardView {
    @Autowired
    private BankFormsService bank;

    @ViewComponent
    private com.vaadin.flow.component.orderedlayout.VerticalLayout gridBox;

    @ViewComponent
    private Span feedback;

    private Grid<BankFormsService.Card> grid;

    @Subscribe public void init(InitEvent e){
        grid=new Grid<>();
        grid.addColumn(BankFormsService.Card::number).setHeader("Номер карты").setWidth("50px").setFlexGrow(1);
        grid.addColumn(BankFormsService.Card::fio).setHeader("ФИО").setWidth("350px").setFlexGrow(0);
        grid.addColumn(c->c.active()?"Активна":"Заблокирована").setHeader("Статус").setWidth("195px").setFlexGrow(0);
        grid.addColumn(c->BankFormsService.money(c.balance())).setHeader("баланс").setWidth("185px").setFlexGrow(0);
        grid.addClassName("cards-grid");
        grid.setAllRowsVisible(true);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        gridBox.add(grid);
    }
    @Subscribe public void beforeShow(BeforeShowEvent e){load();}
    @Subscribe(id="refreshButton",subject="clickListener")
    public void refresh(com.vaadin.flow.component.ClickEvent<Button> e){load();}
    private void load(){
        feedback.setText("");
        try{List<BankFormsService.Card> items=bank.cards();
            grid.setItems(items);
            if(items.isEmpty())feedback.setText("Пользователей пока нет");}
        catch(BankFormsService.BankException e){
            grid.setItems(List.of());
            feedback.setText(e.getMessage());
        }
    }
}
