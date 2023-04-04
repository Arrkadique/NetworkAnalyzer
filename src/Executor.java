import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//This class need to execute different commands. Contain main method.

public class Executor {
    public static void main(String[] args) {

        String command;
        Scanner in = new Scanner(System.in);

        System.out.println("""
                1. Get firewall info
                2. Check is firewall active
                3. Enable firewall
                4. Disable firewall
                5. Reset firewall settings
                6. Show open ports
                7. Check all connections to your network
                Choose option:\s""");

        command = Commands.getById(Integer.parseInt(in.nextLine()));

        try {

            checkNetworkStatus();

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

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void checkNetworkStatus() throws IOException {
        NetworkAnalyzer networkAnalyzer = new NetworkAnalyzer();

        System.out.println("Start checking firewall status...");
        networkAnalyzer.checkIsActive(getCommandOutput(Commands.GET_STATUS.getCommand()));

        System.out.println("\nStart checking open ports...");
        networkAnalyzer.checkOpenPorts(getCommandOutput(Commands.GET_OPEN_PORTS.getCommand()));

        System.out.println("Start checking network connections...");
        networkAnalyzer.checkNetworkConnections(getCommandOutput(Commands.GET_ALL_CONNECTIONS.getCommand()));

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
