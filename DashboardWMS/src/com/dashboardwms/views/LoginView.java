package com.dashboardwms.views;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.dashboardwms.DashboardwmsUI;
import com.dashboardwms.service.UsuarioService;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Component("LoginView")
@Scope("prototype")
@VaadinView(DashboardwmsUI.LOGINVIEW)
public class LoginView extends VerticalLayout implements View  {

	@Autowired
	UsuarioService usuarioService;

	 final HorizontalLayout fields = new HorizontalLayout();
//	final List<String> listaAplicaciones = new ArrayList<String>();

    final ComboBox listaEmisoras = new ComboBox();
    

    @PostConstruct
	public void PostConstruct() {
    	

		Responsive.makeResponsive(this);
//    	
// 	listaAplicaciones.addAll(aplicacionService.getListaAplicacionesDistinct());
    	

 	
 	
    	setSizeFull();
    	
    	  addStyleName("mainview");

        VerticalLayout loginForm = buildLoginForm();
       HorizontalLayout headerPanel = new HorizontalLayout();
        headerPanel.setHeight("80px");
        headerPanel.setWidth("100%");
        headerPanel.addStyleName("header");
        addComponent(headerPanel);
        addComponent(loginForm);
        setComponentAlignment(headerPanel, Alignment.TOP_LEFT);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
        setExpandRatio(loginForm, 1f);
        
    }

    private VerticalLayout buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent((com.vaadin.ui.Component) buildLabels());
        loginPanel.addComponent((com.vaadin.ui.Component) buildFields());
        return loginPanel;
    }

    private HorizontalLayout buildFields() {
    	
         fields.setSpacing(true);
         fields.addStyleName("fields");

         final TextField username = new TextField("Usuario");
         username.setIcon(FontAwesome.USER);
         username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

         final PasswordField password = new PasswordField("Contraseña");
         password.setIcon(FontAwesome.LOCK);
         password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

         final Button signin = new Button("Ingresar");
         signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
         signin.setClickShortcut(KeyCode.ENTER);
         signin.setImmediate(true);
         signin.focus();

         fields.addComponents(username, password, signin);
         fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

         signin.addClickListener(new ClickListener() {
             @Override
             public void buttonClick(final ClickEvent event) {
            	 
            	 
                 String usuario = username.getValue();
                 String passwordValue = password.getValue();
                String emisora = usuarioService.verificarCredenciales(usuario, passwordValue);
                String appMovil = usuarioService.getAppMovil(emisora);

             if(emisora == null)
             {	 Notification.show("Intente de nuevo", "Nombre de Usuario o Contraseña incorrectos", Notification.Type.ERROR_MESSAGE);
             	password.setValue("");
             }else if(emisora.equalsIgnoreCase("todas")){
            	 VaadinSession.getCurrent().setAttribute("emisora", emisora);
            	 VaadinSession.getCurrent().setAttribute("usuario", usuario);

            	   getUI().getNavigator()
   				.navigateTo(DashboardwmsUI.POSTLOGINVIEW);
            	 
             }else{
            	 Notification.show("Bienvenido", Notification.Type.HUMANIZED_MESSAGE);
            	 VaadinSession.getCurrent().setAttribute("emisora", emisora);
            	 VaadinSession.getCurrent().setAttribute("usuario", usuario);
            	 VaadinSession.getCurrent().setAttribute("appMovil", appMovil);
               getUI().getNavigator()
				.navigateTo(DashboardwmsUI.MAINVIEW);
             }
            	
             }
         });
         return fields;
        
    }

    private CssLayout buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H3);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);
        
        Label title = new Label("Bienvenido");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H4);
        title.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(title);
        return labels;
    }

	@Override
	public void enter(ViewChangeEvent event) {
     
	}

}