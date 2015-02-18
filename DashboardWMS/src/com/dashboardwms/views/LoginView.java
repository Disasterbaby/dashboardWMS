package com.dashboardwms.views;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.dashboardwms.DashboardwmsUI;
import com.dashboardwms.service.AplicacionService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Component("LoginView")
@Scope("prototype")
@VaadinView(DashboardwmsUI.LOGINVIEW)
public class LoginView extends VerticalLayout implements View  {

	@Autowired
	AplicacionService aplicacionService;

	final List<String> listaAplicaciones = new ArrayList<String>();

    final ComboBox listaEmisoras = new ComboBox();

    @PostConstruct
	public void PostConstruct() {
    	

		Responsive.makeResponsive(this);
    	
 	listaAplicaciones.addAll(aplicacionService.getListaAplicacionesDistinct());
    	

 	
 	
    	setSizeFull();
    	
    	  addStyleName("mainview");

        VerticalLayout loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);


        
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
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        listaEmisoras.setImmediate(true);
        listaEmisoras.setIcon(FontAwesome.USER);
        listaEmisoras.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        
        listaEmisoras.setNullSelectionAllowed(false);
        listaEmisoras.setInvalidAllowed(false);
		BeanItemContainer<String> listaContainer = new BeanItemContainer<String>(
				String.class);

		listaContainer.addAll(listaAplicaciones);
		listaEmisoras.setContainerDataSource(listaContainer);
     
        final Button signin = new Button("Ingresar");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
      //  signin.setClickShortcut(KeyCode.ENTER);
        

        fields.addComponents(listaEmisoras, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {

            	if(listaEmisoras.getValue()!= null)
            	{	String emisora = (String)listaEmisoras.getValue();
                VaadinSession.getCurrent().setAttribute("emisora", emisora);
                getUI().getNavigator()
				.navigateTo(DashboardwmsUI.MAINVIEW);
            	}
            	else listaEmisoras.focus();
            }
        });
        return fields;
    }

    private CssLayout buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("PlugStreaming");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);
        
        Label title = new Label("Bienvenido");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }

	@Override
	public void enter(ViewChangeEvent event) {
     
	}

}