/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jterminal.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jterminal.table.FormattedTable;
import jterminal.manager.BaseCommander;
import jterminal.manager.JTerminalManager;

/**
 *
 * @author ctydi
 */
public class SqlCommander extends BaseCommander {

    private SqlService service;

    public SqlCommander(JTerminalManager manager) {
        super(manager);
        try {
            this.service = ServiceProvider.getMySql();
        } catch (SQLException ex) {
            this.getManager().addLine("ERROR: " + ex.getMessage());
        }
    }

    public SqlService getService() {
        return this.service;
    }

    @Override
    public String getName() {
        return "SQL EXPLORER";
    }

    @Override
    public int processInput(String command) {
        String check = command.trim().toUpperCase();
        try {
            int res = super.processInput(command);
            if (res != 0) {
                return res;
            }

            if (this.getService() == null) {
                this.getManager().addLine("No SQL Server Connected!");

                return 0;
            }
            this.getManager().addLine(command);

            if (check.indexOf("SELECT") == 0 || check.indexOf("SHOW") == 0) {

                ResultSet rs = this.getService().executeQuery(command);
                // this.printRaw(rs);
                this.printFormatted(rs);

            } else {

                this.getService().executeStatement(command);
                this.getManager().addLine("Statement Executed!");
            }
            return 1;
        } catch (Throwable ex) {
            try {
                ex.printStackTrace();
                this.getManager().addLine("ERROR: " + ex.getMessage());
            } catch (Throwable e) {
                this.getManager().addLine("UNKNOWN ERROR!");
            }

            return 0;

        }

    }

    private void printFormatted(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        String[] colNames = new String[rsmd.getColumnCount()];
        String line = "|";

        for (int x = 0; x < colNames.length; x++) {

            colNames[x] = rsmd.getColumnName(x + 1);

        }
        FormattedTable table = new FormattedTable(colNames);
        while (rs.next()) {
            String[] toAdd = new String[colNames.length];
            for (int index = 0; index < colNames.length; index++) {
                toAdd[index] = rs.getString(index + 1);
            }
            table.addDataLine(toAdd);

        }

        table.calculateWidths(this.getManager().getWidth());
        List<String> toPrint = table.getOutput();

        for (int index = 0; index < toPrint.size(); index++) {
            this.getManager().addLine(toPrint.get(index));

        }

    }

    private void printRaw(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        String[] colNames = new String[rsmd.getColumnCount()];
        String line = "|";
        for (int index = 0; index < colNames.length; index++) {
            colNames[index] = rsmd.getColumnName(index + 1);
            line = line + colNames[index] + "|";
        }
        this.getManager().addLine(line);

        while (rs.next()) {
            line = "|";
            for (int index = 0; index < colNames.length; index++) {
                line = line + rs.getString(colNames[index]) + "|";
            }

            this.getManager().addLine(line);

        }

    }

}
