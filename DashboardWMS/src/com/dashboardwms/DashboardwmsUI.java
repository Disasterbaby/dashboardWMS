package com.dashboardwms;



import java.awt.Color;
import java.awt.GradientPaint;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.springframework.context.annotation.Scope;
import org.vaadin.addon.JFreeChartWrapper;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.DiscoveryNavigator;









import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.views.*;

@SuppressWarnings("serial")
@Theme("dashboard")
@Title("PlugStreaming")
@Component("DashboardwmsUI")
@Scope("prototype")
public class DashboardwmsUI extends UI {

        private Navigator navigator;

        public static final String LOGINVIEW = "";
        public static final String MAINVIEW = "form";
        public static final String ERRORVIEW = "error";
        public static final String POSTLOGINVIEW = "listado";
       
       
        @Override
        protected void init(VaadinRequest request) {
               
                UI.getCurrent().setLocale(new Locale("es"));
        
                
                
                Responsive.makeResponsive(this);
        navigator = new DiscoveryNavigator(this, this);
               
                System.out.println("despues de discovery navigator");
               
              //  navigator.navigateTo(MAINVIEW);
                
                navigator.addViewChangeListener(new ViewChangeListener() {
        	        
        	        @Override
        	        public boolean beforeViewChange(ViewChangeEvent event) {
        	       
        	            System.out.println("view change listener");
        	            boolean isLoggedIn = false;
        	            String emisora = (String) VaadinSession.getCurrent().getAttribute("emisora");
        	            if(emisora!=null)
            	            isLoggedIn = true;
        	            boolean isLoginView = event.getNewView() instanceof LoginView;


        	            if (!isLoggedIn && !isLoginView) {
        	         
        	                getNavigator().navigateTo(LOGINVIEW);
        	                return false;

        	            } else if (isLoggedIn && isLoginView) {
        	            	
        	            	 getNavigator().navigateTo(MAINVIEW);
        	                return false;
        	            } else if(!isLoggedIn && isLoginView)
        	            	return true;
        	            else if (!isLoggedIn)
        	            {
        	            
        	            	 getNavigator().navigateTo(LOGINVIEW);
        		                return false;
        	            }
        	            	
        	            

        	            return true;
        	        }
        	        
        	        @Override
        	        public void afterViewChange(ViewChangeEvent event) {
        	            System.out.println("after view change");
        	        }
        	    });
                
        }
}

