import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//This class need to execute different commands. Contain main method.

public class Executor {
    public static void main(String[] args) throws IOException, InterruptedException {

        String command;
        Scanner in = new Scanner(System.in);
        NetworkAnalyzer networkAnalyzer = new NetworkAnalyzer();

        command = checkNetworkStatus();
        if(command.equals("0")){
            System.out.println();
            for (Commands a: Commands.values()) {
                System.out.println(a.getCommandId() + ". " + a.getDescription());
            }
            System.out.println("Choose option: ");
            command = in.nextLine();
        }
        command = Commands.getById(Integer.parseInt(command));

        System.out.println(getCommandOutput(command));
    }

    public static String checkNetworkStatus() throws IOException {
        NetworkAnalyzer networkAnalyzer = new NetworkAnalyzer();
        ActionAdviser actionAdviser;

        System.out.println("\nStart checking firewall status...");
        networkAnalyzer.checkIsActive(getCommandOutput(Commands.GET_STATUS.getCommand()));

        System.out.println("Start checking open ports...");
        networkAnalyzer.checkOpenPorts(getCommandOutput(Commands.GET_OPEN_PORTS.getCommand()));

        System.out.println("Start checking network connections...");
        networkAnalyzer.checkNetworkConnections(getCommandOutput(Commands.GET_ALL_CONNECTIONS.getCommand()));


        System.out.println("Prepare actions to make your network safety...");
        actionAdviser = new ActionAdviser(networkAnalyzer.isFirewallActive(),
                networkAnalyzer.isAreThereOpenPorts(), networkAnalyzer.isAreThereUntrustedConnections());
        actionAdviser.AnalyzeStatus();

        return actionAdviser.showAdvices();
    }

    public static String getCommandOutput(String command) throws IOException {
        Process process = Runtime.getRuntime().exec(
                command, null);
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        return String.valueOf(output);
    }
}
