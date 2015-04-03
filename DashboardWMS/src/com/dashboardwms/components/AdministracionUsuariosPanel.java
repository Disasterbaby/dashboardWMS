package com.dashboardwms.components;

import java.util.ArrayList;
import java.util.List;

import com.dashboardwms.domain.Usuario;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.UsuarioService;
import com.dashboardwms.service.XLSReadingService;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class AdministracionUsuariosPanel extends VerticalLayout {
    private final Table table = new Table();
    private UsuarioService usuarioService;
    private AplicacionService aplicacionService;
    private XLSReadingService xlsReadingService;
	public Button btnAgregar = new Button();
	
	
 public AdministracionUsuariosPanel() {
	 btnAgregar.setImmediate(true);
	 btnAgregar.setCaption("Agregar Usuario");
	 btnAgregar.addClickListener(buttonClick);
    	Responsive.makeResponsive(this);
        setSizeFull();
        addStyleName("dashboard-view");
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.addComponent(buildToolbar());
        vLayout.setWidth("100%");

        Responsive.makeResponsive(vLayout);
        buildTable();
        Responsive.makeResponsive(table);
        table.setSizeFull();
        addComponent(vLayout);
        addComponent(table);
        setExpandRatio(table, 1);
        

    }
 
 
 

    private HorizontalLayout buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);
       
        Label title = new Label("Administración de Usuarios");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        
	       HorizontalLayout tools = new HorizontalLayout(btnAgregar);
			Responsive.makeResponsive(tools);
		tools.setSpacing(true);
		tools.addStyleName("toolbar");
		tools.setSizeUndefined();
		header.addComponent(tools);


        return header;
    }


    private void buildTable() {
    	table.addContainerProperty("Nombre", String.class, null);
    	table.addContainerProperty("Emisora", String.class, null);
    	table.addContainerProperty("Aplicación Móvil", String.class, null);
    	table.setFooterVisible(false);
        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.setSelectable(true);

          table.setColumnCollapsingAllowed(false);
          table.setColumnReorderingAllowed(true);

    }

 
    public ClickListener buttonClick = new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			buildModalWindow();
			
		}
	};


    
	public void fillTable(){
	
		table.removeAllItems();
		List<Usuario> listaUsuarios = usuarioService.getListaUsuarios();
		if(listaUsuarios!=null)
		{	
			for (Usuario usuario : listaUsuarios) {
				table.addItem(usuario);
			Item row = table.getItem(usuario);
		
			row.getItemProperty("Nombre").setValue(usuario.getNombre());
			row.getItemProperty("Emisora").setValue(usuario.getAplicacion());
			row.getItemProperty("Aplicación Móvil").setValue(usuario.getAplicacionMovil());
			
		}
		}

		}
		

	
		
    private void buildModalWindow(){
    	removeComponent(table);
    	final Label mensajeError = new Label("Debe llenar todos los campos");
    	mensajeError.addStyleName(ValoTheme.LABEL_FAILURE);
    	

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		
		 HorizontalLayout fields = new HorizontalLayout();

         fields.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
         fields.setSpacing(true);
         fields.setMargin(true);
         fields.setSizeUndefined();
         
         HorizontalLayout fields2 = new HorizontalLayout();
         fields2.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
         fields2.setSpacing(true);
         fields2.setMargin(true);

         fields.setMargin(true);
         fields.setSizeUndefined();
         
         final TextField username = new TextField("Nombre de Usuario");
         username.setRequired(true);
         username.setImmediate(true);

         final ComboBox cboxEmisoras = new ComboBox("Emisora");
         cboxEmisoras.setRequired(true);
      	final List<String> listaAplicaciones = new ArrayList<String>();

     	listaAplicaciones.addAll(aplicacionService.getListaAplicacionesDistinct());
     	BeanItemContainer<String> listaContainer = new BeanItemContainer<String>(
				String.class);

		listaContainer.addAll(listaAplicaciones);
		cboxEmisoras.setImmediate(true);
	        
	        cboxEmisoras.setNullSelectionAllowed(false);
	        cboxEmisoras.setInvalidAllowed(false);
		cboxEmisoras.setContainerDataSource(listaContainer);
		
		
        final ComboBox cboxApsMoviles = new ComboBox("Aplicación Móvil");
     	final List<String> listaAplicacionesMoviles = new ArrayList<String>();

     	listaAplicacionesMoviles.addAll(xlsReadingService.getListaAplicacionesMoviles());
    	BeanItemContainer<String> listaMovilContainer = new BeanItemContainer<String>(
				String.class);

    	listaMovilContainer.addAll(listaAplicacionesMoviles);
    	cboxApsMoviles.setImmediate(true);
    	cboxApsMoviles.setRequired(false);
    	cboxApsMoviles.setNullSelectionAllowed(true);
    	cboxApsMoviles.setInvalidAllowed(false);
    	cboxApsMoviles.setContainerDataSource(listaMovilContainer);
		
		
         
         final PasswordField password = new PasswordField("Contraseña");
         password.setImmediate(true);
         password.setRequired(true);
         final Button signin = new Button("Procesar");
         signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
         signin.setClickShortcut(KeyCode.ENTER);
         signin.setImmediate(true);
         signin.focus();

         fields.addComponents(username, cboxEmisoras);
         fields2.addComponents(cboxApsMoviles, password); 
//        
         mainLayout.addComponent(fields);
         mainLayout.addComponent(fields2);
         mainLayout.addComponent(signin);
//       fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
         mainLayout.addComponent(mensajeError);
         mensajeError.setVisible(false);
         addComponent(mainLayout);
         setExpandRatio(mainLayout, 1);
       signin.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
				try {
					username.validate();
					password.validate();
					cboxEmisoras.validate();
					cboxApsMoviles.validate();

			         mensajeError.setVisible(false);
				} catch (Exception e) {
			         mensajeError.setVisible(true);
				}
				}
	});
    }




	public void setUsuarioService(UsuarioService usuarioService){
		this.usuarioService = usuarioService;
	}
	
	
	public void setXLSReadingService(XLSReadingService xlsReadingService){
		this.xlsReadingService = xlsReadingService;
	}
	

	public void setAplicacionService(AplicacionService aplicacionService){
		this.aplicacionService = aplicacionService;
	}

}
