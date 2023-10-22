package com.example.exerciseapp.mClasses;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.exerciseapp.mInterfaces.FragmentSupportListener;

public class ClockClass {

    private Context mContext;

    private FragmentSupportListener fragmentSupportListener;

    private ProgressBar progressBar;
    private TextView showTimeTV;
    private Button modifyTimeBtn;
    private Button skipTimeBtn;


    private int second = 0;
    private int minute = 0, hour = 0;
    private int transit = 0;
    private int currentProgress = 0;
    private int maxTime;
    private String secondTxt, minuteTxt, hourTxt;


    private Thread thread;
    private volatile boolean isRunning = true;
    private boolean isClockRunning;
    private boolean clockState;
    private boolean isMaxTimeSet;
    private boolean isButtonActive;

    public ClockClass(Context context) {
        this.mContext = context;
    }

    public ClockClass(Context context, boolean clockState, int maxTime) {
        this.mContext = context;
        this.clockState = clockState;
        this.maxTime = maxTime;
    }

    public ClockClass(Context context, boolean clockState, int maxTime, boolean isButtonActive) {
        this.mContext = context;
        this.clockState = clockState;
        this.maxTime = maxTime;
        this.isButtonActive = isButtonActive;
    }

    public ClockClass setBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        return this;
    }

    public ClockClass setTextView(TextView showTimeTV) {
        this.showTimeTV = showTimeTV;
        return this;
    }

    public ClockClass setAddBtn(Button modifyTimeBtn) {
        this.modifyTimeBtn = modifyTimeBtn;
        return this;
    }

    public ClockClass setSkipBtn(Button skipTimeBtn) {
        this.skipTimeBtn = skipTimeBtn;
        return this;
    }

    public ClockClass setSecond(int second) {
        this.second = second;
        return this;
    }

    public void addSecond(int second) {
        this.second += second;
    }

    public void minusSecond(int second) {
        this.second -= second;
    }

    public void runClock() {

        isClockRunning = true;
        isMaxTimeSet = true;

        if (clockState) {
            setTime();
        } else {
            maxTime = second;
        }

        if (isButtonActive) {
            addTime();
            skipTime();
        }
        updateProgressBar();
    }

    private void updateProgressBar() {

        thread = new Thread(() -> {
            while (isRunning) {
                if (isMaxTimeSet) {
                    progressBar.setMax(maxTime);
                    isMaxTimeSet = false;
                }
//                ***Time decreases***
                if (clockState) {
                    second--;
                    currentProgress--;
                } else {
//                    ***Time increases***
                    second++;
                    currentProgress++;
                }

                progressBar.setProgress(currentProgress);

//                Change time in TextView;
                new Handler(Looper.getMainLooper()).post(() -> dynamicIncreaseTime(showTimeTV));

                Log.d(TAG, "updateProgressBar: Thread: " + Thread.currentThread().getId() +
                        " sec: " + second);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                Stop loop
                if (second == 0 || second == maxTime) {
                    isRunning = false;
                    if (fragmentSupportListener != null) {
                        fragmentSupportListener.checkCondition(true);
                    }
                }
            }
            thread.interrupt();
        });
        thread.start();
    }

    public ClockClass stopThread() {
        isRunning = false;
        return this;
    }

    private void setTime() {
        second = maxTime;
        currentProgress = second;
    }

    private void addTime() {

        modifyTimeBtn.setOnClickListener(v -> {

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
            isMaxTimeSet = true;
            if (!isClockRunning) {
                isClockRunning = true;
                updateProgressBar();
            }
        });
    }

    private void skipTime() {
        skipTimeBtn.setOnClickListener(v -> {
            stopThread();
            if (fragmentSupportListener != null) {
                fragmentSupportListener.checkCondition(true);
            }
        });
    }

    public void dynamicIncreaseTime(TextView text) {

//        setTime();
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

        clockInstructionsExt(text);
    }

    private void clockInstructionsExt(TextView textView) {

        String bridgeMinutes = String.valueOf(minute + 1);
        String bridgeHours = String.valueOf(hour + 1);

        hourTxt = formatTimeValue(hour);
        minuteTxt = formatTimeValue(minute);
        secondTxt = formatTimeValue(second);

        String firstClockOption = minuteTxt + ":" + secondTxt;
        String clockOption = firstClockOption;

        if (minute == 60) {
            clockOption = hour <= 9 ? "0" + bridgeHours + ":00:" + secondTxt : firstClockOption;
        } else if (second == 60) {
            clockOption = minute <= 9 ? "0" + bridgeMinutes + ":00" : firstClockOption;
        }
        textView.setText(clockOption);
    }

    private String formatTimeValue(int value) {
        return value <= 9 ? "0" + value : String.valueOf(value);
    }

    public void setFragmentSupportListener(FragmentSupportListener listener) {
        fragmentSupportListener = listener;
    }
}

