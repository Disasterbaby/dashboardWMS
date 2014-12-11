package com.dashboardwms.domain.connectioncounts;

public class Stream
{
    private String name;

    private String sessionsMPEGDash;

    private String sessionsFlash;

    private String sessionsCupertino;

    private String sessionsTotal;

    private String sessionsSmooth;

    private String sessionsRTSP;

    private String sessionsSanJose;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getSessionsMPEGDash ()
    {
        return sessionsMPEGDash;
    }

    public void setSessionsMPEGDash (String sessionsMPEGDash)
    {
        this.sessionsMPEGDash = sessionsMPEGDash;
    }

    public String getSessionsFlash ()
    {
        return sessionsFlash;
    }

    public void setSessionsFlash (String sessionsFlash)
    {
        this.sessionsFlash = sessionsFlash;
    }

    public String getSessionsCupertino ()
    {
        return sessionsCupertino;
    }

    public void setSessionsCupertino (String sessionsCupertino)
    {
        this.sessionsCupertino = sessionsCupertino;
    }

    public String getSessionsTotal ()
    {
        return sessionsTotal;
    }

    public void setSessionsTotal (String sessionsTotal)
    {
        this.sessionsTotal = sessionsTotal;
    }

    public String getSessionsSmooth ()
    {
        return sessionsSmooth;
    }

    public void setSessionsSmooth (String sessionsSmooth)
    {
        this.sessionsSmooth = sessionsSmooth;
    }

    public String getSessionsRTSP ()
    {
        return sessionsRTSP;
    }

    public void setSessionsRTSP (String sessionsRTSP)
    {
        this.sessionsRTSP = sessionsRTSP;
    }

    public String getSessionsSanJose ()
    {
        return sessionsSanJose;
    }

    public void setSessionsSanJose (String sessionsSanJose)
    {
        this.sessionsSanJose = sessionsSanJose;
    }
}