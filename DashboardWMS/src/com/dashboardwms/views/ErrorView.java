package com.dashboardwms.views;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;
import com.dashboardwms.DashboardwmsUI;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ChameleonTheme;
import com.vaadin.ui.themes.Runo;

@SuppressWarnings("serial")
@Component("ErrorView")
@Scope("prototype")
@VaadinView(DashboardwmsUI.ERRORVIEW)
public class ErrorView  extends GridLayout implements View{
	
	public static final String NAME = "login";


	private final Label mensajeError = new Label();

	

	private final Label lbTitulo = new Label();

	private final Button loginButton = new Button("Volver");


	private final Label expandingGap1 = new Label("&nbsp;", ContentMode.HTML);

	private final Label expandingGap2 = new Label("&nbsp;", ContentMode.HTML);

	private final Label emptyLabel1 = new Label("&nbsp;", ContentMode.HTML);

	private final Label emptyLabel2 = new Label("&nbsp;", ContentMode.HTML);



	@PostConstruct
	public void PostConstruct() {

		setSizeFull();
		addStyleName("mylayout");
		loginButton.setClickShortcut(KeyCode.ENTER);
	expandingGap1.setHeight("50px");
		expandingGap2.setHeight("50px");

	
		mensajeError.setSizeUndefined();
		mensajeError.setStyleName(ChameleonTheme.LABEL_ERROR);
		mensajeError.setValue("Disculpe. La página a la que intenta ingresar no existe. Por favor pulse el botón para volver ");
		

		lbTitulo.setValue("PlugStreaming");
		lbTitulo.setStyleName(ChameleonTheme.LABEL_H1);

		VerticalLayout fields = new VerticalLayout(expandingGap1, lbTitulo,
				expandingGap2,  emptyLabel1,
				loginButton, emptyLabel2, mensajeError);
		fields.addStyleName(Runo.CSSLAYOUT_SHADOW);
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, true));
		fields.setSizeUndefined();
		fields.setComponentAlignment(mensajeError, Alignment.BOTTOM_CENTER);
		fields.setComponentAlignment(loginButton, Alignment.BOTTOM_CENTER);
		fields.setExpandRatio(expandingGap1, 1.0f);
		fields.setExpandRatio(expandingGap2, 1.0f);
	


		addComponent(fields);

		setComponentAlignment(fields, Alignment.TOP_CENTER);
		setRowExpandRatio(1, 1);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("hola ERROR");
		
	}
	
}
