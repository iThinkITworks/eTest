/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.utilities;

import com.etest.global.ShowErrorNotification;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView.Content;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class CommonUtilities {
    
    public static int convertStringToInt(String str){
        int val = 0;
        try{
            if(str != null || !str.isEmpty()){
                val = Integer.parseInt(str);
            }
        } catch (Exception e){
            Label label = new Label("cant convert input to String \n"+e.toString());
            ShowErrorNotification.error(label.getValue());
            e.getMessage();
        }
        
        return val;
    }
    
    public static double convertStringToDouble(String str){
        double val = 0;
        if(str != null || !str.isEmpty()){
            val = Double.parseDouble(str);
        }
        
        return val;
    }
    
    public static float convertStringToFloat(String str){
        float val = 0;
        if(str != null || !str.isEmpty()){
            val = Float.parseFloat(str);
        }
        
        return val;
    }
    
    public static boolean checkInputIfInteger(String str){
        boolean result;
        
        try{
            int val = Integer.parseInt(str);
            result = true;           
        } catch (Exception e) {
            result = false;
        }         
        
        return result;
    }
    
    public static boolean checkInputIfDouble(String str){
        boolean result;
        try{
            double val = Double.parseDouble(str);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        
        return result;
    }
    
    public static boolean checkInputIfFloat(String str){
        boolean result;
        try{
            double val = Float.parseFloat(str);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        
        return result;
    }
    
    public static String convertDateFormat(String date){
        DateFormat currentDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        DateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateConvert = null;
        try {
            Date newDate = currentDateFormat.parse(date);
            dateConvert = newDateFormat.format(newDate);
        } catch (ParseException ex) {
            Logger.getLogger(CommonUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateConvert;
    }
    
    public static String convertDateFormatWithTime(String date){
        DateFormat currentDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        DateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateConvert = null;
        try {
            Date newDate = currentDateFormat.parse(date);
            dateConvert = newDateFormat.format(newDate);
        } catch (ParseException ex) {
            Logger.getLogger(CommonUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateConvert;
    }
    
    public static Date parsingDate(String str){
        Date dateToFormat = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateToFormat = (Date) formatter.parse(str);
        } catch (ParseException ex) {
            Logger.getLogger(CommonUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateToFormat;
    }
    
    public static Date parsingDateWithTime(String str){
        Date dateToFormat = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateToFormat = (Date) formatter.parse(str);
        } catch (ParseException ex) {
            Logger.getLogger(CommonUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateToFormat;
    }
    
    public static String capitalizeFirstLetter(String original){
        if(original.length() == 0){
            return original;
        }
        
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
    
    public static boolean isNumeric(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            Label label = new Label("Enter a Numeric Format \n" + e.toString());
            ShowErrorNotification.error(label.getValue());
            return false;
        }
    }
    
    public static String escapeSingleQuote(Object object){
        return object.toString().replace("'", "\'");
    }
    
    public static String addSingleQuoteForQuery(Object object){
        return object.toString().replace("'", "\\'");
    }
    
    public static double roundOffToWholeNumber(double value){
        DecimalFormat df = new DecimalFormat("##0");
        return new Double(df.format(value));
    }
    
    public static String replaceStringTBToPick(Object object){
        return object.toString().replace("TB", "Pick");
    }
    
    public static String replaceStringPickToTB(Object object){
        return object.toString().replace("Pick", "TB");
    }
    
    public static boolean isValueInteger(Object object){
        try{
            Integer.parseInt(object.toString());
            return true;
        } catch (Exception e) {            
            Label label = new Label("Enter an Integer Value \n" + e.toString());
            ShowErrorNotification.error(label.getValue());
            return false;
        }
    }
}
