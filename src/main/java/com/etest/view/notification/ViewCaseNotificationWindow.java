/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.notification;

import com.etest.service.CellCaseService;
import com.etest.serviceprovider.CellCaseServiceImpl;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class ViewCaseNotificationWindow extends Window {

    CellCaseService ccs = new CellCaseServiceImpl();
    private int cellCaseId;
    
    public ViewCaseNotificationWindow(int cellCaseId) {
        this.cellCaseId = cellCaseId;
        setCaption("CELL CASE");
        setWidthUndefined();
        setHeight("100%");
        setModal(true);
        center();
        
        setContent(buildForms());
    }
    
    VerticalLayout buildForms(){
        VerticalLayout v = new VerticalLayout();
        v.setWidth("700px");
        v.setMargin(true);
        v.setSpacing(true);
        
        Label cellCase = new Label();
        cellCase.setCaption("Case: "+ccs.getCellCaseById(getCellCaseId()).getCaseTopic());
        cellCase.setContentMode(ContentMode.HTML);
        v.addComponent(cellCase);
        
        Button approvalBtn = new Button("Approve CASE");
        approvalBtn.setWidthUndefined();
        approvalBtn.addStyleName(ValoTheme.BUTTON_TINY);
        approvalBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        approvalBtn.addClickListener(buttonClickListener);
        if(ccs.getCellCaseById(getCellCaseId()).getApprovalStatus() == 0){ approvalBtn.setVisible(true); }
        else { approvalBtn.setVisible(false); }
        v.addComponent(approvalBtn);
        
        Label separator = new Label("<HR>");
        separator.setContentMode(ContentMode.HTML);
        v.addComponent(separator);
        
        return v;
    }
    
    int getCellCaseId(){
        return cellCaseId;
    }
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        if(event.getButton().getCaption().equals("Approve CASE")){
            boolean result = ccs.approveCellCase(getCellCaseId());
            if(result){
                close();
            }
        }
    };
}
