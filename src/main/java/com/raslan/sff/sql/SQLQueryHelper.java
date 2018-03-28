package com.raslan.sff.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 *
 * @author Raslan Rauff
 */
public class SQLQueryHelper {
    private Connection con;
    private SQLConnectionHelper connectionHelper = SQLConnectionHelper.getInstance();
    
    public int executeUpdate(String query,Object[] parameters, boolean isNeededToCloseConnection, Connection customConnection){
    	//declare
    	int data = 0;
    	PreparedStatement stmt = null;
    	//get connection
    	if(customConnection != null)
    		con = customConnection;
    	else
    		con = connectionHelper.getConnection();
    	
    	try{
    		stmt = con.prepareStatement(query);
    		
            //bind parameters
            if(parameters != null)
                SQLParameterFactory.bindParameters(stmt, parameters);
            
            //execute
            data = stmt.executeUpdate();
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}finally{
    		if(stmt!=null && isNeededToCloseConnection)try{stmt.close();}catch(SQLException ex){ex.printStackTrace();}
    		if(con!=null && isNeededToCloseConnection)try{con.close();}catch(SQLException ex){ex.printStackTrace();}
    	}
    	
    	//return
    	return data;
    }
    
    public int executeUpdate(String query,Object[] parameters){
    	return executeUpdate(query, parameters, true, null);
    }
    
    /**
     * 
     * @param SQL query
     * @param Parameters (according to the order)
     * @return generated key, if query fails -1 will be output
     */
    public int executeUpdateAndGetGeneratedKey(String query, Object[] parameters){
    	return executeUpdate(query, parameters, true, null);
    }
    
    /**
     * 
     * @param query
     * @param parameters
     * @param isNeededToCloseConnection
     * @param customConnection
     * @return generated key, if query fails -1 will be output
     */
    public int executeUpdateAndGetGeneratedKey(String query, Object[] parameters, boolean isNeededToCloseConnection, Connection customConnection){
    	//declaring required objects
    	int key = -1;
    	PreparedStatement stmt = null;
    	
    	//get connection
    	if(customConnection != null)
    		con = customConnection;
    	else
    		con = connectionHelper.getConnection();
    	
    	try{
    		//prepare objects
    		ResultSet rsGenKey = null;
    		stmt = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
           
            //bind parameters
            if(parameters != null)
                SQLParameterFactory.bindParameters(stmt, parameters);
           
            //execute
            stmt.executeUpdate();
            
            //get keys
            rsGenKey = stmt.getGeneratedKeys();
            
            //bind key
            if(rsGenKey.next()){
            	key = rsGenKey.getInt("GENERATED_KEY");
            }
            //close
            stmt.close();
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}finally{
    		if(stmt!=null && isNeededToCloseConnection)try{stmt.close();}catch(SQLException ex){ex.printStackTrace();}
    		if(con!=null && isNeededToCloseConnection)try{con.close();}catch(SQLException ex){ex.printStackTrace();}
    	}
    	
    	//return 
    	return key;
    }
    
    public void executeQuery(String query,Object[] parameters, boolean isNeededToCloseConnection, Connection customConnection){
    	//declaring required objects
    	PreparedStatement stmt = null;
    	
    	//get connection
    	if(customConnection != null)
    		con = customConnection;
    	else
    		con = connectionHelper.getConnection();
       
    	try{
    		stmt = con.prepareStatement(query);
           
            //bind parameters
            if(parameters != null)
                SQLParameterFactory.bindParameters(stmt, parameters);
           
            //execute
            stmt.execute();
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}finally{
    		if(stmt!=null && isNeededToCloseConnection)try{stmt.close();}catch(SQLException ex){ex.printStackTrace();}
    		if(con!=null && isNeededToCloseConnection)try{con.close();}catch(SQLException ex){ex.printStackTrace();}
    	}
    }
    
    public void executeQuery(String query,Object[] parameters){
    	executeQuery(query, parameters, true, null);
    }
   
    
    public void executeProcedure(String procedure,Object[] parameters, boolean isNeededToCloseConnection, Connection customConnection){
    	//declaring required objects
    	CallableStatement stmt = null;
    	
    	//get connection
    	if(customConnection != null)
    		con = customConnection;
    	else
    		con = connectionHelper.getConnection();
       
    	try{
    		stmt = con.prepareCall(procedure);
           
            //bind parameters
            if(parameters != null)
                SQLParameterFactory.bindParameters(stmt, parameters);
           
            //execute
            stmt.execute();
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}finally{
    		if(stmt!=null && isNeededToCloseConnection)try{stmt.close();}catch(SQLException ex){ex.printStackTrace();}
    		if(con!=null && isNeededToCloseConnection)try{con.close();}catch(SQLException ex){ex.printStackTrace();}
    	}
    }
    
    public void executeProcedure(String procedure,Object[] parameters){
    	executeProcedure(procedure, parameters, true, null);
    }
}
