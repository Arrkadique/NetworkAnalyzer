
//this class will get info about network status

public class NetworkAnalyzer {

    private boolean isFirewallActive;
    private boolean areThereOpenPorts;
    private boolean areThereUntrustedConnections;


    public void checkIsActive(String commandOutput){
        System.out.println("Start checking firewall status...");
        isFirewallActive = commandOutput.equals("Status: active");
        if(isFirewallActive){
            System.out.println(commandOutput);
        } else {
            System.out.println("Firewall is inactive");
        }
    }

    public void checkOpenPorts(String commandOutput, boolean areThereOpenPorts){
        System.out.println("\nStart checking open ports...");
        this.areThereOpenPorts = areThereOpenPorts;
        System.out.println(commandOutput);
    }

    public void checkNetworkConnections(String commandOutput){
        System.out.println("Start checking network connections...");
        this.areThereUntrustedConnections = true;
        System.out.println(commandOutput);
    }

    public void makingActions(){

    }
}
