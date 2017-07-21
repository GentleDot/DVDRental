package net.gentledot.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gentledot on 2017-05-06.
 */
public class Tools {
    // 빈 값을 받을 때 null 을 "" 로 변환
    public static String toEmptyBlank(String nullStr){
        return nullStr == null ? "" : nullStr;
    }

    public static String customToEmptyBlank(String nullStr, String modifiedStr){
        return nullStr == null ? modifiedStr : nullStr;
    }

    public static String webBlankToCustomize(String inputStr, String modifedStr){
        return inputStr == "" ? modifedStr : inputStr;
    }

    // date 형식으로 변환 후 yy-mm-dd 포맷으로 변경
    public static String dateFommatter(String dateStr) throws ParseException {
        return dateFommatter(dateStr, "yy-MM-dd");
    }

    public static String dateFommatter(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        String modifyDate ="";
        String setDateStr = dateStr;
        
        Calendar cal = Calendar.getInstance();
        int curYear = cal.get(Calendar.YEAR);
        
        try {
            if (setDateStr == null || setDateStr == ""){
                setDateStr = (curYear + 100) + "-12-31";
            }
            
            date = sdf.parse(setDateStr);
            modifyDate= sdf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return modifyDate;
    }

    //String date 를 받아서 10자리만 커트
    public static String cuttingDate(String dateStr){
        String customDate = dateStr.substring(0, 10);
        return customDate;
    }

    //숫자를 원하는 포맷으로 변경
    public static String customNum(String strNum, String format){
        int intNum = Integer.parseInt(strNum);
        DecimalFormat df = new DecimalFormat(format);
        String customNum = df.format(intNum);

        return customNum;
    }

    // 입력받은 문자열이 숫자 형식인지 체크
    public static boolean chkInputOnlyNumber(String input){
        char chrInput = 0;
        boolean result = false;

        if (input == ""){
            result = true;
        }else{
            int inputLen = input.length();
            for (int i = 0; i < inputLen; i++ ){
                chrInput = input.charAt(i);
                if(chrInput >= 0x30 && chrInput <= 0x39){
                    result = true;
                }else if (chrInput == 32){
                    result = true;
                }else if (chrInput == 46){
                    result = true;
                }else{
                    result = false;
                    break;
                }

            }
        }

        return result;
    }

    // 입력받은 문자열이 영문인지 체크
    public static boolean chkInputOnlyAlphabet(String input){
        char chrInput = 0;
        boolean result = false;

        int inputLen = input.length();
        for (int i = 0; i < inputLen; i++ ){
            chrInput = input.charAt(i);
            if(chrInput >= 0x61 && chrInput <= 0x7A){
                result = true;
            }else if(chrInput >= 0x41 && chrInput <= 0x5A){
                result = true;
            }else if(chrInput == 32){
                result = true;
            }
            else{
                result = false;
                break;
            }
        }

        return result;
    }

    // 입력받은 문자열이 영문소문자인지 체크
    public static boolean chkInputOnlyLowerAlphabet(String input){
        char chrInput = 0;
        boolean result = false;

        int inputLen = input.length();
        for (int i = 0; i < inputLen; i++ ){
            chrInput = input.charAt(i);
            if(chrInput >= 0x61 && chrInput <= 0x7A || chrInput == 32){
                result = true;
            }else{
                result = false;
                break;
            }
        }

        return result;
    }

    // 입력받은 문자열이 영문대문자인지 체크
    public static boolean chkInputOnlyUpperAlphabet(String input){
        char chrInput = 0;
        boolean result = false;

        int inputLen = input.length();
        for (int i = 0; i < inputLen; i++ ){
            chrInput = input.charAt(i);
            if(chrInput >= 0x41 && chrInput <= 0x5A || chrInput == 32){
                result = true;
            }else {
                result = false;
                break;
            }
        }

        return result;
    }


    // 입력받은 문자열이 한글인지 체크
    public static boolean chkInputOnlyKorean(String input){
        char chrInput = 0;
        boolean result = false;

        int inputLen = input.length();
        for (int i = 0; i < inputLen; i++ ){
            chrInput = input.charAt(i);
            if(chrInput >= 44032 && chrInput <= 55203 || chrInput == 32){
                result = true;
            }else{
                result = false;
                break;
            }

        }

        return result;
    }
}