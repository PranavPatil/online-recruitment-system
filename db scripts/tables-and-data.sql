drop sequence MC_Ans_Id
/
drop sequence TF_Ans_Id
/
drop sequence MA_Ans_Id
/
drop sequence Ques_Id
/
drop sequence Category_Id
/
drop sequence User_Id
/

drop table TestQuestions
/
drop table MC_Answers
/
drop table TF_Answers
/
drop table MA_Answers
/
drop table Questions
/
drop table Category
/
drop table Users
/
	
create sequence Category_Id increment by 1 
/

create sequence Ques_Id increment by 1 
/

create sequence MC_Ans_Id increment by 1 
/

create sequence TF_Ans_Id increment by 1 
/

create sequence MA_Ans_Id increment by 1 
/

create sequence User_Id increment by 1 
/


create table Category 
(Category_Id number(10) primary key,
 Name varchar2(45),
 Description varchar2(100))
/

create table Questions 
(Ques_Id number(10) primary key,
 Question Varchar2(80),
 Category_ID number(10) references Category(Category_Id),
 Type Varchar2(10) not null
 check(Type in('MC','TF','MA'))
)
/

create table MC_Answers 
(MC_Ans_Id number(10) primary key,
 Ques_Id number(10) references Questions(Ques_Id),
 Correct_Ans number(5)
 Check(Correct_Ans in(1,2,3,4)),        /*  1,2,3,4 For Options  */
 Ans1 varchar2(20),
 Ans2 varchar2(20),
 Ans3 varchar2(20),
 Ans4 varchar2(20)
)
/

create table TF_Answers 
(TF_Ans_Id number(10) primary key,
 Ques_Id number(10) references Questions(Ques_Id),
 Correct_Ans number(5)
 Check(Correct_Ans in(1,2))        /*  1 For True and 1 For False  */
)
/

create table MA_Answers 
(MA_Ans_Id number(10) primary key,
 Ques_Id number(10) references Questions(Ques_Id),
 Correct_Ans number(10),
 Ans1 varchar2(20),
 Ans2 varchar2(20),
 Ans3 varchar2(20),
 Ans4 varchar2(20)
)
/

insert into Category values(1,'General Knowledge',
			    'Science with geography and history')
/
insert into Category values(2,'Information Technology',
			    'Topics are OS,DBses,Security and Hardware')
/
insert into Category values(3,'Mathematics',
			    'Inludes Calculus and trigonometry')
/
insert into Category values(4,'Science',
			    'Test on Physics Chemistry and Biology')
/

insert into Category values(5,'Management',
			    'Test on Economics and Management')
/

insert into Category values(6,'Digital Electronics',
			    'Test on Ditital systems and circuits')
/

insert into Questions values 
( Ques_Id.nextval,'Where is Effiel Tower Situated ?',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,1,3,
'London','New York','Paris','Rome')
/

insert into Questions values 
(Ques_Id.nextval,'Who is the President of USA ?',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,2,4,
'Bill Clinton','John Kenndy','Collen Powell','George Bush')
/

insert into Questions values 
(Ques_Id.nextval,'Sachin Tendulkar is the Top Scorer in ODIs',1,'TF')
/

insert into TF_Answers values 
(TF_Ans_Id.nextval,3,2
)
/

insert into Questions values 
(Ques_Id.nextval,'Who was the mother of Lord Rama',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,4,1,
'Kausalya','Kaikai','Mandodari','none')
/

insert into Questions values 
(Ques_Id.nextval,'Who invented Pencillin Drug',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,5,4,
'Alexender Bell','Ronald Ross','John Alexender','Alexender Fleming')
/

insert into Questions values 
(Ques_Id.nextval,'Zakir Hussain was President of India',1,'TF')
/

insert into TF_Answers values 
(TF_Ans_Id.nextval,6,1
)
/

insert into Questions values 
(Ques_Id.nextval,'RSA Algorithm is used for',2,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,7,1,
'Cyrptography','Encryption','Hacking','none')
/

insert into Questions values 
(Ques_Id.nextval,'Amitabh won the Phalke award in 1994',1,'TF')
/

insert into TF_Answers values 
(TF_Ans_Id.nextval,8,2
)
/

insert into Questions values 
(Ques_Id.nextval,'Laloo Prasad was CM of Bihar for',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,9,2,
'5 yrs','10 yrs','12 yrs','15 yrs')
/

insert into Questions values 
(Ques_Id.nextval,'Brain Lara made 400* in test against',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,10,2,
'Kenya','Zimbabwe','Bangladesh','England')
/

insert into Questions values 
(Ques_Id.nextval,'How many modes does Vi Editor works in unix',2,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,11,2,
'2 Modes','3 Modes','4 Modes','5 Modes')
/

insert into Questions values 
(Ques_Id.nextval,'Where was Gautam Buddha was born ?',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,12,3,
'India','Bhutan','Nepal','Bangladesh')
/

insert into Questions values 
(Ques_Id.nextval,'The currency of bangladesh is ?',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,13,1,
'Taka','Pula','Rupee','Rial')
/

insert into Questions values 
(Ques_Id.nextval,'Hard drives consists of spinning platters ?',2,'TF')
/

insert into TF_Answers values 
(TF_Ans_Id.nextval,14,1
)
/

insert into Questions values 
(Ques_Id.nextval,'Metrology is the scientific study of Wt measures ?',1,'TF')
/

insert into TF_Answers values 
(TF_Ans_Id.nextval,15,1
)
/

insert into Questions values 
(Ques_Id.nextval,'Which of these states highest % of literacy ?',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,16,4,
'Maharashtra','Tamil Nadu','Karnataka','Kerala')
/

insert into Questions values 
(Ques_Id.nextval,'The growth of Coffee in India is largest kerala?',1,'TF')
/

insert into TF_Answers values 
(TF_Ans_Id.nextval,17,2
)
/

insert into Questions values 
(Ques_Id.nextval,'The french revolution took place in?',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,18,3,
'1787','1788','1789','1790')
/

insert into Questions values 
(Ques_Id.nextval,'Machine Gun was invented by ?',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,19,2,
'Rice Kellogg','Richard  Gatling','J E Lundstrom','William Mudrock')
/

insert into Questions values 
(Ques_Id.nextval,'Where is Bauxite found in large quantity ?',1,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,20,3,
'U.S.A','Germany','Australia','Japan')
/

insert into Questions values 
(Ques_Id.nextval,'Which virus mutates with every infection ?',2,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,21,2,
'Stealth virus','Polymorphic virus','Parasitic virus','Macro virus')
/

insert into Questions values 
(Ques_Id.nextval,'Which of these is Eqn for RSA Algorithm ?',2,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,22,1,
'M = (C power d)mod n','M power n = m mod n','ed = k fn(n) - 1','none')
/

insert into Questions values 
(Ques_Id.nextval,'Mars completes one circuit of sun in 567 days ?',1,'TF')
/

insert into TF_Answers values 
(TF_Ans_Id.nextval,23,2
)
/

insert into Questions values 
(Ques_Id.nextval,'The Largest Oscar nominations is 11',1,'TF')
/

insert into TF_Answers values 
(TF_Ans_Id.nextval,24,2
)
/

insert into Questions values 
(Ques_Id.nextval,'Lotus is trademark of',2,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,41,2,
'Microsoft','IBM','Sun','none')
/

insert into Questions values 
(Ques_Id.nextval,'Which company first produced floppy ?',2,'MC')
/

insert into MC_Answers values 
(MC_Ans_Id.nextval,42,3,
'IBM','HP','Sony','none')
/



/***********************************************************************************************/

insert into Questions values 
(Ques_Id.nextval,'Which are capitals of foll ?',1,'MA')
/

insert into MA_Answers values 
(MC_Ans_Id.nextval,25,1204,
'London','Berlin','New York','Tokyo')
/

insert into Questions values 
(Ques_Id.nextval,'Where was Ompl where held ?',1,'MA')
/

insert into MA_Answers values 
(MC_Ans_Id.nextval,26,1230,
'U.S.A','Germany','Australia','Japan')
/

insert into Questions values 
(Ques_Id.nextval,'Which are domestic animals ?',1,'MA')
/

insert into MA_Answers values 
(MC_Ans_Id.nextval,27,1004,
'Dog','Tiger','Bear','Goat')
/

insert into Questions values 
(Ques_Id.nextval,'Which is not a Language ?',1,'MA')
/

insert into MA_Answers values 
(MC_Ans_Id.nextval,28,1200,
'C','Perl','Oracle','Linux')
/

insert into Questions values 
(Ques_Id.nextval,'When Indo-Pak war took place ?',1,'MA')
/

insert into MA_Answers values 
(MC_Ans_Id.nextval,29,0230,
'1942','1969','1975','1990')
/

insert into Questions values 
(Ques_Id.nextval,'Where is Bauxite found ?',1,'MA')
/

insert into MA_Answers values 
(MC_Ans_Id.nextval,30,1030,
'U.S.A','Pakistan','Australia','Africa')
/

insert into Questions values 
(Ques_Id.nextval,'Which are Islam nations ?',1,'MA')
/

insert into MA_Answers values 
(MC_Ans_Id.nextval,31,1230,
'Pakistan','Bangladesh','UAE','Turkey')
/

insert into Questions values 
(Ques_Id.nextval,'Where is Gold found ?',1,'MA')
/

insert into MA_Answers values 
(MC_Ans_Id.nextval,32,0004,
'U.S.A','Germany','Australia','Africa')
/








