package com.dashboardwms;

import javax.servlet.ServletException;

import com.dashboardwms.utilities.MySystemMessagesProvider;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;

import ru.xpoft.vaadin.SpringVaadinServlet;

public class DashboardwmsServlet extends SpringVaadinServlet {
   


    @Override
    public void servletInitialized() throws ServletException {
        super.servletInitialized();
       System.out.println("spring vaadin servlet");
       
        getService().addSessionInitListener(new SessionInitListener() {

            public void sessionInit(SessionInitEvent event)
                    throws ServiceException {
                event.getService().setSystemMessagesProvider(new MySystemMessagesProvider());
            }
            
        });
    }
}