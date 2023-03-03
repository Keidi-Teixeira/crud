package com.keidi.application.Exception;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpServletResponse;

@Route(value = "error")
public class CustomErrorView extends VerticalLayout implements InterfaceErrorView {

    public CustomErrorView() {
        Label errorLabel = new Label();
        Button backButton = new Button("Voltar para a p\u00E1gina inicial", e -> getUI().ifPresent(ui -> ui.navigate("")));

        add(new H1("Ocorreu um erro!"));
        add(errorLabel, backButton);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();
    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<?> parameter) {
        String errorMessage = parameter.getCustomMessage();
        Label errorLabel = new Label(errorMessage);
        add(errorLabel);
        return HttpServletResponse.SC_NOT_FOUND;
    }

}
