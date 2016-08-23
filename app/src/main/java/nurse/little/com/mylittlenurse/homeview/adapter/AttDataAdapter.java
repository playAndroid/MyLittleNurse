package nurse.little.com.mylittlenurse.homeview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nurse.little.com.mylittlenurse.R;


//   adapter 的点击事件 只关心  点击的都要哪些项目   
public class AttDataAdapter extends BaseAdapter {
    private List<String> srtList;

    private ArrayList<String> selectList;
    private Context context;
    private HashMap<String, Boolean> map;
    private OnTextClickListener listener;


    public AttDataAdapter(Context context, HashMap<String, Boolean> map, List<String> srtList,
                          OnTextClickListener listener) {
        this.context = context;
        this.srtList = srtList;
        selectList = new ArrayList<String>();
        this.map = map;
//			map = new HashMap<String, Boolean>();
//
//
//			for (String srtString : srtList) {
//				map.put(srtString, false);
//			}
//
//			for (int i = 0; i < srtList.size(); i++) {
//				map.put(i + "", false);
//			}
//			map.put("iszhu", false);
//			map.put("isxiu", false);
        this.listener = listener;

    }

    public AttDataAdapter(Context context, List<String> srtList,
                          OnTextClickListener listener) {
        this.context = context;
        this.srtList = srtList;
        selectList = new ArrayList<String>();
        map = new HashMap<String, Boolean>();


        for (String srtString : srtList) {
            map.put(srtString, false);
        }

        for (int i = 0; i < srtList.size(); i++) {
            map.put(i + "", false);
        }
        map.put("iszhu", false);
        map.put("isxiu", false);
        this.listener = listener;

    }

    public interface OnTextClickListener {
        void clickData(ArrayList<String> sList);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return srtList.size() / 3 + 1;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return srtList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_att_item, parent);
        }


        final TextView textView1 = (TextView) convertView
                .findViewById(R.id.textView1);
        final TextView textView2 = (TextView) convertView
                .findViewById(R.id.textView2);
        final TextView textView3 = (TextView) convertView
                .findViewById(R.id.textView3);

        textView1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (("不需驻场").equals(textView1.getText().toString())
                        || ("休息").equals(textView1.getText().toString())) {


                    if (!map.get("iszhu")) {
                        textView1
                                .setBackgroundResource(R.drawable.ic_click_true);
                        textView1.setTextColor(Color.parseColor("#0064c9"));
                        selectList.clear();

                        map.put("iszhu", true);
                        map.put("isxiu", true);
                        for (String srtString : srtList) {
                            map.put(srtString, false);
                        }
                        for (int i = 0; i < srtList.size(); i++) {
                            map.put(i + "", false);
                        }

                    } else {
                        textView1.setBackgroundResource(R.drawable.ic_click_no);
                        textView1.setTextColor(Color.parseColor("#666666"));
                        map.put("iszhu", false);
                        map.put("isxiu", false);

                    }
                } else {


                    if (!map.get(3 * position + "")) {

                        textView1
                                .setBackgroundResource(R.drawable.ic_click_true);
                        textView1.setTextColor(Color.parseColor("#0064c9"));
                        selectList.add(3 * position + "");
                        map.put(3 * position + "", true);
                        map.put("iszhu", false);
                        map.put("isxiu", false);

                    } else {
                        textView1.setBackgroundResource(R.drawable.ic_click_no);
                        textView1.setTextColor(Color.parseColor("#666666"));
                        selectList.remove(3 * position + "");
                        map.put(3 * position + "", false);
                    }

                }
                listener.clickData(selectList);
                notifyDataSetChanged();
            }
        });

        textView2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {

                    if (!map.get(3 * position + 1 + "")) {

                        textView2
                                .setBackgroundResource(R.drawable.ic_click_true);
                        textView2.setTextColor(Color.parseColor("#0064c9"));
                        map.put("iszhu", false);
                        map.put("isxiu", false);
                        map.put(3 * position + 1 + "", true);
                        selectList.add(3 * position + 1 + "");
                    } else {
                        textView2.setBackgroundResource(R.drawable.ic_click_no);
                        textView2.setTextColor(Color.parseColor("#666666"));
                        map.put(3 * position + 1 + "", false);
                        selectList.remove(3 * position + 1 + "");
                    }
                    listener.clickData(selectList);
                    notifyDataSetChanged();
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        });

        textView3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {

                    if (!map.get(3 * position + 2 + "")) {

                        textView3
                                .setBackgroundResource(R.drawable.ic_click_true);
                        textView3.setTextColor(Color.parseColor("#0064c9"));
                        map.put("iszhu", false);
                        map.put("isxiu", false);
                        map.put(3 * position + 2 + "", true);
                        selectList.add(3 * position + 2 + "");
                    } else {
                        textView3.setBackgroundResource(R.drawable.ic_click_no);
                        textView3.setTextColor(Color.parseColor("#666666"));
                        map.put(3 * position + 2 + "", false);
                        selectList.remove(3 * position + 2 + "");
                    }
                    listener.clickData(selectList);
                    notifyDataSetChanged();
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        });

        try {

            if (position < srtList.size() / 3 + 1) {

                try {

                    textView1.setText(srtList.get(3 * position));

                } catch (Exception e) {
                    // TODO: handle exception
                }
                try {
                    textView2.setText(srtList.get(3 * position + 1));

                } catch (Exception e) {
                    // TODO: handle exception
                }

                try {

                    textView3.setText(srtList.get(3 * position + 2));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }


            if (("不需驻场").equals(textView1.getText().toString())
                    || ("休息").equals(textView1.getText().toString())) {
                if (map.get("iszhu")) {
                    textView1.setBackgroundResource(R.drawable.ic_click_true);
                    textView1.setTextColor(Color.parseColor("#0064c9"));

                    textView2.setBackgroundResource(R.drawable.ic_click_no);
                    textView3.setBackgroundResource(R.drawable.ic_click_no);
                } else {
                    textView1.setBackgroundResource(R.drawable.ic_click_no);
                    textView1.setTextColor(Color.parseColor("#666666"));

                    if (map.get(3 * position + 1 + "")) {
                        textView2
                                .setBackgroundResource(R.drawable.ic_click_true);
                    } else {
                        textView2.setBackgroundResource(R.drawable.ic_click_no);
                    }

                    if (map.get(3 * position + 2 + "")) {
                        textView3
                                .setBackgroundResource(R.drawable.ic_click_true);
                    } else {
                        textView3.setBackgroundResource(R.drawable.ic_click_no);
                    }
                }
            } else {
                if (map.get(3 * position + "")) {
                    textView1.setBackgroundResource(R.drawable.ic_click_true);
                } else {
                    textView1.setBackgroundResource(R.drawable.ic_click_no);
                }

                if (map.get(3 * position + 1 + "")) {
                    textView2.setBackgroundResource(R.drawable.ic_click_true);
                } else {
                    textView2.setBackgroundResource(R.drawable.ic_click_no);
                }

                if (map.get(3 * position + 2 + "")) {
                    textView3.setBackgroundResource(R.drawable.ic_click_true);
                } else {
                    textView3.setBackgroundResource(R.drawable.ic_click_no);
                }

            }

            if (textView1.getText().toString().equals("")) {
                textView1.setBackgroundColor(Color.WHITE);
            }

            if (textView2.getText().toString().equals("")) {
                textView2.setBackgroundColor(Color.WHITE);
            }

            if (textView3.getText().toString().equals("")) {
                textView3.setBackgroundColor(Color.WHITE);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return convertView;

    }

    public void reSet() {

        for (String srtString : srtList) {
            map.put(srtString, false);
        }
        for (int i = 0; i < srtList.size(); i++) {
            map.put(i + "", false);
        }
        map.put("iszhu", false);
        map.put("isxiu", false);
        selectList.clear();
        notifyDataSetChanged();

    }

}
