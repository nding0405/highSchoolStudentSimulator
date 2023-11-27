# My Personal Project

## Project Description
#### 1. What will the application do?
- The application is a game called **student simulator**. 
Gamers are expected to add activities(can be either courses or games) for a high school
student. Every course and game has 3 basic index: **time, pressure index
and knowledge index**. Once the total time in the schedule exceeds a specific value 
as the user keeps adding courses and games, the student will go to different universities
based on the total knowledge and pressure he/she accumulated. Or the student will drop
the school if the pressure index in total exceed an assigned value. 


#### 2. Who will use it?
- Targeted users are anyone who would like to try this **simple multi-ending game**.

#### 3.Why is this project of interest to me?
- A multi-ending game is of interest to me and it will be fun to play with it.

## User Story
- As a user, I want to be able to add a course or game to the schedule.
- As a user, I want to be able to view the courses and games I added 
  to the schedule.
- As a user, I want to be able to view the total time I already spent on 
  the courses and games in a time bar.
- As a user, I want to be able to view the total pressure accumulated in a pressure bar.
- As a user, I want to be able to view the total knowledge accumulated in a knowledge bar.
- As a user, I want to be able to view the time, pressure index and knowledge 
  index of a course or activity.
- As a user, I want to be able to observe that the image of student are changed as I add 
  courses or games to the schedule, or as the game comes to the end.
- As a user, I want to be able to save my all pieces of current student status when I choose 
- to save.
- As a user, I want to be able to be able to reload my all pieces of current student status I choose
- to reload.

## Phase 4: Task 2
Program is shutting down. Printing all events:\
Sun Nov 26 15:05:04 PST 2023 Load Json data\
Sun Nov 26 15:05:04 PST 2023 Create a student named 893749237\
Sun Nov 26 15:05:13 PST 2023 Add History for 50 hrs\
Sun Nov 26 15:05:19 PST 2023 Saved to Json

## Phase 4: Task 3
1. Create an abstract class in ui called BuildWindow which has fields of type of:\
   protected Student student;\
   protected JFrame myFrame;\
   protected String gender;\
   protected JLayeredPane dialogueContainer;\
   protected JLabel dialogueImageLabel;\
   protected JLabel dialogueTextLabel;\
   protected JLabel studentImageLabel;\
And methods that setup all the layouts. Then, let InitializeStudentWindow, 
PressureExceedEnd, FineArtEnd, TimeExceedEnd extend BuildWindow class. And call
the methods in BuildWindow in constructor to build the general window. **It can avoid 
a lot of duplication by doing so.**
2. Apply observer pattern to MainGamingWindow. Create two new class: one is 
updating the bars, the other is making the student image changing. Let
these two new classes extends Observer while MainGamingWindow extends Subject.
Call addObserver(Observer o) in the constructor of MainGamingWindow to add the 
new classes as observers. When the user adds an activity, call notify() in MainGamingWindow.
**By applying the observer pattern, it will be easier to add more behaviour related to addActivities().
And also, it will promote single responsibility of the program.**