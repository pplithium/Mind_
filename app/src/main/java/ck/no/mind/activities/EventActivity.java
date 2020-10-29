package ck.no.mind.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import ck.no.mind.App;
import ck.no.mind.R;
import ck.no.mind.helpers.MyListAdapter;

/**
 * TODO
 * Add a real data from the real API. Currently not available to show all the events in the
 * city.
 *
 * This activity shows user about the events around the user
 */
public class EventActivity extends AppCompatActivity {
    ListView list;

    // example data
    String[] maintitle = {
            "OSLO WORLD 2020",
            "Let’s talk about integration in Norway",
            "Djangofestivalen 2021 // Cosmopolite",
            "Kampenbobler!",
            "Bryggedans",
    };

    String[] subtitle = {
            "Music", "Networking, Discussion", "Music, Networking", "Other", "Dance",
    };

    Integer[] imgid = {
            R.drawable.oslo_world,   R.drawable.integration, R.drawable.djangofestivalen,
            R.drawable.kampenbobler, R.drawable.bryggedans,
    };

    String[] dates = {
            "Tomorrow, 18.30 CET", "Today, 20.00 CET", "30.10.2020, 12.30 CET",
            "Tomorrow, 20.30 CET", "Today 21.00 CET",
    };

    Integer[] stars = {
            R.drawable.star_5, R.drawable.star_5, R.drawable.star_4,
            R.drawable.star_5, R.drawable.star_5,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psycologists);

        MyListAdapter adapter = new MyListAdapter(this, maintitle, subtitle, imgid, stars, dates);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        App.notImplementedCodeToast();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}