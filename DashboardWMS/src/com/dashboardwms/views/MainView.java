package com.dashboardwms.views;

import java.io.IOException;
import java.text.ParseException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import ru.xpoft.vaadin.VaadinView;

import com.dashboardwms.DashboardwmsUI;
import com.dashboardwms.components.CountryStatisticsPanel;
import com.dashboardwms.components.DailyStatisticsPanel;
import com.dashboardwms.components.DashboardMenu;
import com.dashboardwms.components.LiveDataLayout;
import com.dashboardwms.components.MobileStatisticsPanel;
import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Servidor;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.ClienteService;
import com.dashboardwms.service.UsuarioService;
import com.dashboardwms.service.XLSReadingService;
import com.dashboardwms.service.XMLConnectionService;
import com.dashboardwms.utilities.Utilidades;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
	AplicacionService aplicacionService;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	XLSReadingService xlsReadingService;

   private final DashboardMenu menu = new DashboardMenu();
   private final LiveDataLayout liveDataLayout = new LiveDataLayout();
   private final CountryStatisticsPanel countryStatisticsPanel = new CountryStatisticsPanel();
   DailyStatisticsPanel dailyStatisticsPanel = new DailyStatisticsPanel();
   MobileStatisticsPanel mobileStatisticsPanel = new MobileStatisticsPanel();

   String emisora = (String) VaadinSession.getCurrent().getAttribute("emisora");

	String usuario  = (String) VaadinSession.getCurrent().getAttribute("usuario");  
    @PostConstruct
	public void PostConstruct() {
    	
    	
	     
    	
    	
    	try {
    		    

            
    		Responsive.makeResponsive(this);
    		
    	final	LookupService cl = new LookupService(Utilidades.LOCATIONS_DB,LookupService.GEOIP_MEMORY_CACHE);
		countryStatisticsPanel.setClienteService(clienteService);
		countryStatisticsPanel.setLookupService(cl);
		liveDataLayout.cl = cl;
		liveDataLayout.setAplicacionService(aplicacionService);
   	dailyStatisticsPanel.setAplicacionService(aplicacionService);
    	dailyStatisticsPanel.setClienteService(clienteService);
    	dailyStatisticsPanel.setEmisora(emisora);
    	countryStatisticsPanel.setEmisora(emisora);
        setSizeFull();
        addStyleName("mainview");
        menu.setItemTexto(emisora, usuario);
        menu.setUsuarioService(usuarioService);
        Responsive.makeResponsive(menu);
        
        mobileStatisticsPanel.setXLSReadingService(xlsReadingService);
       
        mobileStatisticsPanel.setEmisora(emisora);
        
        
        menu.botonEstadisticasPaises.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				removeComponent(mobileStatisticsPanel);
				removeComponent(dailyStatisticsPanel);
				removeComponent(liveDataLayout);
				countryStatisticsPanel.cboxPeriodo.setValue(Utilidades.ULTIMO_MES);
				addComponent(countryStatisticsPanel);
				 setExpandRatio(countryStatisticsPanel,  1.0f);
			}
		});
        
        menu.botonEstadisticasMoviles.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				removeComponent(dailyStatisticsPanel);
				removeComponent(liveDataLayout);
			removeComponent(countryStatisticsPanel);
			try {
				mobileStatisticsPanel.fillData();
			} catch (InvalidResultSetAccessException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			addComponent(mobileStatisticsPanel);
			
			 setExpandRatio(mobileStatisticsPanel,  1.0f);
			}
		});
        
        menu.botonOyentesDia.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				

				removeComponent(mobileStatisticsPanel);
			removeComponent(countryStatisticsPanel);
			removeComponent(liveDataLayout);
			try {
				dailyStatisticsPanel.fillData();
			} catch (InvalidResultSetAccessException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			addComponent(dailyStatisticsPanel);
			
			 setExpandRatio(dailyStatisticsPanel,  1.0f);
			}
		});
        
        menu.botonTiempoReal.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
		   		
		   		
					try {
						Servidor servidor = new Servidor();
						servidor = xmlConnectionService.getLiveData();
						for (Aplicacion aplicacion : servidor.getListaAplicaciones()) {
							if(aplicacion.getNombre().equalsIgnoreCase(emisora)){

								liveDataLayout.fillTable(aplicacion);
								break;
							}
						}
					} catch (ParserConfigurationException | SAXException
							| IOException | NullPointerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					removeComponent(mobileStatisticsPanel);
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
				for (Aplicacion aplicacion : servidor.getListaAplicaciones()) {
					if(aplicacion.getNombre().equalsIgnoreCase(emisora)){

						liveDataLayout.fillTable(aplicacion);
						break;
					}
				}
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
        
    	
    	liveDataLayout.cboxPeriodo.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
			
					String periodo = (String) event.getProperty().getValue();
					removeComponent(countryStatisticsPanel);
					removeComponent(liveDataLayout);
					
					dailyStatisticsPanel.cboxPeriodo.setValue(periodo);
				
					addComponent(dailyStatisticsPanel);
					
					 setExpandRatio(dailyStatisticsPanel,  1.0f);
					 menu.clearMenuSelection();
						menu.botonOyentesDia.addStyleName(Utilidades.STYLE_SELECTED);
					}
			
				
			
		});
    }




    

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("VIEWMAIN");
		
	}
}
