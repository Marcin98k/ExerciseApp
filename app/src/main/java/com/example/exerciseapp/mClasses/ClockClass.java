package com.example.exerciseapp.mClasses;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ClockClass {

    private Context mContext;

    private ProgressBar progressBar;

    private TextView showTimeTV;

    private Button addTimeBtn;
    private Button decreaseTimeBtn;
    private Button skipTimeBtn;

    private int second = 0, minute = 0, hour = 0;
    private int transit = 0;
    private int currentProgress = 0;
    private int maxTime;

    private String secondTxt, minuteTxt, hourTxt;

    //    Widgets;
    private Button plusBtn;
    private Button minusBtn;

    //    Initializing variables;

    private boolean statusExercise;
    private byte countExercise;

    //    Start loop = true; || Stop loop = false;
    private boolean clockWork;
    //    Increases = false; || Decreases = true;
    private boolean clockState;
    //    To avoid refreshing 'setMax' whenever a loop is executed and the second is not changed;
    private boolean clockMax;
    //    Activate buttons = true; Disable buttons = false;
    private boolean buttonState;
    //    Activate buttons = true; Disable buttons = false;
    private boolean buttonPlusMinus;

    public void runClock() {

        clockWork = true;
        clockMax = true;

        if (clockState) {
            setTime();
        }

        if (buttonState) {
            addTime();
            skipTime();
        }
        if (buttonPlusMinus) {
            plusTime();
            minusTime();
        }

        updateProgressBar();
    }

    public ClockClass() {
//        Empty constructor;
    }

    public ClockClass(Context context) {
        this.mContext= context;
    }
    //    insert Context; set ClockState Increases = false, Decreases = true; set maxTime;
    public ClockClass(Context context, boolean clockState, int maxTime) {
        this.mContext = context;
        this.clockState = clockState;
        this.maxTime = maxTime;
    }
    //    insert Context; set ClockState Increases = false, Decreases = true; set maxTime;
    public ClockClass(Context context, boolean clockState, int maxTime, boolean buttonState, boolean buttonPlusMinus) {
        this.mContext = context;
        this.clockState = clockState;
        this.maxTime = maxTime;
        this.buttonState = buttonState;
        this.buttonPlusMinus = buttonPlusMinus;
    }

    //    insert ProgressBar;
    public ClockClass setBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        return this;
    }

    public ClockClass setMaxTime(int maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    //    insert a filed that shows the time;
    public ClockClass setTextView(TextView showTimeTV) {
        this.showTimeTV = showTimeTV;
        return this;
    }

    //    insert a button that adds time;
    public ClockClass setAddBtn(Button addTimeBtn) {
        this.addTimeBtn = addTimeBtn;
        return this;
    }

    //    insert a button that skip time;
    public ClockClass setSkipBtn(Button skipTimeBtn) {
        this.skipTimeBtn = skipTimeBtn;
        return this;
    }

    //    insert a button that plus time;
    public ClockClass setPlusBtn(Button plusBtn) {
        this.plusBtn = plusBtn;
        return this;
    }

    //    insert a button that minus time;
    public ClockClass setMinusBtn(Button minusBtn) {
        this.minusBtn = minusBtn;
        return this;
    }

    //    insert a value (external second);
    public ClockClass setSecond(int second) {
        this.second = second;
        return this;
    }

    private void setTime() {
        second = maxTime;
        currentProgress = second;
    }

    public ClockClass setData(boolean statusExercise, byte countExercise) {
        this.statusExercise = statusExercise;
        this.countExercise = countExercise;
        return this;
    }

    public void updateProgressBar() {

        new Thread(() -> {
            while (clockWork) {
                if (clockMax) {
                    progressBar.setMax(maxTime);
                    clockMax = false;
                }
//                ***Time decreases***
                if (clockState) {
                    increaseTime();
                } else {
//                    ***Time increases***
                    decreaseTime();
                }

                progressBar.setProgress(currentProgress);

//                Change time in TextView;
                new Handler(Looper.getMainLooper()).post(this::clockInstructionsInternal);

//                Delay loop;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                Stop loop
                if (second == 0 || second == maxTime) {
                    clockWork = false;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
            }
        }).start();
    }

    private void increaseTime() {
        second--;
        currentProgress--;
    }

    private void decreaseTime() {
        second++;
        currentProgress++;

        if (currentProgress == 60) {
            currentProgress = 0;
        }
    }

    private void plusTime() {

        plusBtn.setOnClickListener(v -> {
            maxTime += 5;
            second += 5;
            currentProgress += 5;
        });
    }

    private void minusTime() {

        minusBtn.setOnClickListener(v -> {
            maxTime -= 5;
            second -= 5;
            currentProgress -= 5;
        });
    }

    private void addTime() {

        addTimeBtn.setOnClickListener(v -> {

//                      ***Time decreases***
            if (clockState) {
                maxTime += 15;
                second += 15;
                currentProgress += 15;
            } else {
//                      ***Time increases***
                if (second < 15 && minute == 0) {
                    second = 0;
                    currentProgress = 0;
                } else if (second < 15 && minute >= 1) {
                    transit = 15 - second;
                    minute--;
                    second = 60 - transit;
                    currentProgress = second;
                } else {
                    second -= 15;
                    currentProgress -= 15;
                }
            }
            clockMax = true;
            if (!clockWork) {
                clockWork = true;
                updateProgressBar();
            }
        });
    }

    private void skipTime() {

        skipTimeBtn.setOnClickListener(v -> {

            if (clockWork) {
                clockWork = false;
            } else {
                clockWork = true;
                updateProgressBar();
            }
        });
    }

    public void clockInstructionsExternal() {

        setTime();
//        When i put second from other class, second always is switched,
//        so when second equal 60 then add one minute,
//        but when value is bigger like 125 (60 && 65)
//        then condition execute only once and score is 01:65, 01:70, ...
//        do while loop check every inserted value (will likely reduce performance);
        do {
            if (second >= 60) {
                second -= 60;
                minute++;
            } else if (minute > 0 && second == 0) {
                minute--;
                second += 60;
            }
        } while (second > 60);

        do {
            if (minute >= 60) {
                minute -= 60;
                hour++;
            } else if (hour > 0 && minute == 0) {
                hour--;
                minute += 60;
            }
        } while (minute > 60);

        clockInstructions();
    }

    private void clockInstructionsInternal() {

        if (second >= 60) {
            second -= 60;
            minute++;
        } else if (minute > 0 && second == 0) {
            minute--;
            second += 60;
        }

        if (minute >= 60) {
            minute -= 60;
            hour++;
        } else if (hour > 0 && minute == 0) {
            hour--;
            minute += 60;
        }
        clockInstructions();
    }

    private void clockInstructions() {

        String secondsClock = String.valueOf(second);
        String minutesClock = String.valueOf(minute);
        String hoursClock = String.valueOf(hour);
        String bridgeMinutes = String.valueOf(minute + 1);
        String bridgeHours = String.valueOf(hour + 1);

        if (hour <= 9) {
            hourTxt = "0" + hoursClock;
        } else {
            hourTxt = hoursClock;
        }

        if (minute <= 9) {
            minuteTxt = "0" + minutesClock;
        } else {
            minuteTxt = minutesClock;
        }

        if (second <= 9) {
            secondTxt = "0" + secondsClock;
        } else {
            secondTxt = secondsClock;
        }

        String firstClockOption = minuteTxt + ":" + secondTxt;

        if (minute == 60) {

            if (hour <= 9) {
                hourTxt = bridgeHours;
                String secondClockOption = "0" + hourTxt + ":00:" + secondTxt;
                showTimeTV.setText(secondClockOption);
            } else {
                showTimeTV.setText(firstClockOption);
            }
        } else if (second == 60) {

            if (minute <= 9) {
                minuteTxt = bridgeMinutes;
                String thirdClockOption = "0" + minuteTxt + ":00";
                showTimeTV.setText(thirdClockOption);
            } else {
                showTimeTV.setText(firstClockOption);
            }
        } else {
            showTimeTV.setText(firstClockOption);
        }
    }
}

