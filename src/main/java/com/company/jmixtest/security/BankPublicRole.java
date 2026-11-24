package com.company.jmixtest.security;

import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "Учебное главное окно", code = BankPublicRole.CODE)
public interface BankPublicRole {
    String CODE = "bank-public";

    @ViewPolicy(viewIds = {"MainView", "LoginView", "Bank.balance", "Bank.transfer", "Bank.cards"})
    @SpecificPolicy(resources = "ui.loginToUi")
    void mainView();
}
