package com.constants;


// please fill the necessary constants value with correct one if I left like *******
// because this information is specific to the user.
// Also provide work load value, you want to put over one thread at one time.

public class Constants {

	public static int port=587;
	public static String host_smtp="smtp.gmail.com";
	public static String databaseUsername="root";
	public static String databasePassword="root";
    public static String database_url= "jdbc:mysql://localhost:3306/test";
   
    // tables name one can change here if some other names followed
    public static String EmailCounter="EmailCounter";
    public static String EmailQueue="EmailQueue";
    public static String EmailPassword="EmailPassword";
    
    public static int work_Load_Over_One_Thread_At_oneTime=1;
}
