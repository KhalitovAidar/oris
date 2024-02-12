package com.context;

import com.context.person.PersonService;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Application application = new Application();
        application.run();
    }
}
