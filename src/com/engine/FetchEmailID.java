package com.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Queue;

import com.constants.Constants;
import com.model.DatabaseRow;


//This class is basically creating connection and statement for data extracting from
//database and have some exception handling mechanism.
public class FetchEmailID {

	QueryDB queryDBobj = new QueryDB();

	Queue<DatabaseRow> fetchIDs() {
		Queue<DatabaseRow> queue = null;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(Constants.database_url,
					Constants.databaseUsername, Constants.databasePassword);
			stmt = conn.createStatement();
			queue = queryDBobj.queryTable(conn, stmt);
			stmt.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
			}
		}
		return queue;
	}
}
