package com.raslan.sff.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Raslan Rauff on 5/4/2015.
 */
public class SQLParameterFactory {
	public static void bindParameters(PreparedStatement stmt,Object[] parameters){
        try{
            for(int i = 0; i < parameters.length; i++) {
	            if (parameters[i] instanceof String)
	                stmt.setString(i + 1, parameters[i].toString());
	            else if (parameters[i] instanceof Integer)
	                stmt.setInt(i + 1, (int) parameters[i]);
	            else if (parameters[i] instanceof Float)
	                stmt.setFloat(i + 1, (float) parameters[i]);
	            else if (parameters[i] instanceof Double)
	                stmt.setDouble(i + 1, (double) parameters[i]);
	            else if (parameters[i] instanceof Date) {
	                Date parmDate = (Date) parameters[i];
	                stmt.setDate(i + 1, new java.sql.Date(parmDate.getTime()));
	            } else if (parameters[i] instanceof Byte)
	                stmt.setByte(i + 1, (byte) parameters[i]);
	            else if (parameters[i] instanceof Byte[])
	                stmt.setBytes(i + 1, (byte[]) parameters[i]);
	        }
        }catch(SQLException ex){
            ex.printStackTrace();
        }

    }
}
