import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActionAdviser {

    private final boolean isFirewallActive;
    private final boolean areThereOpenPorts;
    private final boolean areThereUntrustedConnections;

    List<Commands> advices;

    public ActionAdviser(boolean isFirewallActive, boolean areThereOpenPorts, boolean areThereUntrustedConnections) {
        this.isFirewallActive = isFirewallActive;
        this.areThereOpenPorts = areThereOpenPorts;
        this.areThereUntrustedConnections = areThereUntrustedConnections;
        advices = new ArrayList<>();
    }

    public void AnalyzeStatus(){
        if(!isFirewallActive){
            advices.add(Commands.ENABLE_FIREWALL);
        }
        if(areThereOpenPorts){
            advices.add(Commands.DENY_INCOMING_CONNECTIONS);
        }
        if(areThereUntrustedConnections && !areThereOpenPorts){
            advices.add(Commands.DENY_INCOMING_CONNECTIONS);
        }
        if(advices.isEmpty()){
            advices.add(Commands.RESET_FIREWALL);
        }
    }

    public String showAdvices(){
        Scanner in = new Scanner(System.in);
        System.out.println("To make your network safety you should: ");
        for (Commands a: advices) {
            System.out.println(a.getCommandId() + ". " + a.getDescription());
        }
        System.out.println("0. Show all options");

        return in.nextLine();
    }
}
