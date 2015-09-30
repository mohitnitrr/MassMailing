Before start to Run this Program I would like to share my idea with you :

As per the instructions speed is utmost requirement so keeping that in mind I am implementing 
multi-threading + multi-processing 

So the idea is as there are more then one number of server so each server is executing its own process.
But in each server we can have more then one thread running over it .

To decide the number of threads in a process of each server I took no of cores of CPU as a deciding factor.
Reason : As the no of cores of CPU is more then,  no of parallel threads running at the same time can be more .

So In short :

    server1              server2             server3 ..........................
	
	thread1              thread1             thread1
	thread2              thread2             thread2
	thread3              thread3             thread3
	   .                    .                   .
	   .                    .                   .
	   .                    .                   .          
	   \                    |                 /
	     \                  |               /
		   \                |             /
		     \              |           /
		       \            |         /
		             

    					 DATABASE
						 
						 
						 
						 

						 
So there are three tables in database :

EmailQueue:
id  from_email  to_email  subject  body
1    a@b.com     c@d.com   test     hi

EmailPassword:
No    email      password
1     a@b.com      ******

EmailCounter 
id   Count
1     0


lets look at each table content :

1. EmailQueue : This table contains id, from_email, to_email, subject, body
Each process or more concisely each thread will read this table in parallel mode.
when thread is reading this table in parallel they have something called id range 
id range is basically the ids of rows which are going to read by thread at particular time.
this id come from EmailCounter Table.

2.EmailCounter :
This table should be access in transaction mode. Only one thread is allow to read this table at a time.
when one thread is reading this table it will get count value and increment that value by 300 (work load on each thread at a time)
lets take example :  initially count value in table is 0
one thread will come and read this value and increment by 300,so count value is 300
but this thread is responsible to read 1-300 rows of EmailQueue and send mails parallel.

Now second thread of some other server come it will get count = 300, it will increment that value by 300 and put 600
but this thread will read row from id 301 to 600 of EmailQueue.

Send mails in parallels



3.EmailPasswords:
As we have to access to from_email account , we need credentials. As I believe that senders mails are not such random
on such types of application they all are repeated so we create a separate table to sender_mail and credentials.


Step to run this programs:

1.Just open this project as a java project in eclipse.

2. Download "mysql-connector-java-5.1.16-bin.jar" and "mail.jar"
    put it into lib folder of the project.
	I try to package it with project but due to some security reason not able to send over gmail.
	if any path/pointer you needed for download these jars let me know.

3.Set up the database with these tables, I have send you one file called "SqlQueries" that will help you to create therse tables in Mysql.

4.Insert some test data in tables.

5.In com.constant.Constant.java you have to put your credentials and url of database .
  I declared them as a static variables once you declared them, it will be accessible in other class.
  
6.Related jars are already packaged with this project. So you don't have to worry about that.

7.Run Main.java file in this project as a java application.

8.If you have more machines and are connected to common database then same program can be run on different machine(servers).
  they work parallel finally end up with small time to complete this task.












