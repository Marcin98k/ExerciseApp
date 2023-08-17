# ExerciseApp
I have been exercising regularly at home for several years, which is why I started using the training application, whose task was to structure and control my progress. Over time, I noticed that each application has new, specific functionalities and the nature of training. With the increased intensity, I used not one but several at the same time, because in each there was something that was not in the previous one, but also what pushed the training progress forward. However, control over all of them has become quite problematic, so I decided to create software that is modeled on the applications I encountered, but at the same time will get the best out of them, it will be clear, intuitive and all in one mobile application - "ExerciseApp".

## Tech stack:
- Java 8,
- Android SDK 26,
- SQLite 3.18.2 (for Adroid API 26)

## Structure
The application consists of 6 activities: 5 main and one additional, responsible for registrations.
The main assumption of programming was to reuse the code, which is why the application used: fragments. Most major tasks such as database references, or changes to the user interface, etc...

### (Name, status)
#### WelecomeActivity.java, (basic functions) ->
Activity responsible for logging in and user registrations.

#### MainActivity.java, (not ready) -> 
The activity responsible for displaying new messages from the Firebase database.

#### LibraryActivity.java, (basic functions) -> 
Activity to display the content of the database: such as exercises, workouts, training plans and details about them.

#### UserActivity.java (not ready) -> 
Activity responsible for showing the user's progress.

#### SettingsActivity.java (basic functions) -> 
The activity responsible for modifying the account and application.

#### ExercsieActivity.java (basic functions) ->
The activity responsible for guiding the user through the exercise/training.



