package com.dashboardwms.views;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import ru.xpoft.vaadin.VaadinView;

import com.dashboardwms.DashboardwmsUI;
import com.dashboardwms.components.AdministracionUsuariosPanel;
import com.dashboardwms.components.CountryStatisticsPanel;
import com.dashboardwms.components.DailyStatisticsPanel;
import com.dashboardwms.components.DashboardMenu;
import com.dashboardwms.components.HomePanel;
import com.dashboardwms.components.LiveDataLayout;
import com.dashboardwms.components.MobileStatisticsPanel;
import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Servidor;
import com.dashboardwms.exceptions.PerdidaCredencialesException;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.CargaImagenService;
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
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/*
 * Dashboard MainView is a simple HorizontalLayout that wraps the menu on the
 * left and creates a simple container for the navigator on the right.
 */
@SuppressWarnings("serial")

@Component("MainView")
@Scope("prototype")
@VaadinView(DashboardwmsUI.MAINVIEW)
public class MainView extends VerticalLayout implements View  {
	
	

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
	
	@Autowired
	CargaImagenService cargaService;

	 HorizontalLayout layoutContent = new HorizontalLayout();
   private final DashboardMenu menu = new DashboardMenu();
   private final LiveDataLayout liveDataLayout = new LiveDataLayout();
   private final CountryStatisticsPanel countryStatisticsPanel = new CountryStatisticsPanel();
   DailyStatisticsPanel dailyStatisticsPanel = new DailyStatisticsPanel();
   HomePanel homePanel = new HomePanel();
   MobileStatisticsPanel mobileStatisticsPanel = new MobileStatisticsPanel();
   AdministracionUsuariosPanel  administracionUsuariosPanel = new AdministracionUsuariosPanel();

   String emisora = (String) VaadinSession.getCurrent().getAttribute("emisora");

	String usuario  = (String) VaadinSession.getCurrent().getAttribute("usuario");  
	
	String appMovil  = (String)  VaadinSession.getCurrent().getAttribute("appMovil");
	
	List<String> listaEmisoras = (List<String>) VaadinSession.getCurrent().getAttribute("listaEmisoras");

    @PostConstruct
	public void PostConstruct() {
    	
    	setMargin(false);
    	setSpacing(false);
    
    	
    	try {
    		    

            
    		Responsive.makeResponsive(this);
    		
    	final	LookupService cl = new LookupService(Utilidades.LOCATIONS_DB,LookupService.GEOIP_MEMORY_CACHE);
		countryStatisticsPanel.setClienteService(clienteService);
		countryStatisticsPanel.setLookupService(cl);
		administracionUsuariosPanel.setCargaService(cargaService);
		liveDataLayout.cl = cl;
		homePanel.setEmisora(emisora);
		homePanel.setAplicacionService(aplicacionService);
		liveDataLayout.setAplicacionService(aplicacionService);
   	dailyStatisticsPanel.setAplicacionService(aplicacionService);
    	dailyStatisticsPanel.setClienteService(clienteService);
    	dailyStatisticsPanel.setEmisora(emisora);
    	countryStatisticsPanel.setEmisora(emisora);
    	administracionUsuariosPanel.setUsuarioService(usuarioService);
    	administracionUsuariosPanel.setXLSReadingService(xlsReadingService);

    	administracionUsuariosPanel.setAplicacionService(aplicacionService);
    	
    	setSizeFull();
try{

    menu.setVariables(emisora, appMovil, usuario, xlsReadingService, usuarioService, listaEmisoras, aplicacionService);
}catch(PerdidaCredencialesException e){
	UI.getCurrent().getNavigator().navigateTo("");
}

        
        Responsive.makeResponsive(menu);
        
        mobileStatisticsPanel.setXLSReadingService(xlsReadingService);
       
        mobileStatisticsPanel.setAppMovil(appMovil);
        
        menu.botonAdministracion.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				layoutContent.removeComponent(mobileStatisticsPanel);
				layoutContent.removeComponent(dailyStatisticsPanel);
				layoutContent.removeComponent(liveDataLayout);

				layoutContent.removeComponent(homePanel);
				layoutContent.removeComponent(countryStatisticsPanel);
				administracionUsuariosPanel.fillTable();
				layoutContent.addComponent(administracionUsuariosPanel);
				layoutContent.setExpandRatio(administracionUsuariosPanel,  1.0f);
			}
		});
        
        menu.botonEstadisticasPaises.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				layoutContent.removeComponent(mobileStatisticsPanel);
				layoutContent.removeComponent(dailyStatisticsPanel);
				layoutContent.removeComponent(liveDataLayout);

				layoutContent.removeComponent(homePanel);
				layoutContent.removeComponent(administracionUsuariosPanel);
				countryStatisticsPanel.cboxPeriodo.setValue(Utilidades.ULTIMO_MES);
				layoutContent.addComponent(countryStatisticsPanel);
				layoutContent.setExpandRatio(countryStatisticsPanel,  1.0f);
			}
		});
        
        menu.botonEstadisticasMoviles.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				layoutContent.removeComponent(dailyStatisticsPanel);
				layoutContent.removeComponent(liveDataLayout);
				layoutContent.removeComponent(countryStatisticsPanel);

				layoutContent.removeComponent(homePanel);
				layoutContent.removeComponent(administracionUsuariosPanel);
			try {
				mobileStatisticsPanel.fillData();
			} catch (InvalidResultSetAccessException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			layoutContent.addComponent(mobileStatisticsPanel);
			
			layoutContent.setExpandRatio(mobileStatisticsPanel,  1.0f);
			}
		});
        
        menu.botonOyentesDia.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				

				layoutContent.removeComponent(mobileStatisticsPanel);
				layoutContent.removeComponent(countryStatisticsPanel);
				layoutContent.removeComponent(liveDataLayout);

				layoutContent.removeComponent(homePanel);
				layoutContent.removeComponent(administracionUsuariosPanel);
			try {
				dailyStatisticsPanel.fillData();
			} catch (InvalidResultSetAccessException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			layoutContent.addComponent(dailyStatisticsPanel);
			
			layoutContent.setExpandRatio(dailyStatisticsPanel,  1.0f);
			}
		});
        
        menu.botonHome.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				layoutContent.removeComponent(mobileStatisticsPanel);
				layoutContent.removeComponent(countryStatisticsPanel);
				layoutContent.removeComponent(liveDataLayout);
				layoutContent.removeComponent(dailyStatisticsPanel);
				layoutContent.removeComponent(administracionUsuariosPanel);
				try {
			    	   Servidor servidor = new Servidor();
						servidor = xmlConnectionService.getLiveData();
						for (Aplicacion aplicacion : servidor.getListaAplicaciones()) {
							if(aplicacion.getNombre().equalsIgnoreCase(emisora)){

								homePanel.setAplicacion(aplicacion);
								break;
							}
						}
					homePanel.fillData();
				} catch (InvalidResultSetAccessException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				layoutContent.addComponent(homePanel);

				layoutContent.setExpandRatio(homePanel,  1.0f);
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

					layoutContent.removeComponent(mobileStatisticsPanel);
					layoutContent.removeComponent(dailyStatisticsPanel);
					layoutContent.removeComponent(countryStatisticsPanel);
					layoutContent.removeComponent(homePanel);
					layoutContent.removeComponent(administracionUsuariosPanel);
					layoutContent.addComponent(liveDataLayout);
					layoutContent.setExpandRatio(liveDataLayout, 1.0f);
		   		
					liveDataLayout.addStyleName("view-content");
					liveDataLayout.setSizeFull();
					
		      
				
			}
		});
		
        layoutContent.addComponent(menu);
//        	try {
//				Servidor servidor = new Servidor();
//				servidor = xmlConnectionService.getLiveData();
//				for (Aplicacion aplicacion : servidor.getListaAplicaciones()) {
//					if(aplicacion.getNombre().equalsIgnoreCase(emisora)){
//
//						liveDataLayout.fillTable(aplicacion);
//						break;
//					}
//				}
//			} catch (ParserConfigurationException | SAXException
//					| IOException | NullPointerException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//            addComponent(liveDataLayout);
//
//               setExpandRatio(liveDataLayout, 1.0f);
               try {
            	   
            	   Servidor servidor = new Servidor();
				servidor = xmlConnectionService.getLiveData();
				if(servidor.getListaAplicaciones()!=null)
				for (Aplicacion aplicacion : servidor.getListaAplicaciones()) {
					if(aplicacion.getNombre().equalsIgnoreCase(emisora)){

						homePanel.setAplicacion(aplicacion);
						break;
					}
				}
				homePanel.fillData();
			} catch (InvalidResultSetAccessException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               

           	
           	layoutContent.setSizeFull();
           	layoutContent.addStyleName("mainview");
               layoutContent.addComponent(homePanel);

               layoutContent.setExpandRatio(homePanel, 1.0f);

              	addComponent(layoutContent);
               HorizontalLayout footer = new HorizontalLayout();
           	footer = buildTitle();
           	footer.setHeight("70px");
           	addComponent(footer);
           	setExpandRatio(layoutContent, 1f);
               
    	} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        
    	
    	liveDataLayout.cboxPeriodo.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
			
					String periodo = (String) event.getProperty().getValue();
					layoutContent.removeComponent(countryStatisticsPanel);
					layoutContent.removeComponent(liveDataLayout);
					
					dailyStatisticsPanel.cboxPeriodo.setValue(periodo);
				
					layoutContent.addComponent(dailyStatisticsPanel);
					
					layoutContent.setExpandRatio(dailyStatisticsPanel,  1.0f);
					 menu.clearMenuSelection();
						menu.botonOyentesDia.addStyleName(Utilidades.STYLE_SELECTED);
					}
			
				
			
		});
    }

    private HorizontalLayout buildTitle() {
   
   	
   	Embedded logoEmbedded = new Embedded();
   	logoEmbedded.setSource(new ThemeResource("img/plugstreaming.png"));
   
    	logoEmbedded.setHeight("60px");
    	
      HorizontalLayout logoWrapper = new HorizontalLayout(logoEmbedded);
      logoWrapper.setHeight("70px");
      logoWrapper.setMargin(false);
      logoWrapper.setComponentAlignment(logoEmbedded, Alignment.TOP_CENTER);
      logoWrapper.setExpandRatio(logoEmbedded, 1f);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }



    

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("VIEWMAIN");
		
	}
}
