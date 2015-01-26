package com.dashboardwms.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;


public class FooterPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FooterPanel(){
		addStyleName(Runo.PANEL_LIGHT);
		addStyleName(Runo.LAYOUT_DARKER);
		addStyleName("footer");
		setSizeFull();
		VerticalLayout vLayout = new VerticalLayout();
		
		setContent(vLayout);
		
		Label mensaje = new Label("© Copyright 2014 DELSUR Banco Universal, C.A. RIF: J-00079723-4");
	
				mensaje.setSizeUndefined();


		vLayout.addComponent(mensaje);
		vLayout.setComponentAlignment(mensaje, Alignment.MIDDLE_CENTER);
		vLayout.setSizeFull();
	}
}
