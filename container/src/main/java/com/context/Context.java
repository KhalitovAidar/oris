package com.context;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import com.context.annotations.Component;
import com.context.annotations.Inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Context {

    private Map<Class<?>, Object> components;
    private String scanPacketPath;

    public Context(Map<Class<?>, Object> components, String scanPacketPath) {
        this.components = components;
        this.scanPacketPath = scanPacketPath;
    }

    /**
     *
     * @param className = ru.itis.dis205.lab2_1.person.PersonService
     * @return
     */
    public Object getObjectByName(String className) {
        return components.get(className);
    }

    public Set<Class<?>> scanComponent() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        Package pkg = Package.getPackage(scanPacketPath);
        Reflections reflections = new Reflections(scanPacketPath, new SubTypesScanner(false));
        Set<Class<?>> allClassNames = reflections.getSubTypesOf(Object.class);

        return allClassNames;
    }

    private void createObjectsAndPut(Set<Class<?>> allClassNames) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Class<?> clazz : allClassNames.stream().toList()) {
            Annotation annotation = clazz.getAnnotation(Component.class);
            if (annotation != null) {
                Object instance = createObjectByClass(clazz);
                components.put(clazz, instance);
            }
        }
    }

    public void findAndInitInjectedFields() {
        for(Class<?> clazz: components.keySet()) {
            List<Field> fields = List.of(clazz.getFields());
            for (Field field: fields) {
                if (field.isAnnotationPresent(Inject.class)) {

                }
            }
        }
    }

    private Object createObjectByClass(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Constructor<?> constructor = clazz.getConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }
}
