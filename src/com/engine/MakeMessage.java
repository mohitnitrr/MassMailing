package com.engine;

import java.util.LinkedList;
import java.util.Queue;

import javax.mail.MessagingException;
import javax.mail.Session;

import com.model.DatabaseRow;

// This class is basically maintaining a queue of DataBaserows.
// once the object of this class is created in send message, it fill its queue of 
// with DatabaseRow objects and send it to SendMessage class one by one.
// But once all elements of queue got consumed its again fill it from database
// with the help of FetchEmailID.java file.
// Every thread either on same server or different server has its own object of this class.
// means every thread has its own queue that is different from other thread.


public class MakeMessage {

	FetchEmailID fetchEmailIDobj;
	Queue<DatabaseRow> queue;
	Session session;
	volatile boolean finishedJobsFlag;

	MakeMessage() {
		this.fetchEmailIDobj = new FetchEmailID();
		this.queue = new LinkedList<DatabaseRow>();
		this.finishedJobsFlag = false;

	}

	DatabaseRow getNextMessage() throws MessagingException {
		

		if (queue.size() < 1) {
			queue = fetchEmailIDobj.fetchIDs();
		}
		

		if (queue.size() == 1 && queue.peek() == null) {
			finishedJobsFlag = true;
			return null;
		}

		DatabaseRow databaseRow = queue.poll();
		return databaseRow;
	}
}
