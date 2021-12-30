package com.slimtrade.core.utility;

public class SlimUtil {

    public static String enumToString(String input) {
        input = input.replaceAll("_", " ");
        input = input.toLowerCase();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (i == 0 || input.charAt(i - 1) == ' ') {
                builder.append(Character.toUpperCase(input.charAt(i)));
            } else {
                builder.append(input.charAt(i));
            }
        }
        return builder.toString();
    }

//    public <T> intToEnum(Class<T> e, int index){
//
//        e.getFields()
//
//    }

    public static <T> int safeEnumIndex(Class<T> enumClass, int index){
        System.out.println("enum len" + enumClass.getFields().length);
        return 0;
    }

    public static <T> void test(Class<T> e, int index){
        System.out.println("WOW");


    }

}
