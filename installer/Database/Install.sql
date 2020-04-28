create sequence Access_Id increment by 1 
/
create table Accessibility 
(Access_Id number(1) Primary key,
 Designation varchar2(25) Unique,
 AccessCode number(10) not null
)
/
create sequence Admin_Id increment by 1 
/
create table Admin 
(Admin_Id number(3) Primary key,
 Fname varchar2(20) not null,
 Lname varchar2(20) not null,
 Login varchar2(20) not null Unique,
 Password varchar2(15) not null,
 Telephone number(12) not null,
 Email varchar2(30) not null,
 Access_Id number(1) references Accessibility(Access_Id)
)
/
create sequence User_Id increment by 1 
/
create table Users 
(User_Id number(35) Primary key,
 FName varchar2(20) not null,
 MName varchar2(20) not null,
 LName varchar2(20) not null,
 Login varchar2(20) not null Unique,
 Password varchar2(15) not null,
 Gender varchar2(1) check(Gender in('M','F')),            /* M for Male, F for Female */ 
 Telephone number(12) not null,
 Address varchar(500) not null,
 Email varchar2(30) not null,
 Date_of_Birth date not null,
 Qualification varchar2(20) not null,
 Branch varchar2(45) not null,
 Experience number(2) not null,
 Selected number(5) not null,
 SelectNo number(3) not null,
 Employee number(5) not null)
/
create sequence Category_Id increment by 1 
/
create table Category 
(Category_Id number(5) Primary key,
 Name varchar2(45) not null Unique,
 Description varchar2(500))
/
create sequence Ques_Id increment by 1 
/
create table Questions 
(Ques_Id number(15) Primary key,
 Question Varchar2(200) not null,
 Category_ID number(5) references Category(Category_Id),
 Type Varchar2(2) check(Type in('MC','TF','MA'))
)
/
create sequence MC_Ans_Id increment by 1 
/
create table MC_Answers 
(MC_Ans_Id number(14) Primary key,
 Ques_Id number(15) references Questions(Ques_Id),
 Correct_Ans number(1) Check(Correct_Ans in(1,2,3,4)),        /*  1,2,3,4 For Options  */
 Ans1 varchar2(30) not null,
 Ans2 varchar2(30) not null,
 Ans3 varchar2(30) not null,
 Ans4 varchar2(30) not null
)
/
create sequence TF_Ans_Id increment by 1 
/
create table TF_Answers 
(TF_Ans_Id number(14) Primary key,
 Ques_Id number(15) references Questions(Ques_Id),
 Correct_Ans number(1) Check(Correct_Ans in(1,2))        /*  1 For True and 1 For False  */
)
/
create sequence MA_Ans_Id increment by 1 
/
create table MA_Answers 
(MA_Ans_Id number(14) Primary key,
 Ques_Id number(15) references Questions(Ques_Id),
 Correct_Ans number(4) not null,
 Ans1 varchar2(30) not null,
 Ans2 varchar2(30) not null,
 Ans3 varchar2(30) not null,
 Ans4 varchar2(30) not null
)
/
create sequence Test_Id increment by 1 
/
create table Test 
(Test_Id number(3) primary key,
 Name Varchar2(45) not null Unique,
 Scrolling number(1) check(Scrolling in(0,1)),
 Pass_Score number(2) not null,
 Description varchar2(500),
 Ques_Type varchar2(2) check(Ques_Type in('MC','TF','MA')),
 Time number(3) not null,
 Hide number(1) check(Hide in(0,1)),
 Test_Type number(1) check(Test_Type in(0,1)),
 Total_Ques number(3) not null,
 Negative number(1) check(Negative in(0,1))
)
/
create sequence Post_Id increment by 1  start with 1
/
create or replace type CATEGORY_TY as object 
(Category_Id number(5),
 Test_Id number(3))
/
create type CATEGORY_NT as table of CATEGORY_TY
/
create table POST 
(Post_id number(5)  primary key,
 PostName Varchar2(45) not null Unique,
 Description Varchar2(500),
 CatEntry CATEGORY_NT,
 Aggregate number(2) not null,
 Qualification Varchar2(20) not null,
 Branch varchar(30) not null,
 Experience number(2) not null,
 Agelimit number(2) not null,
 vacancy number(6) not null)
 nested table CatEntry store as CATEGORY_NT_TAB
/
create or replace type TESTQUES_TY as object 
(Ques_Number number(3),
 Ques_Id number(15),
 User_Answer number(4),
 Correct_Ans number(4))
/
create type TESTQUES_NT as table of TESTQUES_TY
/
create table TestQuestions
(User_Id number(35) references Users(User_Id),
 Post_Id number(5) references Post(Post_Id),
 Category_Id number(5) references Category(Category_Id),
 TestEntry TESTQUES_NT,
 Constraint TestQuestions_Pk Primary key (User_Id,Post_Id,Category_Id))
 nested table TestEntry store as TESTQUES_NT_TAB
/
create or replace type TDATA_TY as object 
(Category_Id number(5),
 Test_ID number(3),
 Percent number(6,2),
 Attempt number(5,2),
 Result varchar(4))
/
create type TDATA_NT as table of TDATA_TY
/
Create table Result
(User_Id number(35) references Users(User_Id),
 Post_Id number(5) references Post(Post_Id),
 T_Percent number(6,2) not null,
 T_Attempt number(5,2) not null,
 T_Result varchar2(4)  check(T_Result in('Pass','Fail')),
 Attempt_No number(3) not null,
 TestDate date not null,
 TestData TDATA_NT,
 Constraint Result_Pk Primary key (User_Id,Post_Id))
 nested table TestData store as TDATA_NT_TAB
/
create index Question_Context_Index on Questions(Question) 
indextype is ctxsys.context
/
create or replace function Random 
(C_Id IN NUMBER,Typ IN VARCHAR2,No IN NUMBER) 
return NUMBER 
is 
Quesn_Id NUMBER(15); 
 
begin 
 if Typ = 'None' 
 then 
   select count(rownum) 
   into Quesn_Id 
   from Questions 
   where Category_Id = C_Id; 
 else 
   select count(rownum) 
   into Quesn_Id 
   from Questions 
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
create or replace Trigger Delete_Question 
before delete on Questions 
for each row 
declare 
  num INTEGER(4); 
begin 
 
 Select count(rownum) into num from TestQuestions; 

 if num > 0 then 
 Delete Table(Select TestEntry from TestQuestions)N where N.Ques_Id = :old.Ques_Id; 
 end if; 

 if :old.Type = 'MC' 
 then 
   Delete from MC_Answers 
   where Ques_Id = :old.Ques_Id; 
 elsif :old.Type = 'TF' 
 then 
   Delete from TF_Answers 
   where Ques_Id = :old.Ques_Id; 
 elsif :old.Type = 'MA' 
 then 
   Delete from MA_Answers 
   where Ques_Id = :old.Ques_Id; 
 end if; 

end; 
/
create or replace Trigger Edit_Question 
before Update on Questions 
for each row 
declare 
  num INTEGER(4); 
begin 

 Select count(rownum) into num from TestQuestions; 

 if num > 0 then 
 Delete Table(Select TestEntry from TestQuestions)N where N.Ques_Id = :old.Ques_Id; 
 end if; 
end; 
/
create or replace Trigger Delete_Category 
before delete on Category 
for each row 
begin 
 Delete from TestQuestions where Category_Id = :old.Category_Id; 
 Delete from Questions where Category_Id = :old.Category_Id; 
end; 
/
create or replace Trigger Recruit 
after Update of Employee on Users 
for each row 
begin 
 if :new.Employee > 0 
 then 
  Update Post set Vacancy = Vacancy - 1 where Post_Id = :new.Employee and Vacancy != 0; 
 elsif :new.Employee = 0 
 then 
  Update Post set Vacancy = Vacancy + 1 where Post_Id = :old.Employee; 
 end if; 
end; 
/
commit
/
