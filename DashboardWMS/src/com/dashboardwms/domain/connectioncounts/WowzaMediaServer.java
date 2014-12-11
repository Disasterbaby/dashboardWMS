package com.dashboardwms.domain.connectioncounts;


	public class WowzaMediaServer
	{
	    private String ConnectionsTotalRejected;

	    private String MessagesInBytesRate;

	    private String MessagesOutBytesRate;

	    private VHost VHost;

	    private String ConnectionsCurrent;

	    private String ConnectionsTotal;

	    private String ConnectionsTotalAccepted;

	    public String getConnectionsTotalRejected ()
	    {
	        return ConnectionsTotalRejected;
	    }

	    public void setConnectionsTotalRejected (String ConnectionsTotalRejected)
	    {
	        this.ConnectionsTotalRejected = ConnectionsTotalRejected;
	    }

	    public String getMessagesInBytesRate ()
	    {
	        return MessagesInBytesRate;
	    }

	    public void setMessagesInBytesRate (String MessagesInBytesRate)
	    {
	        this.MessagesInBytesRate = MessagesInBytesRate;
	    }

	    public String getMessagesOutBytesRate ()
	    {
	        return MessagesOutBytesRate;
	    }

	    public void setMessagesOutBytesRate (String MessagesOutBytesRate)
	    {
	        this.MessagesOutBytesRate = MessagesOutBytesRate;
	    }

	    public VHost getVHost ()
	    {
	        return VHost;
	    }

	    public void setVHost (VHost VHost)
	    {
	        this.VHost = VHost;
	    }

	    public String getConnectionsCurrent ()
	    {
	        return ConnectionsCurrent;
	    }

	    public void setConnectionsCurrent (String ConnectionsCurrent)
	    {
	        this.ConnectionsCurrent = ConnectionsCurrent;
	    }

	    public String getConnectionsTotal ()
	    {
	        return ConnectionsTotal;
	    }

	    public void setConnectionsTotal (String ConnectionsTotal)
	    {
	        this.ConnectionsTotal = ConnectionsTotal;
	    }

	    public String getConnectionsTotalAccepted ()
	    {
	        return ConnectionsTotalAccepted;
	    }

	    public void setConnectionsTotalAccepted (String ConnectionsTotalAccepted)
	    {
	        this.ConnectionsTotalAccepted = ConnectionsTotalAccepted;
	    }

		@Override
		public String toString() {
			return "WowzaMediaServer [ConnectionsTotalRejected="
					+ ConnectionsTotalRejected + ", MessagesInBytesRate="
					+ MessagesInBytesRate + ", MessagesOutBytesRate="
					+ MessagesOutBytesRate + ", VHost=" + VHost
					+ ", ConnectionsCurrent=" + ConnectionsCurrent
					+ ", ConnectionsTotal=" + ConnectionsTotal
					+ ", ConnectionsTotalAccepted=" + ConnectionsTotalAccepted
					+ "]";
		}
	    
	    
	}
