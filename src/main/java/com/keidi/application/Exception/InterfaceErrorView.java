package com.keidi.application.Exception;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;

public interface InterfaceErrorView extends HasElement {
    /**
     * Handles a navigation to an error view using the error information provided
     * by an {@link ErrorParameter} instance.
     *
     * @param event the navigation event causing this view to be shown
     * @param parameter the error information provided
     * @return the HTTP status code to be sent to the client
     */

    int setErrorParameter(BeforeEnterEvent event, ErrorParameter<?> parameter);
}
