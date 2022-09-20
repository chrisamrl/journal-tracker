# 2022 Yearly Journal Tracker


The application is similar to a diary app.
It will allow users to add any amount of notes on a
specific date. The users can also set their *mood* to summarize what
they're feeling on a specific day. Note that this app
is a yearly journal tracker app, meaning that it will
only allow users to use it on a specific year
(similar to a *yearly calendar*).


Anyone who is in need of a journal tracker app
can use this app since the additional feature might
be of interest to some people. My motivation for choosing this app is because I've
been looking for an app like this, with no such luck.
By building it by myself, I hope that I could add features specific
to my needs.

### User Stories

- As a user, I want to be able to add a note on
    a specific date.
- As a user, I want to be able to set my *mood* on a specific date.
- As a user, I want to be able to view my journal (which consists of my list of *notes*
    and my *mood*) on a specific day.
- As a user, I want to be able to see a specific note.
- As a user, I want to be able to delete a specific note.
- As a user, I want to be able to save my Journal to file.
- As a user, I want to be able to load my Journal from file.



# Instructions for Grader

To run the application's GUI, run *main()* in <u>JournalAppGUI</u> class 
(*src/main/ui/gui/JournalAppGUI*)

- You can generate the first required event (Adding notes 
  to a page) by: <br>
*Pick a date &rarr; Click submit &rarr; Click Add &rarr; Type in a note
  &rarr; Click OK*


- You can generate the second required event (Deleting a 
  specific note on page) by: <br>
  <u>Assuming there's no note yet</u> <br>
  *Pick a date &rarr; Click submit &rarr; Click Add &rarr; Type in a note
  &rarr; Click OK &rarr; Click Delete (on the note that's just been added)*


- You can locate my visual component by: <br>
  *Run the application (There should be an image on the Panel shown)* 
  **or** *Pick a date &rarr; Click submit (There are icons on the buttons)*


- You can save the state of my application by: <br>
  *Click **File** on the Menu Bar on TOP of the frame &rarr; Click Save &rarr; 
  Click OK*
  

- You can reload the state of my application by: <br>
  *Click **File** on the Menu Bar on TOP of the frame &rarr; Click Load &rarr;
  Click OK*

The other user stories (viewing journal and its contents on
a specific page, setting a mood) are also implemented in the GUI,
the steps are fairly intuitive / similar to the steps written above.


# Phase 4

### Task 2


Sample events / output:

1. Adding 3 Notes on 2 pages <br>

    Run Application &rarr; Choose 1 January &rarr; Add Note &rarr; Ok
   &rarr; Add Note &rarr; OK &rarr; Click Home Button &rarr; Choose 2 February &rarr; Add Note
   &rarr; Ok &rarr; Quit application

> Wed Aug 10 10:11:55 PDT 2022
Added a note to page on 1-1-2022
>
> Wed Aug 10 10:12:04 PDT 2022
Added a note to page on 1-1-2022
> 
> Wed Aug 10 10:12:15 PDT 2022
Added a note to page on 2-2-2022


2. Adding 3 notes then deleting one on the same page <br>

   Run Application &rarr; Choose 1 January &rarr; Add Note &rarr; Ok
   &rarr; Add Note &rarr; OK &rarr; Add Note &rarr; Ok &rarr; 
Click Delete Button on last note &rarr; Ok &rarr; Quit application

> Wed Aug 10 10:14:08 PDT 2022
Added a note to page on 1-1-2022
> 
> Wed Aug 10 10:14:16 PDT 2022
Added a note to page on 1-1-2022
> 
> Wed Aug 10 10:14:27 PDT 2022
Added a note to page on 1-1-2022
> 
> Wed Aug 10 10:14:39 PDT 2022
Removed note on page 1-1-2022 with index 2


### Task 3

#### Reflection

The UML file is in the root folder of this project 
UML_Design_Diagram.pdf

After looking at my own UML Diagram, I realized there
were some things that are redundant and complicate the
relationships between the classes. If I had more time,
these are some changes that I would make:

- On the GUI package, HomePanel and PagePanel 
have associations to Journal. The reason for this was
that I wanted HomePanel and PagePanel to get a reference
to the Journal in MainFrame. If I had to refactor this,
I would just somehow pass the Journal as a parameter when
calling the method in HomePanel and PagePanel, instead of making it
a field. Because HomePanel and PagePanel actually "live" inside
MainFrame's content pane, so I think it's pretty redundant to store
the same Journal as a field.


- Again, on the GUI package, HomePanel and PagePanel has too much
responsibility. For example, in HomePanel, I had a date picker that
I think could be separated into its own class. While on PagePanel,
I had a Header and a list of notes, which I think could also be
seperated.


- I also would introduce the Singleton design pattern if I had more time
in the GUI package. In all of my Panel & Frames on the GUI package,
I kept a reference to a single CardLayout and making sure they all have
the same CardLayout. But I think it's better to use the Singleton Pattern
because I only initiated 1 CardLayout in all the Panels & Frame.


- Now, looking on the model package, I realized I could remove / replace the MonthlyPages
class completely. I initially added that class because I wanted the ArrayList 
in Journal to not have too many Pages inside it. But I think I could
probably make a new class that has 365 pages directly called
"yearlyPages" or similar to that and separate completely the
handling of getting a "Page" on the journal.


  
So, these are all the changes I would make if I had more time.



