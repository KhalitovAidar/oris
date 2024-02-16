package com.context.appcontexts;

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

    protected Map<Class<?>, Object> components;
    protected String scanPacketPath;

    public Context(Map<Class<?>, Object> components, String scanPacketPath) {
        this.components = components;
        this.scanPacketPath = scanPacketPath;
    }

    /**
     *
     * @param className = ru.itis.dis205.lab2_1.person.PersonService
     * @return
     */
    private Object getObjectByName(String className) {
        return components.get(className);
    }

    public Set<Class<?>> scanComponent() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections(scanPacketPath, new SubTypesScanner(false));
        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);

        return allClasses;
    }

    public void createObjectsAndPut(Set<Class<?>> allClassNames) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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

            List<Field> fields = List.of(clazz.getDeclaredFields());

            fields.forEach((field) -> {
                if (field.isAnnotationPresent(Inject.class)) {
                    Class<?> injectClass = field.getType();

                    if (components.containsKey(injectClass)) {
                        try {
                            field.setAccessible(true);
                            field.set(components.get(clazz), components.get(injectClass));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
    }

    protected Object createObjectByClass(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Constructor<?> constructor = clazz.getConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }
}