package com.example.reflectdemo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RefInvoke {
    private int a;
    public int b;
    private String sa;
    private String sb;
    private static int c = 11;


    private RefInvoke(int a, int b) {
        this.a = a;
        this.b = b;

    }

    public RefInvoke(String a, String b) {
        this.sa = a;
        this.sb = b;
    }


    private void privateMethod(int a, int b) {
        System.out.print("privateMethod" + a + b + "\n");
    }

    public void publicMethod(int a, int b) {
        System.out.print("publicMethod" + a + b + "\n");
    }

    public static void staticPublicMethod(int a, int b) {
        System.out.print("staticPublicMethod" + a + b + "\n");
    }

    private static void staticPrivateMethod(int a, int b) {
        System.out.print("staticPrivateMethod" + a + b + "\n");
    }


    /**
     * 反射出一个构造函数并构建一个对象
     * 1.调用
     *
     * @param className
     * @param pareTyples
     * @param pareVaules
     * @return
     */
    public static Object createObject(String className, Class[] pareTyples, Object[] pareVaules) {

        try {
            Class r = Class.forName(className);
            Constructor constructor = r.getDeclaredConstructor(pareTyples);
            constructor.setAccessible(true);
            return constructor.newInstance(pareVaules);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Class[] p1 = {int.class, int.class};
        Object[] v1 = {1, 2};

        Object object = createObject(RefInvoke.class.getName(), p1, v1);

        Object o1 = invokeInstanceMethod(object, "privateMethod", p1, v1);
        Object o2 = invokeInstanceMethod(object, "publicMethod", p1, v1);
        Object o3 = invokeInstanceMethod(object, "staticPrivateMethod", p1, v1);
        Object o4 = invokeStaticMethod(RefInvoke.class.getName(), "staticPrivateMethod", p1, v1);
        Object o5 = invokeStaticMethod(RefInvoke.class.getName(), "staticPublicMethod", p1, v1);

        Object o6 = getFieldObject(RefInvoke.class.getName(), object, "a");
        Object o7 = getFieldObject(RefInvoke.class.getName(), object, "c"); // 获取静态私有字段
        Object o8 = getFieldObject(RefInvoke.class.getName(), null, "c"); // 获取静态私有字段

        setFiledObject(RefInvoke.class.getName(), object, "a", 28); // 设置某个对象的变量值
        setFiledObject(RefInvoke.class.getName(), null, "c",29); // 设置某个对象的静态变量值


    }

    /**
     * 调用对象的私有或公有方法
     *
     * @param o
     * @param methodName
     * @param pareTyples
     * @param pareValues
     * @return
     */
    public static Object invokeInstanceMethod(Object o, String methodName,
                                              Class[] pareTyples, Object[] pareValues) {
        if (o == null) {
            return null;
        }
        try {

            // getMethods(),该方法是获取本类以及父类或者父接口中所有的公共方法(public修饰符修饰的)
            // getDeclaredMethods(),该方法是获取本类中的所有方法，包括私有的(private、protected、默认以及public)的方法。
            Method method = o.getClass().getDeclaredMethod(methodName, pareTyples); // 可获取私有也可获取公有
            method.setAccessible(true);
            return method.invoke(o, pareValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用类私有或公有静态方法
     *
     * @param className
     * @param methodName
     * @param pareTyples
     * @param pareValues
     * @return
     */
    public static Object invokeStaticMethod(String className, String methodName, Class[] pareTyples, Object[] pareValues) {
        Class objClass = null;
        try {
            objClass = Class.forName(className);
            Method method = objClass.getDeclaredMethod(methodName, pareTyples);
            method.setAccessible(true);
            return method.invoke(null, pareValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取一个对象某个私有或公有字段的值
     *
     * @param className
     * @param o         当获取静态字段时，对象传与不传都可
     * @param filedName
     * @return
     */
    public static Object getFieldObject(String className, Object o, String filedName) {
        Class objClass = null;
        try {
            objClass = Class.forName(className);
            Field field = objClass.getDeclaredField(filedName);
            field.setAccessible(true);
            System.out.print(filedName + field.get(o) + "\n");
            return field.get(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置变量值
     * @param className
     * @param o 如果设置的是静态变量传空和传对应对象都可以
     * @param fieldName
     * @param fieldValues
     */
    public static void setFiledObject(String className, Object o, String fieldName, Object fieldValues) {
        try {
            Class classObj = Class.forName(className);
            Field field = classObj.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(o, fieldValues);
            System.out.print(fieldName + field.get(o) + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
