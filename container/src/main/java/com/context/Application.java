package com.context;

import com.context.appcontexts.Context;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

//@Component
public class Application {
    public void run() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Context context = new Context(new HashMap<>(), "com.context");
        Set<Class<?>> allClasses = context.scanComponent();
        context.createObjectsAndPut(allClasses);
        context.findAndInitInjectedFields();
        System.out.println("finish");
    }
}
