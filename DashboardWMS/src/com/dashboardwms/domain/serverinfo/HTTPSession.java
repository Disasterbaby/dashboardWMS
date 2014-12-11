package com.dashboardwms.domain.serverinfo;

public class HTTPSession
{
    private String sessionId;

    private String IoBytesReceived;

    private String sessionType;

    private String IpAddress;

    private String IoBytesSent;

    private String referrer;

    private String queryString;

    private String port;

    private String timeRunning;

    private String dateStarted;

    private String protocol;

    private String IoLastRequest;

    private String URI;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIoBytesReceived() {
		return IoBytesReceived;
	}

	public void setIoBytesReceived(String ioBytesReceived) {
		IoBytesReceived = ioBytesReceived;
	}

	public String getSessionType() {
		return sessionType;
	}

	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}

	public String getIpAddress() {
		return IpAddress;
	}

	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}

	public String getIoBytesSent() {
		return IoBytesSent;
	}

	public void setIoBytesSent(String ioBytesSent) {
		IoBytesSent = ioBytesSent;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTimeRunning() {
		return timeRunning;
	}

	public void setTimeRunning(String timeRunning) {
		this.timeRunning = timeRunning;
	}

	public String getDateStarted() {
		return dateStarted;
	}

	public void setDateStarted(String dateStarted) {
		this.dateStarted = dateStarted;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getIoLastRequest() {
		return IoLastRequest;
	}

	public void setIoLastRequest(String ioLastRequest) {
		IoLastRequest = ioLastRequest;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}


    
}