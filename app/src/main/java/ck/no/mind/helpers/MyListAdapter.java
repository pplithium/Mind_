package ck.no.mind.helpers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ck.no.mind.R;

/**
 * ListAdapter class for showing listView for psychologists and events
 *
 */
public class MyListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final String[] dates;
    private final Integer[] imgid;
    private final Integer[] stars;

    public MyListAdapter(
            Activity context, String[] maintitle, String[] subtitle, Integer[] imgid,
            Integer[] stars, String[] dates) {
        super(context, R.layout.mylist, maintitle);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.maintitle = maintitle;
        this.subtitle = subtitle;
        this.imgid = imgid;
        this.stars = stars;
        this.dates = dates;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        ImageView rating = (ImageView) rowView.findViewById(R.id.rating);
        TextView datesText = (TextView) rowView.findViewById(R.id.date);

        titleText.setText(maintitle[position]);
        imageView.setImageResource(imgid[position]);
        subtitleText.setText(subtitle[position]);
        rating.setImageResource(stars[position]);

        if (dates != null) {
            datesText.setVisibility(View.VISIBLE);
            datesText.setText(dates[position]);
        }
        return rowView;
    };
}
