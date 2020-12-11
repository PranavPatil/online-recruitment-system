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

* Install Apache Tomcat 5.0

* Install Oracle 8i (or Oracle 9i) preferably in primary drive.
  (During installation if you get "protocol adapter error" during the initialization of Database press **"Retry"** for re-initialization)

* If Oracle is already installed initialize it by typing

       Name     :  internal
       Password :  oracle

       Type "startup" in the console to initialize the database.

* Create System DSN named `ORS`.
    * Go to **Control Panel** `->` **Performance and Maintenance** `->` **Administrative Tools** `->` **Data Sources(ODBC)**
    * In that click `System DSN` and click `Add` to create.
    * Select driver `Microsoft ODBC for Oracle`.
    * Type DSN name `ORS`, User name as `scott/tiger`, Description and leave Server name as blank.         
    * Click ok to create a DSN.

* Log into SQL Plus of Oracle with name `scott` and password `tiger`.

* Run the create table script from file `ORS/db-scripts/install.txt` and add entries using `ORS/db-scripts/entries.txt`.

* Paste the tools.jar from `C:\Program Files\Java\jdk1.5.0_06\lib` to `C:\Program Files\Apache Software Foundation\Tomcat 5.0\common\lib`

* Remove the default servlet invoker code from the application definition ie web.xml as follows.

       <servlet-mapping>
            <servlet-name>invoker</servlet-name>
            <url-pattern>/servlet/*</url-pattern>
       </servlet-mapping>


* Change the port no of server from `C:\Program Files\Apache Software Foundation\Tomcat 5.0\conf\server.xml` file.

* Paste `ORS.xml` in `C:\Program Files\Apache Software Foundation\Tomcat 5.0\conf\Catalina\localhost`

* Replace the WEB-INF directory and add Web folder in `C:\Program Files\Apache Group\Tomcat 5.0\webapps`.

* Start Tomcat from **start** `->` **Programs** `->` **Apache Tomcat 5.0** `->` **Start Tomcat.exe**

* For Admin go to "http://localhost:8080/ORS/AdminLogin.html" while for User go to "http://localhost:8080/ORS/UserLogin.html" in the browser.


## Home Page


   ![Home Page](images/Home.png)


## Admin Area


   ![Admin Login](images/admin/AdminLogin.png)


   ![Admin Home](images/admin/AdminHome.png)


### Admin Accounts


   ![Admin Accounts](images/admin/AdminAccounts.png)


   ![Create Admin](images/admin/CreateAdmin.png)


   ![View Admins](images/admin/AdminView.png)


   ![Admin Accessibility](images/admin/Accessibility.png)


### Exam Management


   ![Exam Management](images/admin/ExamManagement.png)


   ![Post Management](images/admin/Post.png)


   ![Create Post](images/admin/CreatePost.png)


   ![Test Management](images/admin/Test.png)


   ![Create Test](images/admin/CreateTest.png)


   ![Publish Test](images/admin/Publish.png)


   ![Question Management](images/admin/Question.png)


   ![Create Question](images/admin/CreateQuestion.png)


   ![Create Multiple Choice](images/admin/CreateMultipleChoice.png)


   ![Create Multiple Answer](images/admin/CreateMultipleAnswer.png)


   ![Create True Or False](images/admin/CreateTrueOrFalse.png)


   ![Create True Or False](images/admin/SearchQuestions.png)


   ![Category Management](images/admin/CategoryManagement.png)


   ![Create Category](images/admin/CreateCategory.png)


### Candidate Processing


   ![View Results](images/admin/ResultView.png)


   ![Result Details](images/admin/ResultDetails.png)


   ![View Selected](images/admin/SelectedView.png)


   ![View Employees](images/admin/EmpView.png)


### System Settings


   ![System Log](images/admin/SystemLog.png)


### Help


   ![Admin Help](images/admin/AdminHelp.png)


### Credits


   ![Credits](images/admin/Credits.png)


## User Area


   ![User Registration](images/user/UserReg.png)


   ![User Login](images/user/UserLogin.png)


   ![User Home](images/user/UserHome.png)


   ![Post View](images/user/PostView.png)


   ![Choose Test](images/user/ChooseTest.png)


   ![Test Rules](images/user/TestRules.png)


   ![Start Test](images/user/StartTest.png)


   ![Test Page](images/user/TestPage.png)


   ![Review Test](images/user/ReviewTest.png)


   ![Thanks](images/user/Thanks.png)


## Flow Diagram


   ![Flow Diagram](images/flow-diagram.png)


## Entity Relationship Diagram


   ![Entity Relationship Diagram](images/entity-diagram.png)
