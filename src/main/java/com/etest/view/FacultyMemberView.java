/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view;

import com.etest.common.CommonTextField;
import com.etest.model.Users;
import com.etest.service.FacultyService;
import com.etest.serviceprovider.facultyServiceImpl;
import com.etest.view.systemadministration.FacultyMainUI;
import com.etest.view.systemadministration.datagrid.FacultyDataGrid;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field.ValueChangeEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class FacultyMemberView extends VerticalLayout implements View {
    
    public FacultyMemberView() {
        setWidth("100%");
        
        addComponent(new FacultyMainUI());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO
    }
    
}
