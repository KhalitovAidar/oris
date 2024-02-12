package com.context;

import com.context.person.PersonService;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //PersonService service = new PersonService();

        System.out.println(PersonService.class.getName());
        Context context = new Context(new HashMap<>(), "ru.itis.dis205.lab2_1.person");
        context.scanComponent();
//        Context context = new Context();

//        Application application =
//                (Application)context.getObjectByName(Application.class.getName());
//
//        application.run();
    }
}
