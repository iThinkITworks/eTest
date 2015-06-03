/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.common.CommonComboBox;
import com.etest.common.CommonTextField;
import com.etest.common.CurriculumPropertyChangeListener;
import com.etest.service.SyllabusService;
import com.etest.serviceprovider.SyllabusServiceImpl;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.vaadin.gridutil.renderer.DeleteButtonValueRenderer;

/**
 *
 * @author jetdario
 */
public class TQCoverageMainUI extends VerticalLayout {

    SyllabusService ss = new SyllabusServiceImpl();
    
    Grid grid = new TQCoverageDataGrid();
    ComboBox subject = CommonComboBox.getSubjectFromCurriculum("Select a Subject..");
    TextField totalItems = new CommonTextField("Enter No of Items..", null);
    ComboBox topic = new ComboBox();
    FooterRow footer;
    
    private int syllabusId;
    private String topicStr;
    
    public TQCoverageMainUI() {
        setSizeFull();
        setMargin(true);
//        setSpacing(true);        
                           
        addComponent(buildTQCoverageForms());
        addComponent(grid);
        
        footer = grid.appendFooterRow();
        
        grid.addItemClickListener((ItemClickEvent event) -> {
            Object itemId = event.getItemId();
            Item item = grid.getContainerDataSource().getItem(itemId);
            
            if(event.getPropertyId().toString().equals("Topic")){
                Window sub = getTopicWindow(item);
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }
                sub.addCloseListener((Window.CloseEvent e) -> {
                    calculateFooter();
//                    System.out.println("itemId: "+calculateFooter());
                });
            }            
        });    
        
        grid.getColumn("remove")
                .setRenderer(new DeleteButtonValueRenderer((ClickableRenderer.RendererClickEvent event) -> {
                    Notification.show("Delete "+event.getItemId(), Notification.Type.HUMANIZED_MESSAGE);
                    grid.getContainerDataSource().removeItem(event.getItemId());
                    calculateFooter();
        })).setWidth(100);
    }
    
    Component buildTQCoverageForms(){
        FormLayout form = new FormLayout();
        form.setWidth("500px");
//        form.setMargin(true);
        
        subject.setCaption("Subject: ");
        subject.setWidth("100%");
        subject.setIcon(FontAwesome.BOOK);
        subject.addStyleName(ValoTheme.COMBOBOX_SMALL);
        subject.addValueChangeListener((new CurriculumPropertyChangeListener(topic)));
        form.addComponent(subject);
        
        totalItems.setCaption("No. of Test Items: ");
        totalItems.setWidth("50%");
        totalItems.setIcon(FontAwesome.TAG);
        totalItems.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(totalItems);
        
        Button button = new Button("ADD ROW");
        button.setWidth("50%");
        button.setIcon(FontAwesome.GEAR);
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener((Button.ClickEvent event) -> {
            if(subject.getValue() == null){
                Notification.show("Select a Subject!", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            if(totalItems.getValue() == null || totalItems.getValue().trim().isEmpty()){
                Notification.show("Enter No. of Test Items!", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            grid.addRow(null, null, null, null, null, null, null, null, null, null, null, 
                    null, null, null, null, null, null, null, null, null, null, null, 
                    null, null, null, null, null, null, null, "del");
        });        
        form.addComponent(button);
        
        return form;
    }
    
    void calculateFooter(){
        double avg = 0;
        
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
//            System.out.println("value: "+item.getItemProperty("Hrs Spent").getValue());
            avg = avg + Double.parseDouble(item.getItemProperty("Hrs Spent").getValue().toString());
        }
        
        footer.getCell("Hrs Spent").setText(String.valueOf(avg));
        
//        return avg;
    }
    
    Window getTopicWindow(Item item){
        Window sub = new Window("TOPIC: ");
        sub.setWidth("500px");
        sub.setModal(true);
        sub.center();
        sub.setClosable(false);
        sub.setResizable(false);
        
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setMargin(true);
        v.setSpacing(true);
        
        topic.setInputPrompt("Select a Topic..");
        topic.addStyleName(ValoTheme.COMBOBOX_SMALL);
        topic.setWidth("100%");
        topic.addValueChangeListener((Property.ValueChangeEvent event) -> {
            if(event.getProperty().getValue() == null){                
            } else {
                syllabusId = (int) event.getProperty().getValue();
                topicStr = topic.getItem(topic.getValue()).toString();
            }            
        });
        v.addComponent(topic);
        
        Button button = new Button("CLOSE");
        button.setWidth("50%");
        button.setIcon(FontAwesome.TASKS);
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener((Button.ClickEvent event) -> {
            item.getItemProperty("Topic").setValue(topic.getItem(topic.getValue()).toString());
            item.getItemProperty("Hrs Spent").setValue(ss.getEstimatedTime(getSyllabusId()));
            sub.close();
        });
        v.addComponent(button);
        v.setComponentAlignment(button, Alignment.MIDDLE_RIGHT);
        
        sub.setContent(v);
        sub.getContent().setHeightUndefined();
        
        return sub;
    }
    
    int getSyllabusId(){
        return syllabusId;
    }
    
    String getTopicStr(){
        return topicStr;
    }
}
