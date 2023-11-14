package com.example.exerciseapp.mClasses;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.exerciseapp.mInterfaces.FragmentSupportListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrainingTimer {

    private static final int SLEEP_TIME_MS = 1000;
    private Context mContext;

    private ProgressBar progressBar;
    private TextView showTimeTV;
    private Button modifyTimeBtn;
    private Button skipTimeBtn;


    private int second;
    private int minute = 0, hour = 0, transit = 0;
    private int currentProgress = 0;
    private int maxTime;
    private String secondTxt, minuteTxt, hourTxt;

    private volatile boolean isRunning = true;
    private boolean isTimerRunning;
    private boolean timerState;
    private boolean isMaxTimeSet;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private FragmentSupportListener fragmentSupportListener;

    private TrainingTimer(TrainingTimerBuilder trainingTimerBuilder) {
        this.mContext = trainingTimerBuilder.mContext;
        this.progressBar = trainingTimerBuilder.progressBar;
        this.showTimeTV = trainingTimerBuilder.showTimeTV;
        this.modifyTimeBtn = trainingTimerBuilder.modifyTimeBtn;
        this.skipTimeBtn = trainingTimerBuilder.skipTimeBtn;
        this.timerState = trainingTimerBuilder.timerState;
        this.second = trainingTimerBuilder.second;
        this.maxTime = trainingTimerBuilder.maxTime;
    }

    public void addSeconds(int second) {
        this.second += second;
    }

    public void subtractSeconds(int second) {
        this.second -= second;
    }

    public void runTimer() {

        isTimerRunning = true;
        isMaxTimeSet = true;

        if (timerState) {
            setTime();
        } else {
            maxTime = second;
        }

        addTime();
        skipTime();
        updateTimer();
    }

    private void updateTimer() {

        executorService.submit(() -> {
            while (isRunning) {
                if (isMaxTimeSet) {
                    progressBar.setMax(maxTime);
                    isMaxTimeSet = false;
                }

                adjustTimeAndProgress();

                progressBar.setProgress(currentProgress);

                new Handler(Looper.getMainLooper()).post(() -> dynamicIncreaseTime(showTimeTV));

                try {
                    Thread.sleep(SLEEP_TIME_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if (second == 0 || second == maxTime) {
                    isRunning = false;
                    checkStatusFragmentSupportListener();
                }
            }
        });
    }

    private void adjustTimeAndProgress() {

        if (timerState) {
            second--;
            currentProgress--;
        } else {
            second++;
            currentProgress++;
        }
    }

    public void stopThread() {
        isRunning = false;
    }

    private void setTime() {
        second = maxTime;
        currentProgress = second;
    }

    private void addTime() {

        if (modifyTimeBtn == null) {
            return;
        }

        modifyTimeBtn.setOnClickListener(v -> {

//                      ***Time decreases***
            if (timerState) {
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
            if (!isTimerRunning) {
                isTimerRunning = true;
                updateTimer();
            }
        });
    }

    private void skipTime() {

        if (skipTimeBtn == null) {
            return;
        }

        skipTimeBtn.setOnClickListener(v -> {
            stopThread();
            checkStatusFragmentSupportListener();
        });
    }

    private void checkStatusFragmentSupportListener() {
        if (fragmentSupportListener != null) {
            fragmentSupportListener.checkCondition(true);
        }
    }

    public void dynamicIncreaseTime(TextView text) {

//        When put second from other class, second always is switched,
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

        timerInstructionsExt(text);
    }

    private void convertTime(int from, int to) {
        if (second >= 60) {
            second -= 60;
            minute++;
        } else if (minute > 0 && second == 0) {
            minute--;
            second += 60;
        }
    }

    private void timerInstructionsExt(TextView textView) {

        String bridgeMinutes = String.valueOf(minute + 1);
        String bridgeHours = String.valueOf(hour + 1);

        hourTxt = formatTimeValue(hour);
        minuteTxt = formatTimeValue(minute);
        secondTxt = formatTimeValue(second);

        String firstTimerOption = minuteTxt + ":" + secondTxt;
        String timerOption = firstTimerOption;

        if (minute == 60) {
            timerOption = hour <= 9 ? "0" + bridgeHours + ":00:" + secondTxt : firstTimerOption;
        } else if (second == 60) {
            timerOption = minute <= 9 ? "0" + bridgeMinutes + ":00" : firstTimerOption;
        }
        textView.setText(timerOption);
    }

    private String formatTimeValue(int value) {
        return value <= 9 ? "0" + value : String.valueOf(value);
    }

    public void setFragmentSupportListener(FragmentSupportListener listener) {
        fragmentSupportListener = listener;
    }

    public static class TrainingTimerBuilder {

        private Context mContext;
        private ProgressBar progressBar;
        private TextView showTimeTV;
        private Button modifyTimeBtn;
        private Button skipTimeBtn;

        private int second = 0;
        private int maxTime;

        private boolean timerState;

        public TrainingTimerBuilder(Context context) {
            this.mContext = context;
        }

        public TrainingTimerBuilder(Context context, boolean timerState, int maxTime) {
            this.mContext = context;
            this.timerState = timerState;
            this.maxTime = maxTime;
        }

        public TrainingTimerBuilder setBar(ProgressBar progressBar) {
            this.progressBar = progressBar;
            return this;
        }

        public TrainingTimerBuilder setTextView(TextView showTimeTV) {
            this.showTimeTV = showTimeTV;
            return this;
        }

        public TrainingTimerBuilder setAddBtn(Button modifyTimeBtn) {
            this.modifyTimeBtn = modifyTimeBtn;
            return this;
        }

        public TrainingTimerBuilder setSkipBtn(Button skipTimeBtn) {
            this.skipTimeBtn = skipTimeBtn;
            return this;
        }

        public TrainingTimerBuilder setSecond(int second) {
            this.second = second;
            return this;
        }

        public TrainingTimer build() {
            return new TrainingTimer(this);
        }
    }
}

