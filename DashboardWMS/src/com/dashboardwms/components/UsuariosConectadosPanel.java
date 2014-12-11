package com.dashboardwms.components;

import java.util.List;

import com.dashboardwms.domain.Aplicacion;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class UsuariosConectadosPanel extends Panel {
	
	VerticalLayout vLayout = new VerticalLayout();
	Table tablaUsuariosConectados = new Table();
	
	public UsuariosConectadosPanel(){
		setSizeFull();
		
		vLayout.setSizeFull();
		setContent(vLayout);
	}
	
	public void generarTabla(List<Aplicacion> listaAplicaciones){
		
		
		
	}
	

}
