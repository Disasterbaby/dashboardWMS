package com.dashboardwms.components;

import java.util.Collection;
import java.util.Iterator;

import com.dashboardwms.events.DashboardEvent.NotificationsCountUpdatedEvent;
import com.dashboardwms.events.DashboardEvent.PostViewChangeEvent;
import com.dashboardwms.views.DashboardViewType;
import com.google.common.eventbus.Subscribe;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */
@SuppressWarnings({ "serial", "unchecked" })
public final class DashboardMenu extends CustomComponent {

    public static final String ID = "dashboard-menu";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    public ValoMenuItemButton botonEstadisticasPaises;
    public ValoMenuItemButton botonTiempoReal;
    public ValoMenuItemButton botonOyentesDia;
    private static final String STYLE_SELECTED = "selected";
    private MenuItem settingsItem;

    
    final CssLayout menuContent = new CssLayout();

    public DashboardMenu() {
   
    	
    	Responsive.makeResponsive(this);
        addStyleName("valo-menu");
        setId(ID);
         //  setHeight("100%");
        setSizeUndefined();
        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
    	
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());
        Responsive.makeResponsive(menuContent);
       

        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("Plug Streaming",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }

    
    public void setItemTexto(String emisora){
    	settingsItem.setText(emisora);
    }
    
    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
       

        settingsItem = settings.addItem("", null, null);
       
   
        settingsItem.addItem("Salir", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {

                VaadinSession.getCurrent().setAttribute("emisora", null);
               
                getUI().getPage().setLocation( "" );
                VaadinSession.getCurrent().close();
               
            }
        });
        return settings;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);
                }
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");
        menuItemsLayout.setHeight(100.0f, Unit.PERCENTAGE);
       menuItemsLayout.setWidthUndefined();

        botonEstadisticasPaises = new ValoMenuItemButton(DashboardViewType.ESTADISTICAS_PAISES);
        botonTiempoReal = new ValoMenuItemButton(DashboardViewType.TIEMPO_REAL);
        botonTiempoReal.addStyleName(STYLE_SELECTED);
        botonTiempoReal.addClickListener(buttonClickListener);
        botonEstadisticasPaises.addClickListener(buttonClickListener);
        botonOyentesDia = new ValoMenuItemButton(DashboardViewType.OYENTES_DIA);
        botonOyentesDia.addClickListener(buttonClickListener);
   //     for (final DashboardViewType view : DashboardViewType.values()) {
     //       Component menuItemComponent = new ValoMenuItemButton(view);
        
           
         //   menuItemsLayout.addComponent(menuItemComponent);
        menuItemsLayout.addComponent(botonTiempoReal);
        menuItemsLayout.addComponent(botonEstadisticasPaises);
        menuItemsLayout.addComponent(botonOyentesDia);
      //  }
        return menuItemsLayout;

    }

    ClickListener buttonClickListener = new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				clearMenuSelection();
				event.getButton().addStyleName(STYLE_SELECTED);
				

			}
		};
    
    
    
    public void clearMenuSelection(){
    	
    	 for (Iterator<Component> it = menuContent.iterator(); it.hasNext();) {
             Component next = it.next();
             if (next instanceof ValoMenuItemButton) {
                 next.removeStyleName(STYLE_SELECTED);
             } 
         }
    	botonEstadisticasPaises.removeStyleName(STYLE_SELECTED);
        botonTiempoReal.removeStyleName(STYLE_SELECTED);
        botonOyentesDia.removeStyleName(STYLE_SELECTED);
    }

    public final class ValoMenuItemButton extends Button {

     
        public ValoMenuItemButton(final DashboardViewType view) {
           
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(view.getViewName().substring(0, 1).toUpperCase()
                    + view.getViewName().substring(1));
      


        }

 
 
    }
}
