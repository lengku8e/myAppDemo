package reflect.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerTest implements InvocationHandler {
    Object target;

    public  InvocationHandlerTest(Object o) {
        this.target = o;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print("invoke start");
        Object o = method.invoke(target, args);
        System.out.print("invoke end");
        return o;
    }
}
