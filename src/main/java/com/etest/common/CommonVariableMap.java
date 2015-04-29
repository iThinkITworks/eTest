/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class CommonVariableMap {

    public CommonVariableMap() {
    }
    
    public static String getYearLevel(Object key){
        Map yearLevel = new HashMap();
        yearLevel.put(1, "1st Year");
        yearLevel.put(2, "2nd Year");
        yearLevel.put(3, "3rd Year");
        yearLevel.put(4, "4th Year");
        yearLevel.put(5, "5th Year");
        
        return yearLevel.get(key).toString();
    }
    
    public static String getNormCourseOffering(Object key){
        Map normCourseOffering = new HashMap();
        normCourseOffering.put(1, "First Semester");
        normCourseOffering.put(2, "Second Semester");
        
        return normCourseOffering.get(key).toString();
    }
}
