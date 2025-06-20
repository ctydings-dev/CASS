/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.data.BaseDTO;
import CASS.data.BaseSearchParameter;
import CASS.data.TypeDTO;
import CASS.search.TypeAssignmentTable;
import CASS.util.DataObjectGenerator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
        return this.createStatement().executeQuery(query.toUpperCase());
    }

    public boolean executeStatement(String sql) throws SQLException {
        return this.createStatement().execute(sql.toUpperCase());
    }

    public int getIntValue(ResultSet rs, String name) throws SQLException {
        return rs.getInt(name);
    }

    public boolean getBooleanValue(ResultSet rs, String name) throws SQLException {
        return rs.getBoolean(name);
    }

    
    
    public ResultSet getAllForTable(String tableName) throws SQLException{
        
        String query = "SELECT * FROM " + tableName;
        
        return this.executeQuery(query);
    }
    
    
    public ResultSet getForId(String tableName, String idName, int id) throws SQLException{
        String query = "SELECT * FROM "+ tableName + " WHERE "+idName +" = "+
                id+ ";";
               return this.executeQuery(query);
        
    }
    
    
    
    public Integer insertAndGet(BaseSearchParameter toInsert) throws SQLException{
        
        String stmt = toInsert.getInsertStatement();
        this.executeStatement(stmt);
        return this.getKey(toInsert);
        
        
    }
    
    
    
    
    public Integer getKey(BaseSearchParameter params) throws SQLException{
        
        String query = params.getSearchQuery();
        
        ResultSet rs = this.executeQuery(query);
        
        rs.next();
        
        return rs.getInt(params.getTable().getIdName());
        
    }
    
    
    public Integer addAssignment(TypeAssignmentTable table, BaseDTO target, TypeDTO type) throws SQLException{
        String query = "INSERT INTO " + table.getTableName() + "(" + table.getTargetName();
        query = query + ", " + table.getTypeName()+ ") VALUES (";
        query = query + target.getKey() + "," + type.getKey()+ ");";
        
        this.executeStatement(query);
        
        return this.getTypeAssignment(table, target, type);
    }
    
    
    
    public Integer getTypeAssignment(TypeAssignmentTable table, BaseDTO target, TypeDTO type) throws SQLException{
        
             String query ="SELECT " + table.getIdName() + " FROM " + table.getTableName() + " WHERE ";
             
          query = query + table.getTargetName() + " = " + target.getKey() + " AND " + table.getTypeName();
           query = query + " = "+ type.getKey() + ";";             
                ResultSet rs = this.executeQuery(query);
                rs.next();
        return rs.getInt(table.getIdName());
        
        
    }
    
    
    public List<BaseDTO> getThoseWithTypeAssignments(TypeAssignmentTable table, TypeDTO type) throws SQLException{
       List<BaseDTO> ret = DataObjectGenerator.createList();
       
       
       String query = "SELECT " + table.getTargetName() + " FROM " + table.getTableName();
       
       query = query + " WHERE " + table.getTypeName() + " = " + type.getKey();
        
        ResultSet rs = this.executeQuery(query);
        while(rs.next()){
            
            BaseDTO toAdd = new BaseDTO(rs.getInt(table.getTargetName()));
            ret.add(toAdd);
        }
        return ret;
        
    }
    
    
    
    
    
    
    
    public static String formatString(String in){
        if(in == null){
            return "NULL";
        }
        return in.trim().toUpperCase();
    }
    
    public abstract Connection createConnection() throws SQLException;

}
