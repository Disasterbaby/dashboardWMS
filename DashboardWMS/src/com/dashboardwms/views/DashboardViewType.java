package com.dashboardwms.views;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType {
    TIEMPO_REAL("Tiempo Real", FontAwesome.HOME, true), SALES(
            "sales",  FontAwesome.BAR_CHART_O, false), TRANSACTIONS(
            "transactions", FontAwesome.TABLE, false), REPORTS(
            "reports",  FontAwesome.FILE_TEXT_O, true), SCHEDULE(
            "schedule",  FontAwesome.MAIL_FORWARD, false);

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
