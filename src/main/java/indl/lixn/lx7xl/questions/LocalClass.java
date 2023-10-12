package indl.lixn.lx7xl.questions;

import java.lang.reflect.Method;

/**
 * @author listen
 **/
public class LocalClass {

    @MyAnnotation
    public void annotatedMethods() {
        System.out.println("Hello from annotated method!");
    }

    public void callAnnotatedMethod() throws Exception {
        Method[] methods = getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyAnnotation.class)) {
                method.invoke(this);
            }
        }
    }

}
