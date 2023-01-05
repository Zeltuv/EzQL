package tr.zeltuv.ezql.objects;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import tr.zeltuv.ezql.objects.EzqlColumn;
import tr.zeltuv.ezql.objects.EzqlTable;
import tr.zeltuv.ezql.settings.DefaultHikariSettings;
import tr.zeltuv.ezql.settings.EzqlCredentials;
import tr.zeltuv.ezql.settings.CustomHikariSettings;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class EzqlDatabase {

    private EzqlCredentials credentials;
    private EzqlQuery ezqlQuery = new EzqlQuery(this);
    private CustomHikariSettings customHikariSettings;
    private HikariDataSource hikariDataSource;
    private Map<String, EzqlTable> tables = new HashMap<>();

    /**
     * @param credentials Needed for the API to connect to your database server
     *                    <p>
     *                    Main constructor, will apply defaults settings for hikari config
     */
    public EzqlDatabase(EzqlCredentials credentials) {
        this.credentials = credentials;
        this.customHikariSettings = new DefaultHikariSettings();
    }

    /**
     * @param credentials          Needed for the API to connect to your database server
     * @param customHikariSettings Changes the hikari config overriding it with your custom settings
     */
    public EzqlDatabase(EzqlCredentials credentials, CustomHikariSettings customHikariSettings) {
        this.credentials = credentials;
        this.customHikariSettings = customHikariSettings;
    }

    public Connection getConnection() throws SQLException {
        try {
            if (hikariDataSource == null) {
                connect();
            }
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void connect() {
        HikariConfig hikariConfig = customHikariSettings.getHikariConfig(credentials);

        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public void disconnect() {
        hikariDataSource.close();
    }

    public EzqlTable getTable(String name) {
        return tables.get(name);
    }

    public EzqlTable addTable(String name, EzqlColumn... ezqlColumns) {
        EzqlTable ezqlTable = new EzqlTable(name, this, ezqlColumns);

        tables.put(name, ezqlTable);

        ezqlQuery.createTable(ezqlTable);

        return ezqlTable;
    }


    protected EzqlQuery getEzqlQuery() {
        return ezqlQuery;
    }
}
