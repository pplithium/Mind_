package ck.no.mind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import androidx.appcompat.app.AppCompatActivity;
import ck.no.mind.App;
import ck.no.mind.R;
import java.util.Calendar;

/**
 * Calendar activity to choose between the dates, give feedback about the day/hour and read the data
 * from previous responses.
 *
 */
public class HealthCalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    int selectedYear = -1;
    int selectedMonth = -1;
    int selectedDayOfMonth = -1;

    int currentYear = -1;
    int currentMonth = -1;
    int currentDayOfMonth = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_calendar);

        Calendar c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH);
        currentDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        calendarView = findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(
                    CalendarView view, int year, int month, int dayOfMonth) {
                selectedYear = year;
                selectedMonth = month;
                selectedDayOfMonth = dayOfMonth;
            }
        });
    }

    private boolean isSelectedDateFuture() {
        if (currentYear == -1) {
            return false;
        }

        if (currentYear < selectedYear) {
            return true;
        }

        if (currentYear == selectedYear && currentMonth < selectedMonth) {
            return true;
        }

        if (currentYear == selectedYear && currentMonth == selectedMonth
                && currentDayOfMonth < selectedDayOfMonth) {
            return true;
        }

        return false;
    }

    private Intent createIntentForSelectedDate() {
        Intent intent = new Intent(this, AssessmentActivity.class);

        if (selectedYear == -1) {
            intent.putExtra("year", currentYear);
            intent.putExtra("month", currentMonth);
            intent.putExtra("day", currentDayOfMonth);
        } else {
            intent.putExtra("year", selectedYear);
            intent.putExtra("month", selectedMonth);
            intent.putExtra("day", selectedDayOfMonth);
        }
        return intent;
    }

    public void launchAssestmentActivity(View v) {
        if (isSelectedDateFuture()) {
            App.showToast("Selected date is future. Please select the current date, or past.");
            return;
        }

        startActivity(createIntentForSelectedDate().putExtra("read_only", false));
    }

    public void launchAssestmentActivityReadOnly(View v) {
        if (isSelectedDateFuture()) {
            App.showToast("Selected date is future. Please select the current date, or past.");
            return;
        }

        startActivity(createIntentForSelectedDate().putExtra("read_only", true));
    }
}