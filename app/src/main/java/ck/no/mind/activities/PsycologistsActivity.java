package ck.no.mind.activities;

/**
 * TODO
 * Add a real data from the real API. Currently not available to show all the psycologists in the
 * city.
 */
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import ck.no.mind.App;
import ck.no.mind.R;
import ck.no.mind.helpers.MyListAdapter;

public class PsycologistsActivity extends AppCompatActivity {
    ListView list;

    String[] maintitle = {
            "Dr. Elenita Boes", "Dr. Mette Mirelle Østerud", "Dr. Carlota Kelly",
            "Dr. Zhao Huang",   "Dr. George Owusu",
    };

    String[] subtitle = {
            "Clinical psychology", "Cognitive and perceptual psychology",  "Social psychology",
            "Clinical psychology", "Industrial/organizational psychology",
    };

    Integer[] imgid = {
            R.drawable.psycologist_1, R.drawable.psycologist_2, R.drawable.psycologist_3,
            R.drawable.psycologist_4, R.drawable.psycologist_5,
    };

    Integer[] stars = {
            R.drawable.star_5, R.drawable.star_5, R.drawable.star_4,
            R.drawable.star_5, R.drawable.star_4,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psycologists);

        MyListAdapter adapter = new MyListAdapter(this, maintitle, subtitle, imgid, stars);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    App.notImplementedCodeToast();
                }

                else if (position == 1) {
                    App.notImplementedCodeToast();
                }

                else if (position == 2) {
                    App.notImplementedCodeToast();
                } else if (position == 3) {
                    App.notImplementedCodeToast();
                } else if (position == 4) {
                    App.notImplementedCodeToast();
                }
            }
        });
    }
}