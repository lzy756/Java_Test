package Util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflectUtil {
    public static Object getField(Object obj,String name) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void setField(Object obj,String name,Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Object constructObject(String className,Object... args) {
        Class<?>[] parasclass=new Class<?>[args.length];
        try {
            if(args==null||args.length==0){
                return Class.forName(className).getDeclaredConstructor().newInstance();
            }
            for(int i=0;i< args.length;i++){
                parasclass[i]=args[i].getClass();
            }
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getDeclaredConstructor(parasclass);
            return constructor.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
