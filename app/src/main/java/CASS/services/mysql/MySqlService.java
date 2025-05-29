/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ctydi
 */
public class MySqlService extends SqlService {

    public MySqlService(String user, String password, String server, String port, String database) throws SQLException {
        super(user, password, server, port, database);
    }

    @Override
    public Connection createConnection() throws SQLException {
        Connection ret = DriverManager.getConnection(
                "jdbc:mysql://" + this.getServer() + ":" + this.getPort() + "/" + this.getDatabase(),
                this.getUser(), this.getPassword());
        return ret;
    }

}
