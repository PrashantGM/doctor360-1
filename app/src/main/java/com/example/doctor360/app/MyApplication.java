package com.example.doctor360.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.balsikandar.crashreporter.CrashReporter;
import com.orhanobut.hawk.Hawk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class MyApplication extends MultiDexApplication {

    public static Context app;
    public static final String DATE_FORMAT = "yyyy/MM/dd";

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReporter.initialize(this);
        app = getApplicationContext();
        Hawk.init(app).build();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void showToasyNoInternetMessage(Activity activity){
        Toasty.error(activity,"No Internet Connection! Please Connect to WIFI or Mobile Data",200).show();
    }

    public static void showToasyFailedLoadMessage(Activity activity){
        Toasty.error(activity,"Failed to Load Data...",200).show();
    }

    public static void hideKeyboardActivity(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputManager.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }


    public static String getDiff(String givenDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");

        Date postDate = null;

        try {
            postDate = formatter.parse(givenDate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Date today = new Date();

        long diff = today.getTime() - postDate.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diffSeconds / 60;
        long diffHours = diffMinutes / 60;
        int diffDays = (int) diffHours / 24;
        int diffMonths = diffDays / 30;
        int diffYears = diffMonths / 12;


        if (diffSeconds < 60) {
            return diffSeconds + " sec ago";
            // return "1s";
        } else if (diffSeconds == 60) {
            return "1 min ago";
        } else if (diffSeconds > 60) {
            if (diffMinutes < 60) {
                return diffMinutes + " mins ago";
            } else if (diffMinutes == 1) {
                return "1 mins ago";
            } else if (diffMinutes == 60) {
                return "1 hour ago";
            } else if (diffMinutes > 60) {
                if (diffHours == 1) {
                    return "1 hour ago";
                } else if (diffHours < 24) {
                    return diffHours + " hours ago";
                } else if (diffHours == 24) {
                    return "1 day ago";
                } else if (diffHours > 24) {
                    if (diffDays == 1) {
                        return "1 day ago";
                    } else if (diffDays < 30) {
                        return diffDays + " days ago";
                    } else if (diffDays == 30) {
                        return "1 month ago";
                    } else if (diffDays > 30) {
                        if (diffMonths == 1) {
                            return "1 mon ago";
                        } else if (diffMonths < 12) {
                            return diffMonths + " months ago";
                        } else if (diffMonths == 12) {
                            return "1 year ago";
                        } else if (diffMonths > 12) {
                            if (diffYears == 1) {
                                return "1 year ago";
                            } else if (diffYears < 10) {
                                return diffYears + " years ago";
                            } else if (diffYears == 10) {
                                return "1dec";
                            } else {
                                return "> 1dec";
                            }
                        }
                    }

                }

            }
        }
        return givenDate;
    }

    public static String convertDate(String oldDate)
    {
        SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        Date testDate=null;
        try {
            testDate = readFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        String newFormat = formatter.format(testDate);

        return newFormat;
    }

    public static void getEnglishCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf_date = new SimpleDateFormat("MMM dd, yyyy");
        String currentDate = sdf_date.format(calendar.getTime());
    }

    public static void getEnglishCurrentDay()
    {
        SimpleDateFormat sdf_time = new SimpleDateFormat("EEE");
        Date date = new Date();
        String currentDay = sdf_time.format(date);
    }

    public static long getCurrentTime(){
        return System.currentTimeMillis();
    }

    public static boolean shouldLoadNewNews(long currTime, long prevTime){
        return (currTime - prevTime) > 15*60*1000;
    }

}