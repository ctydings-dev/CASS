/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services;

import CASS.services.mysql.MySqlService;
import CASS.services.mysql.SqlAddressService;
import CASS.services.mysql.SqlInvoiceService;
import CASS.services.mysql.SqlItemService;
import CASS.services.mysql.SqlPersonService;
import CASS.services.mysql.SqlTypeService;
import java.sql.SQLException;

/**
 *
 * @author ctydi
 */
public class ServiceProvider {

    private static String user = "";

    private static String password = "";

    private static String server = "localhost";

    private static int port = 3306;

    private static String database = "";

    public static AddressService getAddressService() throws SQLException {
        return new SqlAddressService(getMySql());
    }

    public static PersonService getPersonService() throws SQLException {
        return new SqlPersonService(getMySql());
    }

    public static ItemService getItemService() throws SQLException {
        return new SqlItemService(getMySql());
    }

    public static TypeService getTypeService() throws SQLException {
        return new SqlTypeService(getMySql());
    }

    public static InvoiceService getInvoiceService() throws SQLException {
        return new SqlInvoiceService(getMySql(), getItemService());
    }

    public static MySqlService getMySql() throws SQLException {

        return new MySqlService(user, password, server, "" + port, database);

    }

}
