package com.dashboardwms.domain.serverinfo;


public class Application
{
private String name;

private String status;

private String timeRunning;

private String connectionsTotalRejected;

private ApplicationInstance applicationInstance;

private String connectionsCurrent;

private String connectionsTotal;

private String connectionsTotalAccepted;

public String getName ()
{
return name;
}

public void setName (String Name)
{
this.name = Name;
}

public String getStatus ()
{
return status;
}

public void setStatus (String status)
{
this.status = status;
}

public String getTimeRunning ()
{
return timeRunning;
}

public void setTimeRunning (String timeRunning)
{
this.timeRunning = timeRunning;
}

public String getConnectionsTotalRejected ()
{
return connectionsTotalRejected;
}

public void setConnectionsTotalRejected (String connectionsTotalRejected)
{
this.connectionsTotalRejected = connectionsTotalRejected;
}

public ApplicationInstance getApplicationInstance ()
{
return applicationInstance;
}

public void setApplicationInstance (ApplicationInstance applicationInstance)
{
this.applicationInstance = applicationInstance;
}

public String getConnectionsCurrent ()
{
return connectionsCurrent;
}

public void setConnectionsCurrent (String connectionsCurrent)
{
this.connectionsCurrent = connectionsCurrent;
}

public String getConnectionsTotal ()
{
return connectionsTotal;
}

public void setConnectionsTotal (String connectionsTotal)
{
this.connectionsTotal = connectionsTotal;
}

public String getConnectionsTotalAccepted ()
{
return connectionsTotalAccepted;
}

public void setConnectionsTotalAccepted (String connectionsTotalAccepted)
{
this.connectionsTotalAccepted = connectionsTotalAccepted;
}
}