import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//This class need to execute different commands. Contain main method.

public class Executor {
    public static void main(String[] args) {

        String command;
        Scanner in = new Scanner(System.in);

        System.out.println("1. Get firewall info\n" +
                "2. Check is firewall active\n" +
                "3. Enable firewall\n" +
                "4. Disable firewall\n" +
                "5. Reset firewall settings\n" +
                "6. Show open ports\n" +
                "7. Check all connections to your network\n" +
                "Choose option: ");

        command = Commands.getById(Integer.parseInt(in.nextLine()));

        try {

            Process process = Runtime.getRuntime().exec(
                    command, null);
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println(reader.readLine().equals("Status: active") + "\n--------------\n");
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitVal = process.waitFor();
            System.out.println(process.waitFor());
            if (exitVal == 0) {
                System.out.println("Success!");
                //System.out.println(output);
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
}
