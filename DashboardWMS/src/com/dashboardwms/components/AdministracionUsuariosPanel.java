package com.dashboardwms.components;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.dashboardwms.domain.Usuario;
import com.dashboardwms.exceptions.RutaInvalidaException;
import com.dashboardwms.exceptions.UsuarioDuplicadoException;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.CargaImagenService;
import com.dashboardwms.service.ImageUploader;
import com.dashboardwms.service.UsuarioService;
import com.dashboardwms.service.XLSReadingService;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class AdministracionUsuariosPanel extends VerticalLayout {
    private final Table table = new Table();
    private UsuarioService usuarioService;
    private AplicacionService aplicacionService;
    private XLSReadingService xlsReadingService;
	public Button btnAgregar = new Button();
public List<String> listaEmisorasUsuarioSeleccionado = new ArrayList<String>();
	   Label tituloForm = new Label("Crear Usuario");
	   ImageUploader receiver = new ImageUploader(); 
	   
	// Create the upload with a caption and set receiver later
	Upload upload = new Upload();

    Button btnEmisoras = new Button("Emisoras");

    final ComboBox cboxApsMoviles = new ComboBox("Aplicación Móvil");
    final ComboBox cboxEmisoras = new ComboBox("Seleccionar");
    final TextField username = new TextField("Nombre de Usuario");
	public PopupDateField dfFecha = new PopupDateField("Fecha Vencimiento");
    final PasswordField password = new PasswordField("Contraseña");
	VerticalLayout mainLayout = new VerticalLayout();
	
	private CargaImagenService cargaService;
	
	


	public void setCargaService(CargaImagenService cargaService) {
		this.cargaService = cargaService;
	}




public AdministracionUsuariosPanel() {
	btnEmisoras.setImmediate(true);
	btnEmisoras.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			buildModalWindow();
			
		}
	});
	upload.setReceiver(receiver);
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
    	table.addContainerProperty("Logo", String.class, null);
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
                  cboxApsMoviles.setValue(usuarioSeleccionado.getAplicacionMovil());
                  password.setValue(usuarioSeleccionado.getPassword());
                  Date fechaVencimiento = usuarioSeleccionado.getFechaVencimiento();
                  if(fechaVencimiento!=null)
                  if(fechaVencimiento.after(Calendar.getInstance(TimeZone.getTimeZone("America/Caracas")).getTime()))
                  dfFecha.setValue(fechaVencimiento);
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
			
			
			if(caption.equalsIgnoreCase("Agregar Usuario"))
			{	

		         username.setReadOnly(false);
				  username.setValue("");
			         password.setValue("");
			         cboxApsMoviles.setValue(null);
			         cboxEmisoras.setValue(null);
			         dfFecha.setValue(Calendar.getInstance(TimeZone.getTimeZone("America/Caracas")).getTime());
				removeComponent(table);
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
				
			  	List<String> listaEmisoras = usuarioService.getListaEmisorasPorUsuario(usuario.getNombre());
			  	String emisora ="";
			  	if(!listaEmisoras.isEmpty()){

			  		emisora = listaEmisoras.get(0);
			  		usuario.setAplicacion(emisora);
			  	}
				String appMovil = usuarioService.getAppMovil(emisora);
				usuario.setAplicacionMovil(appMovil);
				table.addItem(usuario);
			Item row = table.getItem(usuario);
		
			row.getItemProperty("Nombre").setValue(usuario.getNombre());

		
			row.getItemProperty("Emisora").setValue(emisora);
			
			
			row.getItemProperty("Aplicación Móvil").setValue(usuario.getAplicacionMovil());
	    	
			
			if(usuario.getLogo()!=null){

				row.getItemProperty("Logo").setValue("Sí");

			}
			else

				row.getItemProperty("Logo").setValue("No");

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
		layoutFields.setMargin(new MarginInfo(true, true, true, true));
		layoutFields.addStyleName(ValoTheme.LAYOUT_CARD);
        Responsive.makeResponsive(layoutFields);
		

        

         username.setRequired(true);
         username.setImmediate(true);

		
		
     	 List<String> listaAplicacionesMoviles = new ArrayList<String>();
try{
	listaAplicacionesMoviles = xlsReadingService.getListaAplicacionesMoviles();
}catch (RutaInvalidaException e){
	Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
}
    	BeanItemContainer<String> listaMovilContainer = new BeanItemContainer<String>(
				String.class);

    	listaMovilContainer.addAll(listaAplicacionesMoviles);
    	cboxApsMoviles.setContainerDataSource(listaMovilContainer);
    	cboxApsMoviles.setImmediate(true);
    	cboxApsMoviles.setRequired(false);
    	cboxApsMoviles.setNullSelectionAllowed(true);
    	cboxApsMoviles.setInvalidAllowed(false);


  
		
    	 HorizontalLayout hLayoutLabel = new HorizontalLayout(tituloForm);
    	 hLayoutLabel.setWidth("100%");
    	 hLayoutLabel.setMargin(false);
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
      //   layoutFields.addComponent(hLayoutUser);
         
      

         HorizontalLayout hLayoutPassword = new HorizontalLayout(password);
         hLayoutPassword.setWidth("100%");
         hLayoutPassword.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
         HorizontalLayout hLayoutFirst = new HorizontalLayout(hLayoutUser, hLayoutPassword);
         hLayoutFirst.setWidth("100%");

         hLayoutFirst.setSpacing(true);
     //    layoutFields.addComponent(hLayoutEmisoras);
         layoutFields.addComponent(hLayoutFirst);
         HorizontalLayout hLayoutMovil = new HorizontalLayout(cboxApsMoviles);
         hLayoutMovil.setWidth("100%");
         hLayoutMovil.setComponentAlignment(cboxApsMoviles, Alignment.MIDDLE_CENTER);
         
      //  layoutFields.addComponent(hLayoutMovil);

         dfFecha.setRangeStart(Calendar.getInstance(TimeZone.getTimeZone("America/Caracas")).getTime());
         dfFecha.setRequired(true);
         dfFecha.setInvalidAllowed(false);
         dfFecha.setAssistiveText("Seleccionar fecha");
         dfFecha.setTextFieldEnabled(false);
         HorizontalLayout hLayoutVencimiento = new HorizontalLayout(dfFecha);
         hLayoutVencimiento.setWidth("100%");
         hLayoutVencimiento.setComponentAlignment(dfFecha, Alignment.MIDDLE_CENTER);
         
       //  layoutFields.addComponent(hLayoutVencimiento);

         HorizontalLayout hLayoutSecond = new HorizontalLayout(hLayoutMovil, hLayoutVencimiento);
         hLayoutSecond.setWidth("100%");
         hLayoutSecond.setSpacing(true);
           layoutFields.addComponent(hLayoutSecond);
//         layoutFields.addComponent(hLayoutPassword); 
         
//
           btnEmisoras.setWidth("185px");
         upload.setButtonCaption("Logo");
         
         upload.setWidth("185px");
         upload.setImmediate(true);
         upload.setStyleName(ValoTheme.BUTTON_TINY);
     	upload.addSucceededListener(receiver);
        
 		      
// 		         layoutFields.addComponent(hLayoutUpload); 	
 		      HorizontalLayout hLayoutThird= new HorizontalLayout(btnEmisoras, upload);
 		     hLayoutThird.setSpacing(true);
 		     hLayoutThird.setWidth("100%");
 		    hLayoutThird.setComponentAlignment(btnEmisoras, Alignment.MIDDLE_LEFT);
 		  
 		    hLayoutThird.setComponentAlignment(upload, Alignment.MIDDLE_RIGHT);
 	        layoutFields.addComponent(hLayoutThird); 
 		         HorizontalLayout hLayoutbutton = new HorizontalLayout(signin);
 		         hLayoutbutton.setWidth("100%");
 		        hLayoutbutton.setMargin(true);
 		       hLayoutbutton.setSpacing(true);
 		         hLayoutbutton.setComponentAlignment(signin, Alignment.MIDDLE_CENTER);
 		         layoutFields.addComponent(hLayoutbutton);
 		         layoutFields.setComponentAlignment(hLayoutbutton, Alignment.MIDDLE_CENTER);
 		        
 				
 				 mainLayout.addComponent(layoutFields);
 		         mainLayout.setComponentAlignment(layoutFields,Alignment.MIDDLE_CENTER);
 		         mainLayout.setExpandRatio(layoutFields, 1);
       signin.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			if(listaEmisorasUsuarioSeleccionado.isEmpty())
				listaEmisorasUsuarioSeleccionado.addAll(usuarioService.getListaEmisorasPorUsuario(username.getValue()));

			if(!listaEmisorasUsuarioSeleccionado.isEmpty())
				try {
					username.validate();
					password.validate();
					dfFecha.validate();
					File archivo = receiver.file;
					String ruta = null;
					if(archivo!=null)
						ruta = receiver.file.getAbsolutePath();
			         String emisora = (String) cboxEmisoras.getValue();
			         String appMovil = null;
			         if(cboxApsMoviles.getValue()!=null)
			          appMovil = (String) cboxApsMoviles.getValue();
			         if(tituloForm.getValue().equalsIgnoreCase("CREAR USUARIO"))
			         {usuarioService.crearUsuario(username.getValue(), password.getValue(), appMovil, dfFecha.getValue(), ruta, listaEmisorasUsuarioSeleccionado);
			         Notification.show("Usuario creado exitosamente");}
			         else{
			        	 Usuario usuario = new Usuario();
			        	 usuario.setNombre(username.getValue());
			        	 usuario.setPassword(password.getValue());
			        	 usuario.setAplicacion(emisora);
			        	 usuario.setAplicacionMovil(appMovil);
			        	 usuario.setFechaVencimiento(dfFecha.getValue());
			        	 usuario.setLogo(ruta);
			        	 usuario.setListaEmisoras(listaEmisorasUsuarioSeleccionado);			        	 
			        	 usuarioService.modificarUsuario(usuario);
			        	 Notification.show("Usuario modificado exitosamente");
			         }
			   
			     
			         fillTable();
				} catch (UsuarioDuplicadoException e) {
					System.out.println(e.getMessage());

					Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
				
				}
				catch(InvalidValueException ex){
					ex.printStackTrace();
					System.out.println(ex.getMessage());
					Notification.show("Debe llenar todos los campos requeridos", Notification.Type.WARNING_MESSAGE);
					
				}
			
			else {
				Notification.show("Debe asignar al menos una emisora al usuario", Notification.Type.WARNING_MESSAGE);
				
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
	



    private void buildModalWindow(){
    	
    	  cboxEmisoras.setRequired(true);
       	final List<String> listaAplicaciones = new ArrayList<String>();

      	listaAplicaciones.addAll(aplicacionService.getListaAplicacionesDistinct());
      	BeanItemContainer<String> listaContainer = new BeanItemContainer<String>(
  				String.class);

  		listaContainer.addAll(listaAplicaciones);


		final Table tablaEmisoras = new Table();
		tablaEmisoras.setImmediate(true);

	    final  	BeanItemContainer<String> listaEmisorasContainer = new BeanItemContainer<String>(String.class);
	      listaEmisorasContainer.addAll(usuarioService.getListaEmisorasPorUsuario(username.getValue()));

		 tablaEmisoras.setContainerDataSource(listaEmisorasContainer);
  		cboxEmisoras.setImmediate(true);
  	        
  	        cboxEmisoras.setNullSelectionAllowed(false);
  	        cboxEmisoras.setInvalidAllowed(false);
  		cboxEmisoras.setContainerDataSource(listaContainer);
  		cboxEmisoras.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				String valor = (String)event.getProperty().getValue();
				listaEmisorasContainer.addBean(valor);
			}
		});
    	final Window subWindow = new Window();
    	subWindow.setModal(true);
		subWindow.addStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
	   		subWindow.setCaption("Asignar Emisoras");
		subWindow.setResizable(false);
		subWindow.setDraggable(false);
		subWindow.setWidth("50%");
		subWindow.setHeight("75%");
		subWindow.center();
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		mainLayout.setSizeUndefined();
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		 VerticalLayout vLayout = new VerticalLayout();
	
		 
		 tablaEmisoras.addGeneratedColumn("Emisoras", new Table.ColumnGenerator() {
				public Object generateCell(Table source, final Object itemId,
						Object columnId) {
					return itemId;
				}
			});
		 
		 tablaEmisoras.addGeneratedColumn("", new Table.ColumnGenerator() {
				public Object generateCell(Table source, final Object itemId,
						Object columnId) {
					Button removeButton = new Button("x");
					removeButton.addClickListener(new ClickListener() {
						public void buttonClick(ClickEvent event) {

							tablaEmisoras.removeItem(itemId);
							
						}
					});
					return removeButton;
				}
			});

	      tablaEmisoras.setVisibleColumns("Emisoras","");
	      tablaEmisoras.setColumnExpandRatio("Emisoras", 1f);
		 tablaEmisoras.setSizeFull();
		 tablaEmisoras.setPageLength(5);
		 vLayout.addComponent(cboxEmisoras);
		 vLayout.addComponent(tablaEmisoras);

		 vLayout.setComponentAlignment(cboxEmisoras, Alignment.MIDDLE_LEFT);
		 vLayout.setComponentAlignment(tablaEmisoras, Alignment.MIDDLE_CENTER);
		vLayout.setSizeFull();
		vLayout.setSpacing(true);
		vLayout.setMargin(true);
		vLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		 HorizontalLayout fields = new HorizontalLayout();
         fields.setSpacing(true);
         fields.setMargin(true);
         fields.addStyleName("fields");
         CssLayout labels = new CssLayout();
         labels.addStyleName("labels");

        
         final Button signin = new Button("Procesar");
         signin.addStyleName(ValoTheme.BUTTON_SMALL);
         signin.setClickShortcut(KeyCode.ENTER);
         signin.setImmediate(true);
         signin.focus();

         fields.addComponents( signin);
         fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
         signin.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				listaEmisorasUsuarioSeleccionado.clear();
				listaEmisorasUsuarioSeleccionado.addAll((List<String>) tablaEmisoras.getItemIds());
				subWindow.close();
			}
		});
         vLayout.addComponent(fields);
         subWindow.setContent(vLayout);
		getUI().addWindow(subWindow); 
 
    }

}
