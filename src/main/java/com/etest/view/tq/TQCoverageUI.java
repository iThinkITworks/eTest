/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.common.CommonComboBox;
import com.etest.common.CommonTextField;
import com.etest.common.CurriculumPropertyChangeListener;
import com.etest.global.ShowErrorNotification;
import com.etest.model.CellCase;
import com.etest.model.CellItem;
import com.etest.model.ItemKeys;
import com.etest.model.TQCoverage;
import com.etest.model.TQItems;
import com.etest.model.TopicCoverage;
import com.etest.service.CellCaseService;
import com.etest.service.CellItemService;
import com.etest.service.ItemKeyService;
import com.etest.service.SyllabusService;
import com.etest.service.TQCoverageService;
import com.etest.service.TeamTeachService;
import com.etest.serviceprovider.CellCaseServiceImpl;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.serviceprovider.ItemKeyServiceImpl;
import com.etest.serviceprovider.SyllabusServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.serviceprovider.TeamTeachServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.vaadin.gridutil.renderer.DeleteButtonValueRenderer;

/**
 *
 * @author jetdario
 */
public class TQCoverageUI extends VerticalLayout {

    SyllabusService ss = new SyllabusServiceImpl();
    TeamTeachService tts = new TeamTeachServiceImpl();
    TQCoverageService tq = new TQCoverageServiceImpl();
    CellCaseService ccs = new CellCaseServiceImpl();
    CellItemService cis = new CellItemServiceImpl();
    ItemKeyService k = new ItemKeyServiceImpl();
    
    Grid grid = new TQCoverageDataGrid();
    TextField examTitle = new CommonTextField("Enter Exam Title..", null);
    ComboBox subject = CommonComboBox.getSubjectFromCurriculum("Select a Subject..");
    TextField totalItems = new CommonTextField("Enter No of Items..", null);
    ComboBox topic = new ComboBox();
    FooterRow footer;
    
    private int syllabusId;
    private String topicStr;
//    private int bloomsClassId;
    private int totalTestItems;
    private int curriculumId;
    private int teamTeachId;
    
    public enum BloomsClass {
        Remember, Understand, Apply, Analyze, Evaluate, Create
    }
    
    public TQCoverageUI() {
        setSizeFull();
        setMargin(true);      
                           
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
            }        
            
            Window sub;
            if(event.getPropertyId().toString().contains("Pick")){
                boolean isValueInTBNotZero = tq.isValueInTBNotZero(item, CommonUtilities.replaceStringPickToTB(event.getPropertyId()));
                if(!isValueInTBNotZero){
                    Notification.show("There are no Items in Test Bank!", Notification.Type.ERROR_MESSAGE);
                    return;
                } else {                 
                    sub = getPickWindow(item, CommonUtilities.replaceStringPickToTB(event.getPropertyId()));
                    if(sub.getParent() == null){
                        UI.getCurrent().addWindow(sub);
                    }
                    sub.addCloseListener((Window.CloseEvent e) -> {
                        if(tq.calculateTotalPickItemsPerTopic(grid, itemId) > 
                                CommonUtilities.convertStringToDouble(item.getItemProperty("Max Items").getValue().toString())){
                            item.getItemProperty(event.getPropertyId()).setValue(0);
                            ShowErrorNotification.error("Runnint Total is greater than Max Items");
                        } else {
                            item.getItemProperty("Running Total").setValue(tq.calculateTotalPickItemsPerTopic(grid, itemId));
                            footer.getCell("Running Total").setText(String.valueOf(tq.calculateRunningTotal(grid)));
                        }
                    });
                }
            } 
            
            if(event.getPropertyId().toString().equals("Max Items")){
                double value = (double)item.getItemProperty("Max Items").getValue();
                sub = getMaxItemsWindow(item, value);
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }                
            }
        });    
        
        grid.getColumn("remove")
                .setRenderer(new DeleteButtonValueRenderer((ClickableRenderer.RendererClickEvent event) -> {
                    grid.getContainerDataSource().removeItem(event.getItemId());
                    populateGridFooter();
                    footer.getCell("Running Total").setText(String.valueOf(tq.calculateRunningTotal(grid)));
        })).setWidth(100);     
        
        footer.getCell("Topic").setText("Total");        
        footer.setStyleName("align-center");
                
        Button generateTQ = new Button("Generate TQ");
        generateTQ.setWidth("300px");
        generateTQ.addClickListener(buttonClickListener);
        
        addComponent(new Label("\n"));
        addComponent(generateTQ);
        setComponentAlignment(generateTQ, Alignment.MIDDLE_RIGHT);
    }    
    
    Component buildTQCoverageForms(){
        FormLayout form = new FormLayout();
        form.setWidth("500px");
        
        examTitle.setCaption("Exam Title: ");
        examTitle.setWidth("100%");
        examTitle.setIcon(FontAwesome.TAG);
        examTitle.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        form.addComponent(examTitle);
        
        subject.setCaption("Subject: ");
        subject.setWidth("100%");
        subject.setIcon(FontAwesome.BOOK);
        subject.addStyleName(ValoTheme.COMBOBOX_SMALL);
        subject.addValueChangeListener((new CurriculumPropertyChangeListener(topic)));
        form.addComponent(subject);
        
        totalItems.setCaption("No. of Test Items: ");
        totalItems.setWidth("50%");
        totalItems.setValue("0");
        totalItems.setIcon(FontAwesome.TAG);
        totalItems.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        totalItems.addValueChangeListener(fieldValueListener);
        form.addComponent(totalItems);
        
        Button button = new Button("ADD ROW");
        button.setWidth("50%");
        button.setIcon(FontAwesome.GEAR);
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener((Button.ClickEvent event) -> {
            if(examTitle.getValue() == null || examTitle.getValue().trim().isEmpty()){
                Notification.show("Select an Exam Title!", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
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
    
    Window getTopicWindow(Item item){
        Window sub = new Window("TOPIC: ");
        sub.setWidth("500px");
        sub.setModal(true);
        sub.center();
//        sub.setClosable(false);
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
            if(topic.getValue() == null){
                Notification.show("Select a Topic!", Notification.Type.WARNING_MESSAGE);
            } else {
                populateGridRow(item);
                populateGridFooter();
            }                                    
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
    
    int getTotalTestItems(){
        return totalTestItems;
    }
    
    int getCurriculumId(){
        return curriculumId;
    }
        
    int getTeamTeachId(){
        return teamTeachId;
    }
    
//    int getBloomsClassId(){
//        return bloomsClassId;
//    }
    
    Window getPickWindow(Item item, 
            String propertyId){
        Window sub = new Window("Field Value: ");
        sub.setWidth("150px");
        sub.setModal(true);
        sub.center();        
        sub.setResizable(false);
        
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setMargin(true);
        v.setSpacing(true);
        
        TextField field = new CommonTextField("Enter Value..", "Enter a Value: ");
        v.addComponent(field);
        
        Button button = new Button("CLOSE");
        button.setWidth("100%");
        button.setIcon(FontAwesome.TASKS);
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener((Button.ClickEvent event) -> {  
            boolean isNumeric = CommonUtilities.isNumeric(field.getValue().trim());
            if(!isNumeric){
                return;
            }
            
            boolean isGreaterThanInTB = tq.isGreaterThanInTB(item, propertyId, field.getValue().trim());
            if(isGreaterThanInTB){
                Notification.show("Not allowed to exceed in total Items in Test Bank!", Notification.Type.ERROR_MESSAGE);
                return;
            } else {
                item.getItemProperty(CommonUtilities.replaceStringTBToPick(propertyId)).setValue(CommonUtilities.convertStringToInt(field.getValue())); 
                footer.getCell(CommonUtilities.replaceStringTBToPick(propertyId)).setText(String.valueOf(
                        tq.calculateTotalPickItems(grid, CommonUtilities.replaceStringTBToPick(propertyId))
                ));                
            }
            sub.close();
        });
        v.addComponent(button);
        v.setComponentAlignment(button, Alignment.BOTTOM_CENTER);
        
        sub.setContent(v);
        sub.getContent().setHeightUndefined();
        
        return sub;
    }
    
    Window getMaxItemsWindow(Item item, 
            double previousValue){
        Window sub = new Window("Field Value: ");
        sub.setWidth("150px");
        sub.setModal(true);
        sub.center();        
        sub.setResizable(false);
        
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setMargin(true);
        v.setSpacing(true);
        
        TextField field = new CommonTextField("Enter Value..", "Enter a Value: ");
        v.addComponent(field);
        
        Button button = new Button("CLOSE");
        button.setWidth("100%");
        button.setIcon(FontAwesome.TASKS);
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener((Button.ClickEvent event) -> {  
            boolean isNumeric = CommonUtilities.isNumeric(field.getValue().trim());
            if(!isNumeric){
                return;
            }
            
            item.getItemProperty("Max Items").setValue(CommonUtilities.convertStringToDouble(field.getValue()));
            if(tq.calculateTotalMaxItems(grid) == CommonUtilities.convertStringToDouble(totalItems.getValue().trim())){
                footer.getCell("Max Items").setText(String.valueOf(tq.calculateTotalMaxItems(grid)));
            } else {
                item.getItemProperty("Max Items").setValue(previousValue);
                footer.getCell("Max Items").setText(String.valueOf(tq.calculateTotalMaxItems(grid)));
                ShowErrorNotification.warning("Total Max Items should be equal to Total Test Items");
                return;
            }
            
            sub.close();
        });
        v.addComponent(button);
        v.setComponentAlignment(button, Alignment.BOTTOM_CENTER);
        
        sub.setContent(v);
        sub.getContent().setHeightUndefined();
        
        return sub;
    }
    
    Property.ValueChangeListener fieldValueListener = (Property.ValueChangeEvent e) -> {
        boolean isNumeric = CommonUtilities.isNumeric(e.getProperty().getValue().toString());
        if(isNumeric){
            totalTestItems = CommonUtilities.convertStringToInt(e.getProperty().getValue().toString());
        }
    };
    
    Button.ClickListener buttonClickListener = (Button.ClickEvent e) -> {
        boolean isMaxItemsCompareToInputItems = tq.isMaxItemsCompareToInputItems(
                CommonUtilities.convertStringToDouble(footer.getCell("Max Items").getText()), 
                getTotalTestItems());
        if(!isMaxItemsCompareToInputItems){
            ShowErrorNotification.error("Max Items should be equal to Total Input Items!");
            return;
        }
        
        boolean isRunningTotalGreaterThanMaxItemsTotal = tq.isRunningTotalGreaterThanMaxItemsTotal(
                CommonUtilities.convertStringToInt(footer.getCell("Running Total").getText()), 
                CommonUtilities.convertStringToDouble(footer.getCell("Max Items").getText()));
        if(isRunningTotalGreaterThanMaxItemsTotal){
            ShowErrorNotification.error("Running Total and Max Items are not equal!");
            return;
        }

        TopicCoverage coverage = new TopicCoverage();
        coverage.setExamTitle(examTitle.getValue().trim());
        coverage.setCurriculumId((int) subject.getValue());
        coverage.setTeamTeachId(tts.getTeamTeachIdByUserId(CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString())));
        coverage.setTotalHoursCoverage(tq.calculateTotalHourSpent(grid));
        coverage.setSyllabusId(getSyllabusId());
        coverage.setTotalItems(getTotalTestItems());
        
        List<CellItem> cellItemIdList = tq.getItemIdByDiscriminationIndex(grid);
        List<Integer> cellCaseIdList = new ArrayList<>();
        cellItemIdList.stream().map((ci) -> ccs.getCellCaseIdByCellItemId(ci.getCellItemId())).forEach((c) -> {
            cellCaseIdList.add(c.getCellCaseId());
        });
        Set<Integer> s = new LinkedHashSet<Integer>(cellCaseIdList);      
        cellCaseIdList.clear();
        cellCaseIdList.addAll(s);

        Map<Integer, Integer> itemAndKeyMap = null; 
        List<Integer> keyList;
        
        Map<Integer, Map<Integer, Integer>> cellCaseItemKey = new HashMap<>();
        for(Object cellCaseId : cellCaseIdList){  
            itemAndKeyMap = new HashMap<>();
            for(CellItem ci : cellItemIdList){                      
                if((int)cellCaseId == ccs.getCellCaseIdByCellItemId(ci.getCellItemId()).getCellCaseId()){
                    keyList = k.getItemKeyIdsByCellItemId(ci.getCellItemId());                    
                    itemAndKeyMap.put(ci.getCellItemId(), keyList.get(0));
                }
                cellCaseItemKey.put((Integer) cellCaseId, itemAndKeyMap);
            }
        }
        
        TQItems tqItems = new TQItems();
        tqItems.setCellCaseItemKey(cellCaseItemKey);
        boolean result = tq.insertNewTQCoverage(coverage, tqItems, null, grid);
        if(result){
            Notification.show("Successfully Created TQ Coverage!", Notification.Type.HUMANIZED_MESSAGE);
            return;
        }
        
//        Window pdf = new TQViewer(grid,
//                (int) subject.getValue(), 
//                getTotalTestItems());
//        if(pdf.getParent() == null){
//            UI.getCurrent().addWindow(pdf);
//        }
//        Window sub = new TQCoverageWindow(grid,
//                (int) subject.getValue(), 
//                getTotalTestItems());
//        if(sub.getParent() == null){
//            UI.getCurrent().addWindow(sub);
//        }
    };    
        
    void populateGridRow(Item item){
        item.getItemProperty("Topic").setValue(topic.getItem(topic.getValue()).toString());
        item.getItemProperty("Hrs Spent").setValue(ss.getEstimatedTime(getSyllabusId()));
        item.getItemProperty("Re-U(TB)").setValue(cis.getTotalUnanalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Remember.toString())));
        item.getItemProperty("Re-A(TB)").setValue(cis.getTotalAnalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Remember.toString())));
        item.getItemProperty("Un-U(TB)").setValue(cis.getTotalUnanalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Understand.toString())));
        item.getItemProperty("Un-A(TB)").setValue(cis.getTotalAnalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Understand.toString())));
        item.getItemProperty("Ap-U(TB)").setValue(cis.getTotalUnanalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Apply.toString())));
        item.getItemProperty("Ap-A(TB)").setValue(cis.getTotalAnalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Apply.toString())));
        item.getItemProperty("An-U(TB)").setValue(cis.getTotalUnanalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Analyze.toString())));
        item.getItemProperty("An-A(TB)").setValue(cis.getTotalAnalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Analyze.toString())));
        item.getItemProperty("Ev-U(TB)").setValue(cis.getTotalUnanalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Evaluate.toString())));
        item.getItemProperty("Ev-A(TB)").setValue(cis.getTotalAnalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Evaluate.toString())));
        item.getItemProperty("Cr-U(TB)").setValue(cis.getTotalUnanalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Create.toString())));
        item.getItemProperty("Cr-A(TB)").setValue(cis.getTotalAnalyzeItem(syllabusId, tq.getBloomsClassId(BloomsClass.Create.toString())));
    }
    
    void populateGridFooter(){
        footer.getCell("Hrs Spent").setText(String.valueOf(tq.calculateTotalHourSpent(grid)));
        tq.calculateProportion(grid);
        tq.calculateMaxItems(grid, totalItems);
        footer.getCell("Proportion(%)").setText(String.valueOf(tq.calculateTotalProportion(grid)));
        footer.getCell("Max Items").setText(String.valueOf(tq.calculateTotalMaxItems(grid)));
        footer.getCell("Re-U(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "Re-U(TB)")));
        footer.getCell("Re-A(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "Re-A(TB)")));
        footer.getCell("Un-U(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "Un-U(TB)")));
        footer.getCell("Un-A(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "Un-A(TB)")));
        footer.getCell("Ap-U(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "Ap-U(TB)")));
        footer.getCell("Ap-A(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "Ap-A(TB)")));
        footer.getCell("An-U(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "An-U(TB)")));
        footer.getCell("An-A(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "An-A(TB)")));
        footer.getCell("Ev-U(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "Ev-U(TB)")));
        footer.getCell("Ev-A(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "Ev-A(TB)")));
        footer.getCell("Cr-U(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "Cr-U(TB)")));
        footer.getCell("Cr-A(TB)").setText(String.valueOf(tq.getTotalForBloomsClassColumn(grid, "Cr-A(TB)")));
    }
}
