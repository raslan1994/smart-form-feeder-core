package com.raslan.sff.sql;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class SQLQueryHelperWithTransaction {
	public abstract void executionInTransactionBlock(Connection connection) throws SQLException;
	private void performTransaction() throws SQLException{
		//get connection
		Connection connection = SQLConnectionHelper.getInstance().getConnection();
		
		//turn off auto commit mode
		connection.setAutoCommit(false);
		
		try{
			//execute block
			executionInTransactionBlock(connection);
			
			//commit
			connection.commit();
		}catch(SQLException ex){
			//roll back
			connection.rollback();
			ex.printStackTrace();
		}finally{
			if(connection != null) connection.close();
		}
	}
	
	public void beginTransaction(){
		try{
			performTransaction();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
}
