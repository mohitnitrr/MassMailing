package com.engine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

import com.constants.Constants;
import com.model.DatabaseRow;

public class QueryDB {

	int fetch_mails_volume;
	ResultSet rs;

	QueryDB() {
		fetch_mails_volume = Constants.work_Load_Over_One_Thread_At_oneTime;
	}

	Queue<DatabaseRow> queryTable(Connection conn, Statement stmt)
			throws SQLException {

		Queue<DatabaseRow> queue = new LinkedList<DatabaseRow>();
		int id = 0;
		int maxId = 0;
		String sql;
		System.out.println("Querydb reached");

		sql = "select max(Id) as 'ID' from emailqueue";
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			maxId = rs.getInt("ID");
		}

		// from here we are calling Counter Table in database, but in a
		// transaction mode.
		// means only one thread is allow to access that table at a time.
		// logic : we read count value in this table that reside over first row
		// once we read it means we have range for this thread
		// id - id + workload(over one thread), as we give this work load to one
		// thread
		// In the same transaction we update that count value in Counter table
		// so that another thread should not fall
		// in the same range.

		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		sql = "SELECT Counter FROM " + Constants.EmailCounter
				+ " WHERE ID=1 FOR UPDATE";
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			id = rs.getInt("Counter");
		}
		if (maxId >= id + fetch_mails_volume) {
			sql = "UPDATE " + Constants.EmailCounter + " SET Counter=Counter+"
					+ fetch_mails_volume + " WHERE ID=1";
			stmt.executeUpdate(sql);
		}
		conn.commit();
		conn.setAutoCommit(true);// here the transaction ends.

		// once we have range define for this thread, we will fill this thread
		// with DataBaserows by reading EmailQueue table in range
		// id in between "id" and "id+workload".
		// id: from where to start
		// id+workload : where to end
		// reading of data from a table can be parallel.
		// return this full queue to MakeMessage so that it can then pass
		// one by one to SendMessage.

		sql = "Select ID, FromEmail,ToEmail,Subject,Body, password from  "
				+ Constants.EmailQueue + "  ,  " + Constants.EmailPassword
				+ "  where  " + Constants.EmailQueue + ".FromEmail =  "
				+ Constants.EmailPassword + ".Email AND ID>" + id + " AND ID<="
				+ (id + fetch_mails_volume);
		rs = stmt.executeQuery(sql);

		if (!rs.isBeforeFirst()) {
			queue.add(null);
			rs.close();
			return queue;
		}
		while (rs.next()) {
			int _id = rs.getInt("ID");
			String from_email_address = rs.getString("FromEmail");
			String to_email_address = rs.getString("ToEmail");
			String Subject = rs.getString("Subject");
			String body = rs.getString("body");
			String password = rs.getString("password");

			DatabaseRow databaseRow = new DatabaseRow(_id, from_email_address,
					to_email_address, Subject, body, password);

			queue.add(databaseRow);
		}
		rs.close();
		return queue;
	}

}
