public class ActionAdviser {

    private boolean isFirewallActive;
    private boolean areThereOpenPorts;
    private boolean areThereUntrustedConnections;

    public ActionAdviser(boolean isFirewallActive, boolean areThereOpenPorts, boolean areThereUntrustedConnections) {
        this.isFirewallActive = isFirewallActive;
        this.areThereOpenPorts = areThereOpenPorts;
        this.areThereUntrustedConnections = areThereUntrustedConnections;
    }

    public void AnalyzeStatus(){

    }
}
