package com.raslan.sff.sql;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.raslan.sff.core.Config;

public class SQLConnectionHelper extends Config{
    private MysqlDataSource dataSource;
    
    private final static SQLConnectionHelper singletonObj = new SQLConnectionHelper();
    public static SQLConnectionHelper getInstance(){
            return singletonObj;
    }
    
    private SQLConnectionHelper(){}
    //initialize
    {
        try {
            this.dataSource = new MysqlConnectionPoolDataSource();
            this.dataSource.setUser(USERNAME);
            this.dataSource.setPassword(PASSWORD);
            this.dataSource.setServerName(HOST);
            this.dataSource.setPort(PORT);
            this.dataSource.setDatabaseName(DATABASE);
            this.dataSource.setLocalSocketAddress(HOST);
            this.dataSource.setURL("jdbc:mysql://" + HOST + ":" + PORT +"/" + DATABASE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    public synchronized Connection getConnection(){
        //declare
        Connection connection = null;
        //setup connection
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {e.printStackTrace();}
        //return
        return connection;
    }
}
