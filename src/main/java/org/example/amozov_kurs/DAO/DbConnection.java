package org.example.amozov_kurs.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class DbConnection {

    private final Preferences prefs;

    public DbConnection() {
        this.prefs = Preferences.userRoot().node("pref");
    }

    public String getHost() {
        return prefs.get("host", "host");
    }

    public String getPort() {
        return prefs.get("port", "port");
    }

    public String getDbName() {
        return prefs.get("db_name", "db_name");
    }

    public String getSchema() {
        return prefs.get("schema", "schema");
    }

    public String getLogin() {
        return prefs.get("login", "login");
    }

    public String getPassword() {
        return prefs.get("pass", "pass");
    }


    public void saveSettings(String host,
                             String port,
                             String dbName,
                             String schema,
                             String login,
                             String pass) {
        prefs.put("host", host);
        prefs.put("port", port);
        prefs.put("db_name", dbName);
        prefs.put("schema", schema);
        prefs.put("login", login);
        prefs.put("pass", pass);
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:postgresql://" + getHost() + ":" + getPort() + "/" + getDbName() + "?currentSchema=" + getSchema();
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(connStr, getLogin(), getPassword());
    }

    public boolean canConnect() {
        try (Connection conn = getConnection()) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}