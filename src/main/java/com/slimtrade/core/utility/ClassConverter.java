package com.slimtrade.core.utility;

import java.lang.reflect.Field;

public class ClassConverter<T> {

    public Object[] classToObjectArray(T data, Class<T> targetClass) {
        Field[] fields = targetClass.getFields();
        Object[] objArr = new Object[fields.length];
        try {
            for (int i = 0; i < fields.length; i++) {
                objArr[i] = fields[i].get(data).toString();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return objArr;
    }

}
