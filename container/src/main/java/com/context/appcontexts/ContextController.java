package com.context.appcontexts;

import com.context.annotations.Controller;
import com.context.annotations.GetRequest;
import com.context.annotations.PostRequest;
import com.context.enums.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class ContextController extends Context {
    public ContextController(Map<Class<?>, Object> components, String scanPacketPath) {
        super(components, scanPacketPath);
    }

    @Override
    public void createObjectsAndPut(Set<Class<?>> allClassNames) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Class<?> clazz : allClassNames.stream().toList()) {

            Annotation annotation = clazz.getAnnotation(Controller.class);

            if (annotation != null) {

                Object instance = createObjectByClass(clazz);
                components.put(clazz, instance);

            }
        }
    }

    public void useControllerByMethodAndURI(RequestMethod method, String uri) {
        switch (method){
            case GET -> {
                invokeMethodByPath(uri, GetRequest.class);
            }
            case POST -> {
                invokeMethodByPath(uri, PostRequest.class);
            }
        }
    }

    private void invokeMethodByPath(String path, Class<? extends Annotation> annotationClass) {
        for (Class<?> controllerClass : components.keySet()) {
            Method[] methods = controllerClass.getDeclaredMethods();

            for (Method method : methods) {
                try {
                    if (method.isAnnotationPresent(annotationClass)) {
                        Annotation requestAnnotation = method.getAnnotation(annotationClass);
                        String annotationPath = (String) annotationClass.getDeclaredMethod("path").invoke(requestAnnotation);

                        if (annotationPath.equals(path)) {
                                Object instance = controllerClass.getDeclaredConstructor().newInstance();
                                method.invoke(instance);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
