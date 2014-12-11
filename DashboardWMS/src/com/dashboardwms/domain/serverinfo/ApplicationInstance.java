package com.dashboardwms.domain.serverinfo;

public class ApplicationInstance
{
    private String httpSessionCount;

    private String sanJoseConnectionCount;

    private String connectionsTotalAccepted;

    private String name;

    private String cupertinoConnectionCount;

    private String rtmpSessionCount;

    private String timeRunning;

    private String connectionsTotalRejected;

    private String rtmpConnectionCount;

    private String rtpConnectionCount;

    private String rtpSessionCount;

    private String smoothConnectionCount;

    private String connectionsTotal;

    private String connectionsCurrent;

    private HTTPSession httpSession;
    
    private RTPSession[] rtpSession;

	public String getHttpSessionCount() {
		return httpSessionCount;
	}

	public void setHttpSessionCount(String httpSessionCount) {
		this.httpSessionCount = httpSessionCount;
	}

	public String getSanJoseConnectionCount() {
		return sanJoseConnectionCount;
	}

	public void setSanJoseConnectionCount(String sanJoseConnectionCount) {
		this.sanJoseConnectionCount = sanJoseConnectionCount;
	}

	public String getConnectionsTotalAccepted() {
		return connectionsTotalAccepted;
	}

	public void setConnectionsTotalAccepted(String connectionsTotalAccepted) {
		this.connectionsTotalAccepted = connectionsTotalAccepted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCupertinoConnectionCount() {
		return cupertinoConnectionCount;
	}

	public void setCupertinoConnectionCount(String cupertinoConnectionCount) {
		this.cupertinoConnectionCount = cupertinoConnectionCount;
	}

	public String getRtmpSessionCount() {
		return rtmpSessionCount;
	}

	public void setRtmpSessionCount(String rtmpSessionCount) {
		this.rtmpSessionCount = rtmpSessionCount;
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

	public String getRtmpConnectionCount() {
		return rtmpConnectionCount;
	}

	public void setRtmpConnectionCount(String rtmpConnectionCount) {
		this.rtmpConnectionCount = rtmpConnectionCount;
	}

	public String getRtpConnectionCount() {
		return rtpConnectionCount;
	}

	public void setRtpConnectionCount(String rtpConnectionCount) {
		this.rtpConnectionCount = rtpConnectionCount;
	}

	public String getRtpSessionCount() {
		return rtpSessionCount;
	}

	public void setRtpSessionCount(String rtpSessionCount) {
		this.rtpSessionCount = rtpSessionCount;
	}

	public String getSmoothConnectionCount() {
		return smoothConnectionCount;
	}

	public void setSmoothConnectionCount(String smoothConnectionCount) {
		this.smoothConnectionCount = smoothConnectionCount;
	}

	public String getConnectionsTotal() {
		return connectionsTotal;
	}

	public void setConnectionsTotal(String connectionsTotal) {
		this.connectionsTotal = connectionsTotal;
	}

	public String getConnectionsCurrent() {
		return connectionsCurrent;
	}

	public void setConnectionsCurrent(String connectionsCurrent) {
		this.connectionsCurrent = connectionsCurrent;
	}

	public HTTPSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HTTPSession httpSession) {
		this.httpSession = httpSession;
	}

	public RTPSession[] getRtpSession() {
		return rtpSession;
	}

	public void setRtpSession(RTPSession[] rtpSession) {
		this.rtpSession = rtpSession;
	}
    
    
}