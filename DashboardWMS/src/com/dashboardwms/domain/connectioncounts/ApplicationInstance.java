package com.dashboardwms.domain.connectioncounts;

public class ApplicationInstance
{
    private String name;

    private String timeRunning;

    private String connectionsTotalRejected;

    private String messagesInBytesRate;

    private Stream stream;

    private String messagesOutBytesRate;

    private String connectionsCurrent;

    private String connectionsTotal;

    private String connectionsTotalAccepted;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
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

    public String getMessagesInBytesRate ()
    {
        return messagesInBytesRate;
    }

    public void setMessagesInBytesRate (String messagesInBytesRate)
    {
        this.messagesInBytesRate = messagesInBytesRate;
    }

    public Stream getStream ()
    {
        return stream;
    }

    public void setStream (Stream stream)
    {
        this.stream = stream;
    }

    public String getMessagesOutBytesRate ()
    {
        return messagesOutBytesRate;
    }

    public void setMessagesOutBytesRate (String messagesOutBytesRate)
    {
        this.messagesOutBytesRate = messagesOutBytesRate;
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