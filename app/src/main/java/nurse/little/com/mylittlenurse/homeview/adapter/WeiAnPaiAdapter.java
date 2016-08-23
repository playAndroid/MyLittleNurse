package nurse.little.com.mylittlenurse.homeview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import nurse.little.com.mylittlenurse.R;

/**
 * 未排班adapter
 * Created by user on 2016/3/28.
 */
public class WeiAnPaiAdapter extends RecyclerView.Adapter<WeiAnPaiAdapter.ViewHolder> {


    private final ArrayList<String> data;

    public WeiAnPaiAdapter(Context context, ArrayList<String> list) {
        this.data = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_weianpai, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(data.get(position));
        Logger.e("onBindViewHolder"+data.get(position));
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView1);
        }
    }
}
