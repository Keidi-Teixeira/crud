package com.keidi.application.views;

import com.keidi.application.Entity.Cliente;
import com.keidi.application.Service.ClienteService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;

import java.io.Serial;
import java.util.function.Consumer;

@Route("")
public class ClienteView extends VerticalLayout {
    public ClienteView(ClienteService service) {
        Grid<Cliente> grid = new Grid<>();
        final GridListDataView<Cliente> gridListDataView = grid.setItems(service.listarTodos());
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(Cliente::getId).setHeader("ID").setResizable(true);
        grid.addColumn(Cliente::getNome).setHeader("Nome").setResizable(true);
        grid.addColumn(Cliente::getEmail).setHeader("Email").setResizable(true);
        grid.addItemDoubleClickListener(selection -> new ClienteFormDialog(selection.getItem(), service, i -> UI.getCurrent().getPage().reload()));

        Button btnExcluir = new Button("Excluir");
        btnExcluir.addClickListener(event -> {
            Cliente cliente = grid.asSingleSelect().getValue();
            service.excluir(cliente.getId());
            gridListDataView.removeItem(cliente);
            gridListDataView.refreshAll();
        });

        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.addClickListener(event -> {
            // Cria uma instância do formulário
            new ClienteFormDialog(new Cliente(), service, c -> {
                gridListDataView.addItem(c);
                gridListDataView.refreshAll();
            });
        });
        add(btnAdicionar, btnExcluir, grid);
    }
    static class ClienteFormDialog extends Dialog {
        @Serial
        private static final long serialVersionUID = 6055099001923416653L;

        public ClienteFormDialog(final Cliente cliente, final ClienteService clienteService, final Consumer<Cliente> consumer) {
            FormLayout formLayout = new FormLayout();

            Binder<Cliente> binder = new Binder<>(Cliente.class);

            // Cria os campos de texto do formulário
            TextField txtNome = new TextField("Nome");
            TextField txtEmail = new TextField("E-mail");

            // Adiciona os campos de texto ao formulário
            formLayout.add(txtNome, txtEmail);

            // Associa cada campo do formulário com um atributo do objeto Cliente
            binder.forField(txtNome).asRequired()
                    .withValidator(new StringLengthValidator("O nome deve ter entre 3 e 50 caracteres", 3, 50))
                    .bind(Cliente::getNome, Cliente::setNome);

            binder.forField(txtEmail).asRequired()
                    .bind(Cliente::getEmail, Cliente::setEmail);

            // Define o objeto que será editado pelo formulário
            binder.setBean(cliente);

            // Abre o diálogo de edição do cliente
            Dialog dialog = new Dialog();
            dialog.add(formLayout);

            // Configura o diálogo para salvar o objeto Cliente quando o botão 'Salvar' for clicado
            Button btnSalvar = new Button("Salvar", event -> {
                if (binder.writeBeanIfValid(cliente)) {
                    consumer.accept(cliente);
                    clienteService.salvar(cliente);
                    dialog.close();
                } else {
                    Notification.show("Preencha todos os campos corretamente.");
                }
            });

            Button cancelButton = new Button("Cancelar", e -> dialog.close());

            dialog.getFooter().add(btnSalvar);
            dialog.getFooter().add(cancelButton);

            // Abre o diálogo
            dialog.open();
        }

    }

}
