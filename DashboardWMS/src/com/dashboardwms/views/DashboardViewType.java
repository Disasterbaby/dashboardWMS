package com.dashboardwms.views;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType { HOME("Inicio",FontAwesome.HOME, true),
    TIEMPO_REAL("Tiempo Real", FontAwesome.CLOCK_O, true), ESTADISTICAS_PAISES(
            "Estad�sticas Pa�ses",  FontAwesome.GLOBE, false), OYENTES_DIA(
            "Oyentes por D�a", FontAwesome.BAR_CHART_O, false), ADMINISTRACION(
            "Administraci�n",  FontAwesome.DATABASE, true), MOVIL(
            "Estad�sticas M�viles",  FontAwesome.MOBILE_PHONE, false);

    private final String viewName;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }



    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
