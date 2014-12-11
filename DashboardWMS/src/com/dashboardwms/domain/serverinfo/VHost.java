package com.dashboardwms.domain.serverinfo;

public class VHost
{
    private String name;

    private String connectionsLimit;

    private String timeRunning;

    private String connectionsTotalRejected;

    private Application[] application;

    private String connectionsCurrent;

    private String connectionsTotal;

    private String connectionsTotalAccepted;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConnectionsLimit() {
		return connectionsLimit;
	}

	public void setConnectionsLimit(String connectionsLimit) {
		this.connectionsLimit = connectionsLimit;
	}

	public String getTimeRunning() {
		return timeRunning;
	}

	public void setTimeRunning(String timeRunning) {
		this.timeRunning = timeRunning;
	}

	public String getConnectionsTotalRejected() {
		return connectionsTotalRejected;
	}

	public void setConnectionsTotalRejected(String connectionsTotalRejected) {
		this.connectionsTotalRejected = connectionsTotalRejected;
	}

	public Application[] getApplication() {
		return application;
	}

	public void setApplication(Application[] application) {
		this.application = application;
	}

	public String getConnectionsCurrent() {
		return connectionsCurrent;
	}

	public void setConnectionsCurrent(String connectionsCurrent) {
		this.connectionsCurrent = connectionsCurrent;
	}

	public String getConnectionsTotal() {
		return connectionsTotal;
	}

	public void setConnectionsTotal(String connectionsTotal) {
		this.connectionsTotal = connectionsTotal;
	}

	public String getConnectionsTotalAccepted() {
		return connectionsTotalAccepted;
	}

	public void setConnectionsTotalAccepted(String connectionsTotalAccepted) {
		this.connectionsTotalAccepted = connectionsTotalAccepted;
	}




}

   