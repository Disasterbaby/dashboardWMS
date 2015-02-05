package com.dashboardwms.views;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import ru.xpoft.vaadin.VaadinView;

import com.dashboardwms.DashboardwmsUI;
import com.dashboardwms.components.CountryStatisticsPanel;
import com.dashboardwms.components.DailyStatisticsPanel;
import com.dashboardwms.components.DashboardMenu;
import com.dashboardwms.components.LiveDataLayout;
import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Servidor;
import com.dashboardwms.geoip.Location;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.service.ClienteService;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.XMLConnectionService;
import com.dashboardwms.utilities.Utilidades;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;

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
	AplicacionService aplicacionService;
	
	@Autowired
	ClienteService clienteService;
	

   private final DashboardMenu menu = new DashboardMenu();
   private final LiveDataLayout liveDataLayout = new LiveDataLayout();
   private final CountryStatisticsPanel countryStatisticsPanel = new CountryStatisticsPanel();
   DailyStatisticsPanel dailyStatisticsPanel = new DailyStatisticsPanel();
    
    @PostConstruct
	public void PostConstruct() {
    	
    	try {
    		    
		
    		Responsive.makeResponsive(this);
    		
    		liveDataLayout.aplicacionService = aplicacionService;
    		
    	final	LookupService cl = new LookupService(Utilidades.LOCATIONS_DB,LookupService.GEOIP_MEMORY_CACHE);
		final List<String> listaAplicaciones = aplicacionService.getListaAplicacionesDistinct();
		countryStatisticsPanel.setClienteService(clienteService);
		countryStatisticsPanel.setLookupService(cl);
		liveDataLayout.cl = cl;
		countryStatisticsPanel.fillComboBox(listaAplicaciones);
    	dailyStatisticsPanel.setAplicacionService(aplicacionService);

        setSizeFull();
        addStyleName("mainview");

        Responsive.makeResponsive(menu);
        
        menu.botonEstadisticasPaises.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {

				removeComponent(dailyStatisticsPanel);
				removeComponent(liveDataLayout);
				addComponent(countryStatisticsPanel);
				 setExpandRatio(countryStatisticsPanel, 1.0f);
			}
		});
        
        menu.botonOyentesDia.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
			System.out.println("oyentes dia");
			removeComponent(countryStatisticsPanel);
			removeComponent(liveDataLayout);
			dailyStatisticsPanel.fillData(new Date(), null);
			addComponent(dailyStatisticsPanel);
			 setExpandRatio(dailyStatisticsPanel, 1.0f);
			}
		});
        
        menu.botonTiempoReal.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("tiemporial");
		
		   		
		   		
					try {
						Servidor servidor = new Servidor();
						servidor = xmlConnectionService.getLiveData();
						liveDataLayout.fillComboBox(servidor);
					} catch (ParserConfigurationException | SAXException
							| IOException | NullPointerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					removeComponent(dailyStatisticsPanel);
					removeComponent(countryStatisticsPanel);
					addComponent(liveDataLayout);
					 setExpandRatio(liveDataLayout, 1.0f);
		   		
					liveDataLayout.addStyleName("view-content");
					liveDataLayout.setSizeFull();
					
		      
				
			}
		});
		
            addComponent(menu);
        	try {
				Servidor servidor = new Servidor();
				servidor = xmlConnectionService.getLiveData();
				liveDataLayout.fillComboBox(servidor);
			} catch (ParserConfigurationException | SAXException
					| IOException | NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            addComponent(liveDataLayout);
        
               setExpandRatio(liveDataLayout, 1.0f);
               
               
               
    	} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        
    }
        

   
   
    

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("HOLAVIEWMAIN");
		
	}
}
