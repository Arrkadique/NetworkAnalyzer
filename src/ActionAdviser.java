import java.util.ArrayList;
import java.util.List;

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
        List<Commands> advices = new ArrayList<>();
        if(!isFirewallActive){
            advices.add(Commands.ENABLE_FIREWALL);
        }
        if(areThereOpenPorts){
            advices.add(Commands.DENY_INCOMING_CONNECTIONS);
        }
        if(areThereUntrustedConnections){
            advices.add(Commands.DENY_INCOMING_CONNECTIONS);
        }
    }
}
