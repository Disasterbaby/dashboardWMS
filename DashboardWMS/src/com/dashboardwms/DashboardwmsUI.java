package com.dashboardwms;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.dashboardwms.service.XMLReader;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("dashboardwms")
public class DashboardwmsUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		final XMLReader xmlReader = new XMLReader();
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
					xmlReader.getXML("http://s1.serverht.net:8086/connectioncounts");
				} catch (ParserConfigurationException
						| SAXException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				layout.addComponent(new Label("Thank you for clicking"));
			}
		});
		layout.addComponent(button);
	}

}