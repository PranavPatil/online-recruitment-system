insert into Accessibility values ( Access_Id.nextval,'SuperAdministrator',2147483647)
/
insert into Accessibility values ( Access_Id.nextval,'Administrator',2147417984)
/
insert into Accessibility values ( Access_Id.nextval,'Manager',2147417984)
/
insert into Accessibility values ( Access_Id.nextval,'Superviser',2147417984)
/
insert into Accessibility values ( Access_Id.nextval,'TestIncharge',2147417984)
/
insert into Accessibility values ( Access_Id.nextval,'Examiner',2147417984)
/
insert into Admin values ( Admin_Id.nextval,'Firstname','Lastname',
			  'SuperAdmin','change_passwd',
                           12345678900,'Admin@Website.com',1)
/
insert into Users values ( User_Id.nextval,
			  'Pranav','Sachin','Patil','Pranav',
                          'sample','M',02025678981,
                          'Pune, India',
			  'pranav@yahoo.com','23-DEC-1975','BE','Production',3,0,0,0)
/
insert into Category values(Category_Id.nextval,'General Knowledge',
			    'Science with geography and history')
/
insert into Category values(Category_Id.nextval,'Information Technology',
			    'Topics are OS,DBses,Security and Hardware')
/
insert into Category values(Category_Id.nextval,'Mathematics',
			    'Inludes Calculus and trigonometry')
/
insert into Category values(Category_Id.nextval,'Science',
			    'Test on Physics Chemistry and Biology')
/

insert into Category values(Category_Id.nextval,'Management',
			    'Test on Economics and Management')
/

insert into Category values(Category_Id.nextval,'Digital Electronics',
			    'Test on Ditital systems and circuits')
/

insert into Questions values
( Ques_Id.nextval,'Where is Effiel Tower Situated ?',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,3,
'London','New York','Paris','Rome')
/

insert into Questions values
(Ques_Id.nextval,'Who is the President of USA ?',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,4,
'Bill Clinton','John Kenndy','Collen Powell','George Bush')
/

insert into Questions values
(Ques_Id.nextval,'Sachin Tendulkar is the Top Scorer in ODIs',1,'TF')
/

insert into TF_Answers values
(TF_Ans_Id.nextval,Ques_Id.Currval,2
)
/

insert into Questions values
(Ques_Id.nextval,'Who was the mother of Lord Rama',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,1,
'Kausalya','Kaikai','Mandodari','none')
/

insert into Questions values
(Ques_Id.nextval,'Who invented Pencillin Drug',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,4,
'Alexender Bell','Ronald Ross','John Alexender','Alexender Fleming')
/

insert into Questions values
(Ques_Id.nextval,'Zakir Hussain was President of India',1,'TF')
/

insert into TF_Answers values
(TF_Ans_Id.nextval,Ques_Id.Currval,1
)
/

insert into Questions values
(Ques_Id.nextval,'RSA Algorithm is used for',2,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,1,
'Cyrptography','Encryption','Hacking','none')
/

insert into Questions values
(Ques_Id.nextval,'Amitabh won the Phalke award in 1994',1,'TF')
/

insert into TF_Answers values
(TF_Ans_Id.nextval,Ques_Id.Currval,2
)
/

insert into Questions values
(Ques_Id.nextval,'Laloo Prasad was CM of Bihar for',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,2,
'5 yrs','10 yrs','12 yrs','15 yrs')
/

insert into Questions values
(Ques_Id.nextval,'Brain Lara made 400* in test against',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,2,
'Kenya','Zimbabwe','Bangladesh','England')
/

insert into Questions values
(Ques_Id.nextval,'How many modes does Vi Editor works in unix',2,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,2,
'2 Modes','3 Modes','4 Modes','5 Modes')
/

insert into Questions values
(Ques_Id.nextval,'Where was Gautam Buddha was born ?',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,3,
'India','Bhutan','Nepal','Bangladesh')
/

insert into Questions values
(Ques_Id.nextval,'The currency of bangladesh is ?',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,1,
'Taka','Pula','Rupee','Rial')
/

insert into Questions values
(Ques_Id.nextval,'Hard drives consists of spinning platters ?',2,'TF')
/

insert into TF_Answers values
(TF_Ans_Id.nextval,Ques_Id.Currval,1
)
/

insert into Questions values
(Ques_Id.nextval,'Metrology is the scientific study of Wt measures ?',1,'TF')
/

insert into TF_Answers values
(TF_Ans_Id.nextval,Ques_Id.Currval,1
)
/

insert into Questions values
(Ques_Id.nextval,'Which of these states highest % of literacy ?',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,4,
'Maharashtra','Tamil Nadu','Karnataka','Kerala')
/

insert into Questions values
(Ques_Id.nextval,'The growth of Coffee in India is largest kerala?',1,'TF')
/

insert into TF_Answers values
(TF_Ans_Id.nextval,Ques_Id.Currval,2
)
/

insert into Questions values
(Ques_Id.nextval,'The french revolution took place in?',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,3,
'1787','1788','1789','1790')
/

insert into Questions values
(Ques_Id.nextval,'Machine Gun was invented by ?',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,2,
'Rice Kellogg','Richard  Gatling','J E Lundstrom','William Mudrock')
/

insert into Questions values
(Ques_Id.nextval,'Where is Bauxite found in large quantity ?',1,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,3,
'U.S.A','Germany','Australia','Japan')
/

insert into Questions values
(Ques_Id.nextval,'Which virus mutates with every infection ?',2,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,2,
'Stealth virus','Polymorphic virus','Parasitic virus','Macro virus')
/

insert into Questions values
(Ques_Id.nextval,'Which of these is Eqn for RSA Algorithm ?',2,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,1,
'M = (C power d)mod n','M power n = m mod n','ed = k fn(n) - 1','none')
/

insert into Questions values
(Ques_Id.nextval,'Mars completes one circuit of sun in 567 days ?',1,'TF')
/

insert into TF_Answers values
(TF_Ans_Id.nextval,Ques_Id.Currval,2
)
/

insert into Questions values
(Ques_Id.nextval,'The Largest Oscar nominations is 11',1,'TF')
/

insert into TF_Answers values
(TF_Ans_Id.nextval,Ques_Id.Currval,2
)
/

insert into Questions values
(Ques_Id.nextval,'Lotus is trademark of',2,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,2,
'Microsoft','IBM','Sun','none')
/

insert into Questions values
(Ques_Id.nextval,'Which company first produced floppy ?',2,'MC')
/

insert into MC_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,3,
'IBM','HP','Sony','none')
/

insert into Questions values
(Ques_Id.nextval,'Which are capitals of foll ?',1,'MA')
/

insert into MA_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,1204,
'London','Berlin','New York','Tokyo')
/

insert into Questions values
(Ques_Id.nextval,'Where was Ompl where held ?',1,'MA')
/

insert into MA_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,1230,
'U.S.A','Germany','Australia','Japan')
/

insert into Questions values
(Ques_Id.nextval,'Which are domestic animals ?',1,'MA')
/

insert into MA_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,1004,
'Dog','Tiger','Bear','Goat')
/

insert into Questions values
(Ques_Id.nextval,'When Indo-Pak war took place ?',1,'MA')
/

insert into MA_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,0230,
'1942','1969','1975','1990')
/

insert into Questions values
(Ques_Id.nextval,'Where is Bauxite found ?',1,'MA')
/

insert into MA_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,1030,
'U.S.A','Pakistan','Australia','Africa')
/

insert into Questions values
(Ques_Id.nextval,'Which are Islam nations ?',1,'MA')
/

insert into MA_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,1230,
'Pakistan','Bangladesh','UAE','Turkey')
/

insert into Questions values
(Ques_Id.nextval,'Where is Gold found ?',1,'MA')
/

insert into MA_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,0004,
'U.S.A','Germany','Australia','Africa')
/

insert into Questions values
(Ques_Id.nextval,'Which is not a Language ?',2,'MA')
/

insert into MA_Answers values
(MC_Ans_Id.nextval,Ques_Id.Currval,1200,
'C','Perl','Oracle','Linux')
/
insert into Test values(Test_Id.nextval,'Short Time',
1,35,'Test with 5 Questions all MC type.','MC',1,0,1,5,0)
/
insert into Test values(Test_Id.nextval,'Unlimited Period',
0,60,'All Questions are Random.','',180,1,0,5,1)
/
insert into Test values(Test_Id.nextval,'Level - 4',
1,45,'All Questions are Multi option type.','MA',1,0,1,5,0)
/
insert into Post values
(Post_Id.nextval,'Manager','Managing the departmental work with the responsibility of
 handling the staff.',
CATEGORY_NT(
 CATEGORY_TY(1,1),
 CATEGORY_TY(2,1)
),
50,'BE','Computer',2,40,4)
/
insert into Post values
(Post_Id.nextval,'Tester','Tester involves a full time job of testing the developed code
 and removing all the errors satisfying the requirements.',
CATEGORY_NT(
 CATEGORY_TY(2,2)
),
40,'MCA','Computer',1,22,7)
/
commit
/
