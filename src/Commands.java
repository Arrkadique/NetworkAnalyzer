
//it's enum with commands, that we can use in executor. this enum makes code scalable
//for example, we can add one more command and we shouldn't change different classes

public enum Commands {
    GET_INFO(1,"ufw version"),
    GET_STATUS(2, "ufw status"),
    ENABLE_FIREWALL(3, "ufw enable"),
    DISABLE_FIREWALL(4, "ufw disable"),
    RESET_FIREWALL(5, "ufw reset"),
    GET_OPEN_PORTS(6, "netstat -ntlp | grep LISTEN"),
    GET_ALL_CONNECTIONS(7, "sudo nmap -sn -PU 192.168.0.0/24");


    private final int commandId;
    private final String command;

    Commands(int commandId, String command) {
        this.commandId = commandId;
        this.command = command;
    }

    public int getCommandId() {
        return commandId;
    }

    public String getCommand() {
        return command;
    }

    public static String getById(int id) {
        for(Commands e : values()) {
            if(e.commandId == id) return e.command;
        }
        return null;
    }

}
