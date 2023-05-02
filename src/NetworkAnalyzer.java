
//this class will get info about network status

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkAnalyzer {

    private boolean isFirewallActive;
    private boolean areThereOpenPorts;
    private boolean areThereUntrustedConnections;

    public boolean isFirewallActive() {
        return isFirewallActive;
    }

    public boolean isAreThereOpenPorts() {
        return areThereOpenPorts;
    }

    public boolean isAreThereUntrustedConnections() {
        return areThereUntrustedConnections;
    }

    public void checkIsActive(String commandOutput){
        String[] result = commandOutput.split("\n", 2);

        isFirewallActive = result[0].equals("Status: active");
        if(isFirewallActive){
            System.out.println(commandOutput);
        } else {
            System.out.println("Firewall is inactive\n");
        }
    }

    public void checkOpenPorts(String commandOutput){
        this.areThereOpenPorts = true;
        System.out.println(commandOutput);
    }

    public void checkNetworkConnections(String commandOutput){

        Pattern pattern = Pattern.compile("[(](.*?)[)]");
        Matcher matcher = pattern.matcher(commandOutput);
        List<String> lst = new ArrayList<>();
        while (matcher.find()) {
            lst.add(matcher.group(1));
        }

        if(!lst.isEmpty()){
            if(lst.get(1).equals("0 hosts up")){
                this.areThereUntrustedConnections = false;
                System.out.println("0 hosts up");
            } else{
                this.areThereUntrustedConnections = true;
                System.out.println(commandOutput);
            }
        }
    }
}
