package com.dashboardwms.components;

import java.util.ArrayList;
import java.util.List;

import com.dashboardwms.domain.Usuario;
import com.dashboardwms.exceptions.UsuarioDuplicadoException;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.UsuarioService;
import com.dashboardwms.service.XLSReadingService;
import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class AdministracionUsuariosPanel extends VerticalLayout {
    private final Table table = new Table();
    private UsuarioService usuarioService;
    private AplicacionService aplicacionService;
    private XLSReadingService xlsReadingService;
	public Button btnAgregar = new Button();

	   Label tituloForm = new Label("Crear Usuario");

    final ComboBox cboxApsMoviles = new ComboBox("Aplicación Móvil");
    final ComboBox cboxEmisoras = new ComboBox("Emisora");
    final TextField username = new TextField("Nombre de Usuario");

    final PasswordField password = new PasswordField("Contraseña");
    
	VerticalLayout mainLayout = new VerticalLayout();
	
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
        table.setNullSelectionAllowed(false);
          table.setColumnCollapsingAllowed(false);
          table.setColumnReorderingAllowed(true);
         
          table.addItemClickListener(new ItemClickListener() {
              @Override
              public void itemClick(ItemClickEvent event) {
                  if (!event.isDoubleClick())
                      return;
                  Usuario usuarioSeleccionado = (Usuario) table.getValue();
                  username.setValue(usuarioSeleccionado.getNombre());
                  cboxEmisoras.setValue(usuarioSeleccionado.getAplicacion());
                  cboxApsMoviles.setValue(usuarioSeleccionado.getAplicacionMovil());
                  password.setValue(usuarioSeleccionado.getPassword());
      			removeComponent(table);
      			   addComponent(mainLayout);
      		         setExpandRatio(mainLayout, 1);
      					btnAgregar.setCaption("Listado Usuarios");
      					tituloForm.setValue("Modificar Usuario");

   			         username.setReadOnly(true);
      				
              }
          });

    }

 
    public ClickListener buttonClick = new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			String caption = btnAgregar.getCaption();
			username.setReadOnly(false);
			  username.setValue("");
		         password.setValue("");
		         cboxApsMoviles.setValue(null);
		         cboxEmisoras.setValue(null);
			if(caption.equalsIgnoreCase("Agregar Usuario"))
			{	removeComponent(table);
			   addComponent(mainLayout);
		         setExpandRatio(mainLayout, 1);
					btnAgregar.setCaption("Listado Usuarios");

  					tituloForm.setValue("Crear Usuario");
			}
			else{
				removeComponent(mainLayout);
				   addComponent(table);
			         setExpandRatio(table, 1);
			         username.setReadOnly(false);
						btnAgregar.setCaption("Agregar Usuario");
			}
				
				
			
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
		

	
		
    private void buildCreateComponent(){
    	tituloForm.setSizeUndefined();
    	tituloForm.addStyleName(ValoTheme.LABEL_H4);
    	tituloForm.addStyleName(ValoTheme.LABEL_COLORED);
        Responsive.makeResponsive(mainLayout);
		mainLayout.setSizeFull();
		
		VerticalLayout layoutFields = new VerticalLayout();
		layoutFields.setSizeUndefined();
		layoutFields.setSpacing(true);
		layoutFields.setMargin(true);
		layoutFields.addStyleName(ValoTheme.LAYOUT_CARD);
        Responsive.makeResponsive(layoutFields);
		

        

         username.setRequired(true);
         username.setImmediate(true);

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
		
    	 HorizontalLayout hLayoutLabel = new HorizontalLayout(tituloForm);
    	 hLayoutLabel.setWidth("100%");

    	 hLayoutLabel.setComponentAlignment(tituloForm, Alignment.MIDDLE_CENTER);
         layoutFields.addComponent(hLayoutLabel);

         layoutFields.setComponentAlignment(hLayoutLabel, Alignment.MIDDLE_CENTER);
         
         password.setImmediate(true);
         password.setRequired(true);
         final Button signin = new Button("Procesar");
         signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
         signin.setClickShortcut(KeyCode.ENTER);
         signin.setImmediate(true);
         signin.focus();
         HorizontalLayout hLayoutUser = new HorizontalLayout(username);
         hLayoutUser.setWidth("100%");

         hLayoutUser.setComponentAlignment(username, Alignment.MIDDLE_CENTER);
         layoutFields.addComponent(hLayoutUser);
         HorizontalLayout hLayoutEmisoras = new HorizontalLayout(cboxEmisoras);
         hLayoutEmisoras.setWidth("100%");

         hLayoutEmisoras.setComponentAlignment(cboxEmisoras, Alignment.MIDDLE_CENTER);
         layoutFields.addComponent(hLayoutEmisoras);
         HorizontalLayout hLayoutMovil = new HorizontalLayout(cboxApsMoviles);
         hLayoutMovil.setWidth("100%");
         hLayoutMovil.setComponentAlignment(cboxApsMoviles, Alignment.MIDDLE_CENTER);
         layoutFields.addComponent(hLayoutMovil);
      
         HorizontalLayout hLayoutPassword = new HorizontalLayout(password);
         hLayoutPassword.setWidth("100%");
         hLayoutPassword.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
         layoutFields.addComponent(hLayoutPassword); 
         
//
         HorizontalLayout hLayoutbutton = new HorizontalLayout(signin);
         hLayoutbutton.setWidth("100%");

         hLayoutbutton.setComponentAlignment(signin, Alignment.MIDDLE_CENTER);
         layoutFields.addComponent(hLayoutbutton);
         layoutFields.setComponentAlignment(hLayoutbutton, Alignment.MIDDLE_CENTER);
         mainLayout.addComponent(layoutFields);
         mainLayout.setComponentAlignment(layoutFields,Alignment.MIDDLE_CENTER);
         mainLayout.setExpandRatio(layoutFields, 1);
      
       signin.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
				try {
					username.validate();
					password.validate();
					cboxEmisoras.validate();

			         String emisora = (String) cboxEmisoras.getValue();
			         String appMovil = null;
			         if(cboxApsMoviles.getValue()!=null)
			          appMovil = (String) cboxApsMoviles.getValue();
			         if(tituloForm.getValue().equalsIgnoreCase("CREAR USUARIO"))
			         {usuarioService.crearUsuario(username.getValue(), password.getValue(), emisora, appMovil);
			         Notification.show("Usuario creado exitosamente");}
			         else{
			        	 Usuario usuario = new Usuario();
			        	 usuario.setNombre(username.getValue());
			        	 usuario.setPassword(password.getValue());
			        	 usuario.setAplicacion(emisora);
			        	 usuario.setAplicacionMovil(appMovil);
			        	 usuarioService.modificarUsuario(usuario);
			        	 Notification.show("Usuario modificado exitosamente");
			         }
			         username.setValue("");
			         password.setValue("");
			         cboxApsMoviles.setValue(null);
			         cboxEmisoras.setValue(null);
			         fillTable();
				} catch (UsuarioDuplicadoException e) {
					System.out.println(e.getMessage());

					Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
				
				}
				catch(InvalidValueException ex){
					System.out.println(ex.getMessage());
					Notification.show("Debe llenar todos los campos requeridos", Notification.Type.WARNING_MESSAGE);
					
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

        buildCreateComponent();
	}

}
