package fakesetgame.seniordesign.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.DateFormat;

import fakesetgame.seniordesign.R;

/**
 * Created by Chris on 10/22/2014.
 */
public class GameSummaryListItemCursorAdapter extends CursorAdapter {
    private LayoutInflater mInflater;

    public GameSummaryListItemCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if (cursor.getPosition() % 2 == 1) {
            view.setBackgroundColor(context.getResources().getColor(R.color.background_odd));
        } else {
            view.setBackgroundColor(context.getResources().getColor(R.color.background_even));
        }

        TextView rankView = (TextView) view.findViewById(R.id.rank);
        TextView firstLineView = (TextView) view.findViewById(R.id.firstLine);
        TextView secondLineView = (TextView) view.findViewById(R.id.secondLine);

        int rank = cursor.getPosition() + 1;
        GameOutcome outcome = GameOutcome.fromCursor(context, cursor);
        rankView.setText(Integer.valueOf(rank).toString());
        long seconds = outcome.getElapsed() / 1000;
        firstLineView.setText(String.format("%d:%02d", seconds / 60, seconds % 60));
        secondLineView.setText(DateFormat.getDateInstance().format(outcome.getInserted()));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.game_summary_list_item_view, parent, false);
    }
}
