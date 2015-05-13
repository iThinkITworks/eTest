/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.common;

import com.vaadin.ui.TextField;

/**
 *
 * @author jetdario
 */
public class CommonTextField extends TextField {

    public CommonTextField(String inputPrompt, String caption) {
        setCaption(caption);
        setWidth("100%");
        setInputPrompt(inputPrompt);
        addStyleName("small");
    }
    
}
