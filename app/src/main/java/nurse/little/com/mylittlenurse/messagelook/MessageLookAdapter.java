package nurse.little.com.mylittlenurse.messagelook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import nurse.little.com.mylittlenurse.DaoSession;
import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.SickPeople;
import nurse.little.com.mylittlenurse.SickPeopleDao;
import nurse.little.com.mylittlenurse.bast.NurseApplication;
import nurse.little.com.mylittlenurse.bast.TableName;

/**
 * 信息预览界面适配器
 * Created by user on 2016/3/17.
 */
public class MessageLookAdapter extends RecyclerView.Adapter<MessageLookAdapter.ViewHolder> {


    private List<SickPeople> list;
    private final SickPeopleDao sickPeopleDao;

    public MessageLookAdapter(Context context, List<SickPeople> list) {
        Collections.reverse(list);//集合倒序
        this.list = list;
        DaoSession daoSession = NurseApplication.getDaoSession(context, TableName.DB_SICK_NAME);
        sickPeopleDao = daoSession.getSickPeopleDao();
    }


    @Override
    public MessageLookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_look, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageLookAdapter.ViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.sex.setBackgroundResource(list.get(position).getSex().equals("男") ? R.drawable.sex_man : R.drawable.sex_woman);
        holder.phone.setText(list.get(position).getTel());
        holder.add_time.setText(list.get(position).getTime());
//        holder.linearLayout.seton
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos,list.get(position));
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos,list.get(position));
                    return true;
                }
            });
        }

    }

    public void setAdapterList(List<SickPeople> sickPeoples) {
        Collections.reverse(sickPeoples);//集合倒序
        this.list = sickPeoples;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public void deleveData(int position) {
        SickPeople sickPeople = list.get(position);
        sickPeopleDao.delete(sickPeople);
        list.remove(position);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView sex;
        private TextView phone;
        private TextView add_time;
//        private LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            sex = (ImageView) view.findViewById(R.id.sex);
            phone = (TextView) view.findViewById(R.id.phone);
            add_time = (TextView) view.findViewById(R.id.add_time);
//            linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        }
    }

    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position,SickPeople people);

        void onItemLongClick(View view, int position,SickPeople people);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}
