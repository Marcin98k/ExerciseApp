package com.example.exerciseapp;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class RedundantCode {

//    Obliczaine daty poniedizłaku; Użyte przy wyznaczaniu konkretnych tygodni;
//    LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//    String mondayPattern = monday.format(LONG_DATE_TIME_FORMATTER);
//    convertToInteger = Integer.parseInt(mondayPattern);

//    Ustawienie tygodnia zaczynającego się od poniedziałku(według polskiego kalendarza);
//    WeekFields weekFields = WeekFields.of(new Locale("pl", "PL"));


//  Zmiana języka dla fragmentu


//    public class MyFragment extends Fragment {
//        private Context mContext;
//
//        @Override
//        public void onAttach(Context context) {
//            super.onAttach(context);
//            mContext = updateBaseContextLocale(context);
//        }
//
//        private Context updateBaseContextLocale(Context context) {
//            String language = "fr"; // Ustaw język na francuski
//            Locale locale = new Locale(language);
//            Locale.setDefault(locale);
//
//            Configuration configuration = context.getResources().getConfiguration();
//            configuration.setLocale(locale);
//            return context.createConfigurationContext(configuration);
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            // Użyj mContext do uzyskania zasobów zaktualizowanych dla nowej konfiguracji
//            inflater = inflater.cloneInContext(mContext);
//            View view = inflater.inflate(R.layout.fragment_my, container, false);
//            // ...
//            return view;
//        }
//    }


}


