package com.context;

import com.context.appcontexts.ContextController;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        Application application = new Application();
//        application.run();
        Tomcat tomcat = new Tomcat();

        tomcat.setBaseDir("temp");
        Connector conn = new Connector();
        conn.setPort(8090);
        tomcat.setConnector(conn);
        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context tomcatContext = tomcat.addContext(contextPath, docBase);
        String contextsPacketPath = "com.context.person";

        ContextController applicationControllerContext = new ContextController(new HashMap<>(), contextsPacketPath);
        com.context.appcontexts.Context applicationContext = new com.context.appcontexts.Context(new HashMap<>(), contextsPacketPath);

        String servletName = "dispatcherServlet";
        tomcat.addServlet(contextPath, servletName, new DispatcherServlet(applicationControllerContext));

        tomcatContext.addServletMappingDecoded("/*", servletName);

        try {
            tomcat.start();
            tomcat.getServer().await();

            /*
                tomcat.stop()
                tomcat.destroy()
             */
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
