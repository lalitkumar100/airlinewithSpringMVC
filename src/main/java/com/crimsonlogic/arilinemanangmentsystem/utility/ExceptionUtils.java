package com.crimsonlogic.arilinemanangmentsystem.utility;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
    /**
     * Action for getStackTraceAsString.
     * @param ex input parameter
     * @return String output
     */
    public static String getStackTraceAsString(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}