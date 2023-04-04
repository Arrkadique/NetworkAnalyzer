
//it's enum with commands, that we can use in executor. this enum makes code scalable
//for example, we can add one more command and we shouldn't change different class

public enum Commands {
    GET_INFO(1,"ufw version","Get firewall info"),
    GET_STATUS(2, "ufw status", "Check is firewall active"),
    ENABLE_FIREWALL(3, "ufw enable", "Enable firewall"),
    DISABLE_FIREWALL(4, "ufw disable", "Disable firewall"),
    RESET_FIREWALL(5, "ufw reset", "Reset firewall settings"),
    GET_OPEN_PORTS(6, "netstat -ntlp | grep LISTEN", "Show open ports"),
    GET_ALL_CONNECTIONS(7, "sudo nmap -sn -PU 192.198.0.0/24",
            "Check all connections in your network");


    private final int commandId;
    private final String command;
    private final String description;

    Commands(int commandId, String command, String description) {
        this.commandId = commandId;
        this.command = command;
        this.description = description;
    }

    public int getCommandId() {
        return commandId;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public static String getById(int id) {
        for(Commands e : values()) {
            if(e.commandId == id) return e.command;
        }
        return null;
    }

}
