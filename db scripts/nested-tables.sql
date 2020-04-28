/*****************************************************************************************/

create sequence Admin_Id increment by 1
/

Drop table Admin
/

create table Admin
(Admin_Id number(35) primary key,
 Fname varchar2(20) not null,
 Lname varchar2(20) not null,
 Login varchar2(20) not null,
 Password varchar2(15),
 Email varchar2(30),
 Designation varchar2(25)
)
/

insert into Admin values ( Admin_Id.nextval,'Pranav','Patil',
			  'Pranav','sscl',
                          'pranav1234@yahoo.com','Administrator')
/

/*****************************************************************************************/

create sequence User_Id increment by 1
/

Drop table Users
/

create table Users
(User_Id number(35) primary key,
 FName varchar2(20) not null,
 MName varchar2(20) not null,
 LName varchar2(20) not null,
 Login varchar2(20) not null,
 Password varchar2(15),
 Gender varchar2(3),            /* M for Male, F for Female */
 Telephone number(12),
 Address varchar(100),
 Email varchar2(30),
 Date_of_Birth date,
 Qualification varchar2(25),
 Branch varchar2(45),
 Experience number(15))
/

insert into Users values ( User_Id.nextval,
			  'Pranav','Sachin','Patil','Pranav',
                          'passwd','M',02056788981,
                          'Pune, India',
			  'pranav123456@yahoo.com','23-DEC-75','BE','Production','1-2yrs')
/

insert into Table(select Attempted from Users
		 where User_Id = UserId)
                 values (ATTEMPT_TY());


select User_Id,FNAME,Password,N.Category_Id
from Users,TABLE(Users.Attempted)N;

delete Table(select Attempted from Users where User_Id = 1)N;

create table Test_Category(Test_Id number(20) references Test(Test_Id),
                           Category_Id number(10) references Category(Category_Id),
			   Publish number(5)  check(Publish in(0,1)))
/

insert into Test_Category values(1,1,0)
/
insert into Test_Category values(2,1,0)
/

Update Test_Category set Publish = 0 where Test_Id=1;

select * from Test_Category;

 select count(rownum)
 from Users,Table(Users.Attempted)N
 where User_Id = 1 and N.Category_Id = 2;

 select N.Category_Id
 from Users,TABLE(Users.Attempted)N where User_Id = 1;


/************************************************************************************************/

drop sequence Post_Id
/

create sequence Post_Id increment by 1  start with 1
/

 create or replace type CATEGORY_TY as object
 (Category_Id number(10),
  Test_Id number(35))
/

create type CATEGORY_NT as table of CATEGORY_TY;
/

drop table post
/

create table POST
(Post_id number(10)  primary key,
PostName Varchar2(10),
CatEntry CATEGORY_NT,
Aggregate number(10),
Qualification Varchar2(25),
Branch varchar(25),
Experience number(10),
vacancy number(10)
)
nested table CatEntry store as CATEGORY_NT_TAB;


insert into Table(select CatEntry from Post
		  where Post_Id = 1)
                  values(CATEGORY_TY(1,2),CATEGORY_TY(2,1));


select Post_Id,PostName,Aggregate,Qualification,Branch,Experience,vacancy,N.Category_Id,N.Test_Id
from Post,TABLE(Post.CatEntry)N;

select Post_Id,N.Category_Id
from Post,TABLE(Post.CatEntry)N;

select N.User_Answer from TestQuestions,
Table(TestQuestions.TestEntry)N where User_Id = 1 and Test_Id = 2 and N.Ques_Id = 18;


select N.User_Answer from TestQuestions,
Table(TestQuestions.TestEntry)N where User_Id = 1 and Test_Id = 2 and N.Ques_Id = 18;

insert into Post values
(1,'Manager',
CATEGORY_NT(
 CATEGORY_TY(1,2),
 CATEGORY_TY(2,1)
),
50,'BE','Computer',2,4)
/

insert into Post values
(1,'Manager',
CATEGORY_NT(
 CATEGORY_TY(1,2)),
50,'BE','Computer',2,4)
/

insert into Post values
(2,'Tester',
CATEGORY_NT(
 CATEGORY_TY(3,3)
),
40,'MCA','Computer',1,7)
/

insert into Table(select TestEntry from TestQuestions
		  where User_Id = 1 and Test_Id = 1)
                  values(TestQues_TY(2,4,2));


delete Table(select CatEntry from Post where Post_Id = 3)N;

delete Table(select CatEntry from Post where Post_Id = 2)N where N.category_Id =0
/

/***********************************************************************************************/

create or replace type TDATA_TY as object
(Category_Id number(10),
 Test_ID number(10),
 Percent number(10,2),
 Attempt number(10,2),
 Result varchar(5)
)
/

create type TDATA_NT as table of TDATA_TY;
/

drop table Result
/

Create table Result
(User_Id number(35) references Users(User_Id),
 Post_Id number(35) references Post(Post_Id),
 T_Percent number(10,2),
 T_Attempt number(10,2),
 T_Result varchar2(5)  check(T_Result in('Pass','Fail')),
 Attempt_No number(25),
 TestDate date,
 TestData TDATA_NT)
 nested table TestData store as TDATA_NT_TAB
/


insert into Result values
(1,1,45.45,100,'Pass',1,'27-MAY-04',
TDATA_NT(
 TDATA_TY(1,55.45,100,'Pass'),
 TDATA_TY(2,35.45,100,'Fail')
))
/

select User_Id,Post_Id,T_Percent,T_Attempt,T_Result,Attempt_No,TestDate,N.Category_Id,
N.Test_Id,N.Percent,N.Attempt,N.Result
from Result ,TABLE(Result.TestData)N
/
 set linesize 500
/

select N.Test_Id
from Result ,TABLE(Result.TestData)N

select T_Percent,T_Attempt,T_Result,Attempt_No,TestDate,N.Test_Id,N.Percent,N.Attempt,N.Result
 from Result,Table(Result.TestData)N where User_Id = 1 and Post_Id = 1;
/

update Result set T_Percent = 50.0,T_Attempt = 50.0,T_Result = 'Pass'

delete Table(select TestData from Result where User_Id = 1 and
Post_Id = 1)N;

/***********************************************************************************************/

create or replace type TESTQUES_TY as object
(Ques_Number number(10),
Ques_Id number(10),
User_Answer number(10),
Correct_Ans number(10)
)
/

create type TESTQUES_NT as table of TESTQUES_TY
/

drop table TestQuestions
/
create table TestQuestions
(User_Id number(35) references Users(User_Id),
 Post_Id number(35) references POst(Post_Id),
 Test_ID number(10) not null,
 TestEntry TESTQUES_NT)
nested table TestEntry store as TESTQUES_NT_TAB;

select User_Id,Post_Id,Test_Id,N.Ques_Number,N.Ques_Id,N.User_Answer,N.Correct_Ans
from TestQuestions ,TABLE(TestQuestions.TestEntry)N;

select N.User_Answer from TestQuestions,
Table(TestQuestions.TestEntry)N where User_Id = 1 and Test_Id = 2 and N.Ques_Id = 18;

select N.User_Answer,N.Correct_Ans from TestQuestions,
Table(TestQuestions.TestEntry)N where User_Id = 1 and Test_Id = 2 order by N.Ques_Number;

select count(N.Ques_number)
from TestQuestions,Table(TestQuestions.TestEntry)N where User_Id = 1 and Test_Id = 1;

insert into Table(select TestEntry from TestQuestions
		  where User_Id = 1 and Test_Id = 1)
                  values(TestQues_TY(2,4,2));

insert into Table(select TestEntry from TestQuestions
		 where User_Id = UserId and Test_Id = TestId)
                 values(TestQues_TY(Ques_Number,Ques_Id,User_Answer));


Update Table(select TestEntry from TestQuestions)N set
 N.User_Answer=1 where N.Ques_Id=13 and User_Id = 1 and
Test_Id = 1;


Update Table(select TestEntry from TestQuestions where User_Id = 1 and Test_Id = 1)N set
 N.User_Answer=1 where N.Ques_Id=13;

select N.User_Answer from TestQuestions,Table(TestQuestions.TestEntry)N where User_Id =1
and Test_Id = 2 and N.Ques_Id = 16


delete Table(select TestEntry from TestQuestions where User_Id = 1 and
Test_Id = 2)N;


/***************************************************************************************************/

create or replace Function Checked
(UserId Number,Post_Id Number,TestId Number,QuesNo Number,
 Ques_Id Number,Ans Number,Correct Number)
return Number
is
no Number(10);
begin

 select count(rownum)
 into no
 from TestQuestions
 where User_Id = UserId and Test_Id = TestId;

 if no > 0
 then
     insert into Table(select TestEntry from TestQuestions
		 where User_Id = UserId and Test_Id = TestId)
                 values (TestQues_TY(QuesNo,Ques_Id,Ans,Correct));
 else
     insert into TestQuestions values(UserId,Post_Id,TestId,
		         TestQues_NT(TestQues_TY(QuesNo,Ques_Id ,Ans,Correct)));
 end if;

 no := 1;

 return(no);
end;
/



insert into TestQuestions values(1,1,
		         TestQues_NT(TestQues_TY(1,3,2)));

variable no number
/
execute:no := Checked(1,1,1,1,18,1,3)
/
print no;


insert into TestQuestions values(1,1,1,
		         TestQues_NT(TestQues_TY(1,18,1,3)));

     insert into TestQuestions values(1,1,
		         TestQues_NT(TestQues_TY(1,12,1)));


/*************************************************************************************************/

create sequence Test_Id increment by 1
/

create table Test (
Test_Id number(20) primary key,
Name Varchar2(35),
Scrolling number(10) check(Scrolling in(0,1)),
Pass_Score number(10) not null,
Description varchar2(50),
Ques_Type varchar2(10) check(Ques_Type in('MC','TF','MA')),
Time number(10),
Timed number(10) check(Timed in(0,1)),
Test_Type number(5) check(Test_Type in(0,1)),
Total_Ques number(10) not null
)
/

insert into Test values(1,'General Knowledge',
1,35,'Test with 5 Questions all MC type','MC',1,1,1,5)
/
insert into Test values(2,'Information Technology',
1,35,'G K','MC',0,0,1,5)
/
insert into Test values(3,'General Knowledge',
1,35,'G K','MC',0,0,0,5)
/



/*************************************************************************************************/


 delete from TestData where MONTHS_BETWEEN(Sysdate,TestDate) > 6;

 select MONTHS_BETWEEN(Sysdate,TestDate) from TestData where PERCENT = 67;


/*---------------------------------------------------------------------------------*/

create or replace function Random
(C_Id IN NUMBER,Typ IN VARCHAR2,No IN NUMBER)
return NUMBER
is
Quesn_Id NUMBER(10);

begin
 if Typ = 'None'
 then
   select count(rownum)
   into Quesn_Id
   from questions
   where Category_Id = C_Id;
 else
   select count(rownum)
   into Quesn_Id
   from questions
   where Category_Id = C_Id and Type = Typ;
 end if;

 if no > Quesn_Id
 then
   Quesn_Id := mod(no,Quesn_Id);
 else
   Quesn_Id := no;
 end if;

 if Quesn_Id > 0
 then
   if Typ = 'None'
   then
      select max(Ques_Id)
      into Quesn_Id
      from Questions
      where Category_Id = C_Id and
      rownum < Quesn_Id + 1
      order by Ques_Id;
   else
      select max(Ques_Id)
      into Quesn_Id
      from Questions
      where Category_Id = C_Id  and Type = Typ and
      rownum < Quesn_Id + 1
      order by Ques_Id;
   end if;
 end if;

 RETURN(Quesn_Id);
end;
/

variable no number
/
execute:no := Random(1,'None',24)
/
print no;


/*****************************************************************************************/

create or replace Function ResultEntry
(UserId Number,PostId Number,TestId Number,TestDate Date)
return Number
is
no Number(10);
begin

 select count(rownum)
 into no
 from Result
 where User_Id = UserId and Post_Id = PostId;

 if no > 0
 then
     insert into Table(select TestEntry from TestQuestions
		 where User_Id = UserId and Test_Id = TestId)
                 values (TestQues_TY(QuesNo,Ques_Id,Ans,Correct));
 else
     insert into Result values(UserId,Post_Id,TestId,
		         TestQues_NT(TestQues_TY(QuesNo,Ques_Id ,Ans,Correct)));
 end if;

 return(no);
end;
/

/*****************************************************************************************/

insert into TestQuestions values(1,1,1,
      TestQues_NT(TestQues_TY(,Ques_Id ,Ans,Correct)));

/*****************************************************************************************/
