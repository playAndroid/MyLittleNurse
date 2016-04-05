package nurse.little.com.mylittlenurse.homeview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import nurse.little.com.mylittlenurse.R;

/**
 * 当前排班人或项目的adapter
 */
public class AttCurrentAdapter extends BaseAdapter {
    private String[] srtList;
    private Context context;

    public AttCurrentAdapter(Context context, String[] srtList) {
        this.context = context;
        this.srtList = srtList;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (srtList.length % 3 == 0) {
            return srtList.length / 3;
        } else {

            return srtList.length / 3 + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return srtList[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {


            for (int i = 0; i < srtList.length; i++) {
                Log.e("test----", srtList[i]);
            }
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_att_item, null);

            TextView textView1 = (TextView) convertView
                    .findViewById(R.id.textView1);
            TextView textView2 = (TextView) convertView
                    .findViewById(R.id.textView2);
            TextView textView3 = (TextView) convertView
                    .findViewById(R.id.textView3);

            Log.e("test--before", position + "-------");
            try {


                if (position < srtList.length / 3 + 1) {

                    try {

                        textView1.setText(srtList[3 * position]);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    try {
                        textView2.setText(srtList[3 * position + 1]);

                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    try {

                        textView3.setText(srtList[3 * position + 2]);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        return convertView;

    }

}
