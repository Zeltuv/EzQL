package tr.zeltuv.ezql.settings;

public class EzqlCredentials {

    private String host,database,username,password;
    private int maxPool, port;
    private boolean useSSL;

    public EzqlCredentials(String host, String database, String username, String password, int maxPool, int port, boolean useSSL) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.maxPool = maxPool;
        this.port = port;
        this.useSSL = useSSL;
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxPool() {
        return maxPool;
    }

    public int getPort() {
        return port;
    }

    public boolean useSSL() {
        return useSSL;
    }
}
