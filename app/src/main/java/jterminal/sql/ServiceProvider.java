/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jterminal.sql;

import java.sql.SQLException;

/**
 *
 * @author ctydi
 */
public class ServiceProvider {

    private static String user = "ctydings";

    private static String password = "Per@grin1";

    private static String server = "localhost";

    private static int port = 3306;

    private static String database = "DA19785";

    public static MySqlService getMySql() throws SQLException {

        return new MySqlService(user, password, server, "" + port, database);

    }

}
