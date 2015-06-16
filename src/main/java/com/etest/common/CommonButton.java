/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.common;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class CommonButton extends Button {

    public CommonButton(String caption) {
        super(caption);
    
        setWidth("100%");
        setCaption(caption);
        setIcon(FontAwesome.SAVE);
        addStyleName(ValoTheme.BUTTON_PRIMARY);
        addStyleName(ValoTheme.BUTTON_SMALL);
    }
    
}
