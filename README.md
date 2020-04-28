# Online Recruitment System (Release 2008)

Online Recruitment System is a web based exam tool which helps to take tests of the candidates for recruitment purposes as per company's requirements.
Initially built in 2005 as part of final year project was later updated in 2008. It is a web based application running on Tomcat Server and only worked on Windows operating system. It is a multi user application with Admin setting up the tests, while users applying for jobs and taking the online test. The number of machines supported by the application is unknown. The application is divided into two parts.

* **Administrator** who creates posts, categories, questions and custom test settings.  
* **User** who submits resume, applies for desired post and gives the tests.  

The application is restricted to LAN and does not much security features.

The project was updated to provide security, MVC, installer, help docs, exception handling, DB layer and other internal refactoring, although the UI work was never completed.

### Instructions to run Online Recruitment System

To run the application follow as below :

* Install jdk 1.5.0 (for windows).

* Install Apache Tomcat 4.0.1.

* Install Oracle 8i (or Oracle 9i) preferably in primary drive.
  (During installation if you get "protocol adapter error" during the initialization of Database press "Retry" for re-initialization)

* If Oracle is already installed initialize it by typing

       Name     :  internal
       Password :  oracle

       Type "startup" in the console to initialize the database.

* Create System DSN named "ORS".
    * Go to Control Panel -> Performance and Maintenance -> Administrative Tools -> Data Sources(ODBC)
    * In that click 'System DSN' and click 'Add' to create.
    * Select driver "Microsoft ODBC for Oracle".
    * Type DSN name "ORS", User name as "scott/tiger", Description and leave Server name as blank.         
    * Click ok to create a DSN.

* Log into SQL Plus of Oracle with name "scott" and password "tiger".

* Copy the tables and entries from file "ORS/db-scripts/install.txt" and paste into the console.

* Then copy from file "ORS/db-scripts/entries.txt" and paste into the console.

* Type "Commit" in the console followed by Enter and close SQL Plus.

* Replace the WEB-INF directory and add Web folder in "C:\Program Files\Apache Group\Tomcat 4.1\webapps. Need extra steps here which are unknown.

* Start Tomcat from start->Programs->Apache Tomcat 4.1 -> Start Tomcat.exe

* For Admin go to "http://localhost:8080/ORS/AdminLogin.html" while for User go to "http://localhost:8080/ORS/UserLogin.html" in the browser.


## Flow Diagram


   ![Flow Diagram](images/flow-diagram.png)


## Entity Relationship Diagram


   ![Entity Relationship Diagram](images/entity-diagram.png)
