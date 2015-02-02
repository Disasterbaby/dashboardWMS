package com.dashboardwms.views;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import ru.xpoft.vaadin.VaadinView;

import com.dashboardwms.DashboardwmsUI;
import com.dashboardwms.components.DashboardMenu;
import com.dashboardwms.components.LiveDataLayout;
import com.dashboardwms.domain.Servidor;
import com.dashboardwms.service.DashboardService;
import com.dashboardwms.service.XMLConnectionService;
import com.dashboardwms.utilities.Utilidades;
import com.geoip.LookupService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;

/*
 * Dashboard MainView is a simple HorizontalLayout that wraps the menu on the
 * left and creates a simple container for the navigator on the right.
 */
@SuppressWarnings("serial")

@Component("MainView")
@Scope("prototype")
@VaadinView(DashboardwmsUI.MAINVIEW)
public class MainView extends HorizontalLayout implements View {

	@Autowired
	XMLConnectionService xmlConnectionService;
	
	@Autowired
	DashboardService dashboardService;
	
    
   private final DashboardMenu menu = new DashboardMenu();
    
    @PostConstruct
	public void PostConstruct() {
    	
    	 LiveDataLayout liveData = new LiveDataLayout(dashboardService);
    	try {
    	
        setSizeFull();
        addStyleName("mainview");

        addComponent(menu);
       
    	LookupService cl;
		
			cl = new LookupService(Utilidades.LOCATIONS_DB,LookupService.GEOIP_MEMORY_CACHE);
	
   		Servidor servidor = new Servidor();
   		
			servidor = xmlConnectionService.getLiveData();
	
   		
        liveData.addStyleName("view-content");
        liveData.setSizeFull();
        liveData.fillComboBox(servidor, dashboardService, cl);
        addComponent(liveData);
        
        setExpandRatio(liveData, 1.0f);
	} catch (ParserConfigurationException | SAXException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
    }

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("HOLAVIEWMAIN");
		
	}
}
