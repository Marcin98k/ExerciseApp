ExerciseApp



Application structure {

    Main Activity {
        Fragments:
            Sign-in;
            Sign-up;
            News $list $calendar;
            $workouts
    }
    Library Activity {
        Fragments:
            $workouts;
            $search;
            $exercise;
    }
    Exercise Activity {
        Fragments:
            $exercise;
            $clock;
    }
    User Activity {
        Fragments:
            $workouts $calendar;
            Completed $workouts;
    }
    Settings Activity {
        Fragments:
            Settings $list;
    }

    Fragments {
        $calendar -> Calendar;
        $workouts -> Workouts;
        $exercise -> Exercise;
        $search -> Serach;
        $list -> List;
        $clock -> Clock;
    }
}