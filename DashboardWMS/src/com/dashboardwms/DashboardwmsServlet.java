package com.dashboardwms;

import java.util.Properties;

import javax.servlet.ServletException;

import ru.xpoft.vaadin.SpringVaadinServlet;

import com.dashboardwms.utilities.MySystemMessagesProvider;
import com.dashboardwms.utilities.Utilidades;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;

public class DashboardwmsServlet extends SpringVaadinServlet {
   


    @Override
    public void servletInitialized() throws ServletException {
        super.servletInitialized();
       System.out.println("spring vaadin servlet");
       Properties props = System.getProperties();
       props.setProperty("phantom.exec", Utilidades.PHANTOM_EXEC);
        getService().addSessionInitListener(new SessionInitListener() {

            public void sessionInit(SessionInitEvent event)
                    throws ServiceException {
                event.getService().setSystemMessagesProvider(new MySystemMessagesProvider());
            }
            
        });
    }
}