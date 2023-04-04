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
        Process process;
        BufferedReader reader;
        StringBuilder output;

        process = Runtime.getRuntime().exec(
                Commands.GET_STATUS.getCommand(), null);
        reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        networkAnalyzer.checkIsActive(reader.readLine());

        process = Runtime.getRuntime().exec(
                Commands.GET_OPEN_PORTS.getCommand(), null);
        reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        output = new StringBuilder();
        String line = reader.readLine();
        if(line.equals("Active Internet connections (only servers)")){
            output.append(line);
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            networkAnalyzer.checkOpenPorts(output.toString(),true);
        } else {
            networkAnalyzer.checkOpenPorts("There aren't open ports (max safe)",
                    false);
        }

        process = Runtime.getRuntime().exec(
                Commands.GET_ALL_CONNECTIONS.getCommand(), null);
        reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        output = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        networkAnalyzer.checkNetworkConnections(output.toString());


    }
}
