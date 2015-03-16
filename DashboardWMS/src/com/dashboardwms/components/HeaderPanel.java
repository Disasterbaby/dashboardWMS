package com.dashboardwms.components;

import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;

public class HeaderPanel extends Panel {




	public HeaderPanel(){
		setSizeFull();
		addStyleName("header");
		addStyleName(ValoTheme.PANEL_BORDERLESS);

	}



}