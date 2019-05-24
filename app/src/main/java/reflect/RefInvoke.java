package reflect;

import java.lang.reflect.Constructor;

public class RefInvoke {
    private int a;
    private int b;
    private String sa;
    private String sb;


    private RefInvoke(int a, int b) {
        this.a = a;
        this.b = b;

    }

    public RefInvoke(String a, String b) {
        this.sa = a;
        this.sb = b;
    }



    /**
     * 反射出一个构造函数并构建一个对象
     * 1.调用
     *
     *
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

    public static void main (String [] args) {
        Class[] p1 = {int.class, int.class};
        Object[] v1 = {1, 2};
        Object object = createObject(RefInvoke.class.getName(), p1, v1);

    }



}
