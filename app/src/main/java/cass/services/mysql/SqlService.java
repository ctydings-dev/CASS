/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cass.services.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ctydi
 */
public abstract class SqlService {

    private String user;

    private String password;

    private String server;

    private String port;

    private String database;

    private Connection con;

    public SqlService(String user, String password, String server, String port, String database) throws SQLException {
        this.user = user;
        this.password = password;
        this.server = server;
        this.port = port;
        this.database = database;
        this.con = this.createConnection();
    }

    public String getUser() {
        return user;
    }

    protected String getPassword() {
        return password;
    }

    public String getServer() {
        return server;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public Connection getConnection() {
        return this.con;
    }

    public Statement createStatement() throws SQLException {
        return this.getConnection().createStatement();
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return this.createStatement().executeQuery(query);
    }

    public boolean executeStatement(String sql) throws SQLException {
        return this.createStatement().execute(sql);
    }

    public int getIntValue(ResultSet rs, String name) throws SQLException {
        return rs.getInt(name);
    }

    public boolean getBooleanValue(ResultSet rs, String name) throws SQLException {
        return rs.getBoolean(name);
    }

    public abstract Connection createConnection() throws SQLException;

}
