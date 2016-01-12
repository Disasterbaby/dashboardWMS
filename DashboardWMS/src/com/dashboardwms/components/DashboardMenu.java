package com.dashboardwms.components;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.dashboardwms.DashboardwmsUI;
import com.dashboardwms.exceptions.PerdidaCredencialesException;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.ImageUploader;
import com.dashboardwms.service.UsuarioService;
import com.dashboardwms.service.XLSReadingService;
import com.dashboardwms.views.DashboardViewType;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */
@SuppressWarnings("serial")
public final class DashboardMenu extends CustomComponent {

    public static final String ID = "dashboard-menu";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    public ValoMenuItemButton botonEstadisticasPaises;
    public ValoMenuItemButton botonTiempoReal;
    public ValoMenuItemButton botonOyentesDia;
    public ValoMenuItemButton botonAdministracion;
    public ValoMenuItemButton botonEstadisticasMoviles;
    public ValoMenuItemButton botonHome;
    private static final String STYLE_SELECTED = "selected";
    private MenuItem settingsItem;
    private UsuarioService usuarioService;
    private List<String> listaEmisoras;
    private AplicacionService aplicacionService;
    private MenuBar userMenu = new MenuBar();
    final CssLayout menuItemsLayout = new CssLayout();
	final CssLayout menuContent = new CssLayout();
	private String  usuario;
	private String emisora;
    public DashboardMenu() {
   

    	Responsive.makeResponsive(this);
        addStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();
        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
    	
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(userMenu);
        menuContent.addComponent(buildToggleButton());
        buildMenuItems();
        menuContent.addComponent(menuItemsLayout);

      //  menuContent.addComponent(buildTitle());
        
        Responsive.makeResponsive(menuContent);
       

        return menuContent;
    }


    
    public void setVariables(String emisora, String appMovil, String usuario, XLSReadingService xlsReadingService, UsuarioService usuarioService, List<String> listaEmisoras, AplicacionService aplicacionService) throws PerdidaCredencialesException{
    	this.emisora = emisora;
       this.listaEmisoras = listaEmisoras;
    	this.usuario = usuario;
    	this.usuarioService = usuarioService;
    	this.aplicacionService = aplicacionService;
    	if(emisora!=null){
    	if(appMovil!=null){	    	
    	if(xlsReadingService.verificarAppMovil(appMovil))
            menuItemsLayout.addComponent(botonEstadisticasMoviles);
    	}
    	}
    	if(usuario!=null)
        if(usuario.equalsIgnoreCase("admin"))
        	menuItemsLayout.addComponent(botonAdministracion);
    	try{

        	buildUserMenu();
    	}catch(Exception e){
    		throw new PerdidaCredencialesException("Credenciales vacías");
    	}
    }
    
    private void buildUserMenu() {
        userMenu.addStyleName("user-menu");

        settingsItem = userMenu.addItem("", null, null);
        settingsItem.setText(emisora);
        String ruta = usuarioService.getLogo(usuario);
        if(usuario.equalsIgnoreCase("admin")){

        	settingsItem.setIcon(new ThemeResource("img/icoplug.ico"));
        	listaEmisoras = aplicacionService.getListaAplicacionesDistinct();
        	if(emisora.equalsIgnoreCase("todas"))
        	settingsItem.setText(listaEmisoras.get(0));
        }
        	
        
    	if(ruta!=null){
    	FileResource resource = new FileResource(new File(ruta));
    	settingsItem.setIcon(resource);
    	}
    	
        if(listaEmisoras!=null)
        {
        	
        	
        	for (final String emisora : listaEmisoras) {
        		if(!emisora.equalsIgnoreCase(this.emisora))
        		 settingsItem.addItem(emisora, new Command() {
        	            @Override
        	            public void menuSelected(final MenuItem selectedItem) {

        	             	 VaadinSession.getCurrent().setAttribute("emisora", emisora);

        	             	VaadinSession.getCurrent().setAttribute("appMovil", usuarioService.getAppMovil(emisora));
        	               getUI().getNavigator()
        					.navigateTo(DashboardwmsUI.MAINVIEW);
        	              
        	            }
        	        });
			}
        }
        
        settingsItem.addItem("Cambiar Contraseña", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
            	buildModalWindow();
            }
        });
        
        settingsItem.addItem("Cambiar Logo", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
            	buildModalWindowLogo();
            }
        });
        
        
        settingsItem.addItem("Salir", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {

                VaadinSession.getCurrent().setAttribute("emisora", null);
               
                getUI().getPage().setLocation( "" );
                VaadinSession.getCurrent().close();
               
            }
        });
        

    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);
                }
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private void buildMenuItems() {
        menuItemsLayout.addStyleName("valo-menuitems");
        menuItemsLayout.setHeight(100.0f, Unit.PERCENTAGE);
       menuItemsLayout.setWidthUndefined();
       
       	botonHome = new ValoMenuItemButton(DashboardViewType.HOME);
       	botonHome.addClickListener(buttonClickListener);
        botonEstadisticasPaises = new ValoMenuItemButton(DashboardViewType.ESTADISTICAS_PAISES);
        botonTiempoReal = new ValoMenuItemButton(DashboardViewType.TIEMPO_REAL);
        botonHome.addStyleName(STYLE_SELECTED);
        botonTiempoReal.addClickListener(buttonClickListener);
        botonEstadisticasPaises.addClickListener(buttonClickListener);
        botonOyentesDia = new ValoMenuItemButton(DashboardViewType.OYENTES_DIA);
        botonOyentesDia.addClickListener(buttonClickListener);
        botonAdministracion = new ValoMenuItemButton(DashboardViewType.ADMINISTRACION);
        botonAdministracion.addClickListener(buttonClickListener);
        botonEstadisticasMoviles = new ValoMenuItemButton(DashboardViewType.MOVIL);
        botonEstadisticasMoviles.addClickListener(buttonClickListener);
        menuItemsLayout.addComponent(botonHome);
        menuItemsLayout.addComponent(botonTiempoReal);
        menuItemsLayout.addComponent(botonEstadisticasPaises);
        menuItemsLayout.addComponent(botonOyentesDia);
        

    }

    ClickListener buttonClickListener = new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				clearMenuSelection();
				event.getButton().addStyleName(STYLE_SELECTED);
				

			}
		};
    
    
    
    public void clearMenuSelection(){
    	
    	 for (Iterator<Component> it = menuContent.iterator(); it.hasNext();) {
             Component next = it.next();
             if (next instanceof ValoMenuItemButton) {
                 next.removeStyleName(STYLE_SELECTED);
             } 
         }
    	 botonHome.removeStyleName(STYLE_SELECTED);
    	botonEstadisticasPaises.removeStyleName(STYLE_SELECTED);
        botonTiempoReal.removeStyleName(STYLE_SELECTED);
        botonOyentesDia.removeStyleName(STYLE_SELECTED);
        botonEstadisticasMoviles.removeStyleName(STYLE_SELECTED);
        botonAdministracion.removeStyleName(STYLE_SELECTED);
    }

    public final class ValoMenuItemButton extends Button {

     
        public ValoMenuItemButton(final DashboardViewType view) {
           
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(view.getViewName().substring(0, 1).toUpperCase()
                    + view.getViewName().substring(1));
      


        }

 
 
    }
    
    private void buildModalWindow(){
    
    	final Window subWindow = new Window();
    	subWindow.setModal(true);
		subWindow.addStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
	   		subWindow.setCaption("Introduzca la nueva contraseña");
		subWindow.setResizable(false);
		subWindow.setDraggable(false);
		subWindow.setSizeUndefined();
		
		subWindow.center();
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		mainLayout.setSizeUndefined();
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		 VerticalLayout vLayout = new VerticalLayout();
		vLayout.setSizeUndefined();
		vLayout.setSpacing(true);
		vLayout.setMargin(true);
		vLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		 HorizontalLayout fields = new HorizontalLayout();
         fields.setSpacing(true);
         fields.setMargin(true);
         fields.addStyleName("fields");
         CssLayout labels = new CssLayout();
         labels.addStyleName("labels");

         Label welcome = new Label("Introduzca la nueva contraseña");
         welcome.setSizeUndefined();
         welcome.addStyleName(ValoTheme.LABEL_H3);
        
         labels.addComponent(welcome);

         final PasswordField password = new PasswordField();
         password.setIcon(FontAwesome.LOCK);
         password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

         final Button signin = new Button("Procesar");
         signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
         signin.setClickShortcut(KeyCode.ENTER);
         signin.setImmediate(true);
         signin.focus();

         fields.addComponents(password, signin);
         fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
        
         vLayout.addComponent(fields);
         subWindow.setContent(vLayout);
		getUI().addWindow(subWindow); 
       signin.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			if(password.getValue() == null)
				Notification.show("La nueva contraseña no puede ser vacía", Notification.Type.ERROR_MESSAGE);
			else
				{
				
				usuarioService.cambiarPassword(usuario, password.getValue());
				Notification.show("Contraseña modificada exitosamente", Notification.Type.HUMANIZED_MESSAGE);
				subWindow.close();
				}
				}
	});
    }
    
    

    private void buildModalWindowLogo(){
    	final ImageUploader receiver = new ImageUploader(); 
  	   
    		// Create the upload with a caption and set receiver later
    	final	Upload upload = new Upload();
    		upload.setReceiver(receiver);
    		  upload.setButtonCaption("Cargar");
    	         
    		  
//    	         upload.setImmediate(true);
    	         upload.setStyleName(ValoTheme.BUTTON_TINY);
    	     	upload.addSucceededListener(receiver);
    	     	
    	final Window subWindow = new Window();
    	subWindow.setModal(true);
		subWindow.addStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
	   		subWindow.setCaption("Seleccione el nuevo logo");
		subWindow.setResizable(false);
		subWindow.setDraggable(false);
		subWindow.setSizeUndefined();
		
		subWindow.center();
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		mainLayout.setSizeUndefined();
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		 VerticalLayout vLayout = new VerticalLayout();
		vLayout.setSizeUndefined();
		vLayout.setSpacing(true);
		vLayout.setMargin(true);
		vLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		 HorizontalLayout fields = new HorizontalLayout();
         fields.setSpacing(true);
         fields.setMargin(true);
         fields.addStyleName("fields");
         CssLayout labels = new CssLayout();
         labels.addStyleName("labels");

         Label welcome = new Label("Seleccione el nuevo logo");
         welcome.setSizeUndefined();
         welcome.addStyleName(ValoTheme.LABEL_H3);
        
         labels.addComponent(welcome);

         
         fields.addComponents(upload);
         vLayout.addComponent(fields);
         subWindow.setContent(vLayout);
		getUI().addWindow(subWindow); 
	
		upload.addSucceededListener(new SucceededListener() {
			
			@Override
			public void uploadSucceeded(SucceededEvent event) {
				Notification.show("Logo modificado exitosamente");
				File archivo = receiver.file;
				String ruta = null;
				if(archivo!=null)
				{ruta = receiver.file.getAbsolutePath();
				usuarioService.modificarLogo(ruta, usuario);
				FileResource resource = new FileResource(archivo);
		    	settingsItem.setIcon(resource);
				subWindow.close();
				}
				else
					Notification.show("Debe seleccionar una imagen");
				
			}
		});
    
    }
}
