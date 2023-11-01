package org.fudan.asdt2023.modules.edit.command;

public class NumberUtil {
    public static boolean isNumber(String str){
        try{
            int num = Integer.parseInt(str);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static int getNumber(String str){
        return Integer.parseInt(str);
    }
}
