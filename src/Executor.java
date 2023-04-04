import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//This class need to execute different commands. Contain main method.

public class Executor {
    public static void main(String[] args) throws IOException, InterruptedException {

        String command;
        Scanner in = new Scanner(System.in);

        checkNetworkStatus();

        System.out.println();
        for (Commands a: Commands.values()) {
            System.out.println(a.getCommandId() + ". " + a.getDescription());
        }
        System.out.println("Choose option: ");

        command = Commands.getById(Integer.parseInt(in.nextLine()));

        Process process = Runtime.getRuntime().exec(
                command, null);
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        int exitVal = process.waitFor();
        if (exitVal == 0) {
            System.out.println("Success!");
            System.out.println(output);
        } else {
            StringBuilder errorOutput = new StringBuilder();

            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));

            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorOutput.append(errorLine).append("\n");
            }
            System.out.println(errorOutput);
        }
    }

    public static void checkNetworkStatus() throws IOException {
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
