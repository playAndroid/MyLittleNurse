package nurse.little.com.mylittlenurse.broadcastmess.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.XuanJiao;

/**
 * Created by user on 2016/4/6.
 */
public class XuanJiaoAdapter extends RecyclerView.Adapter<XuanJiaoAdapter.ViewHolder> {


    private final Context context;
    private final List<XuanJiao> xuanJiaos;

    public XuanJiaoAdapter(Context context, List<XuanJiao> xuanJiaos) {
        this.context = context;
        this.xuanJiaos = xuanJiaos;
    }

    @Override
    public XuanJiaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.xuan_recycler_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final XuanJiaoAdapter.ViewHolder holder, final int position) {
        final XuanJiao xuanJiao = xuanJiaos.get(position);
        holder.xuan_content.setText(position + 1 + ":" + xuanJiao.getContent());
        holder.xuan_time.setText(R.string.record + xuanJiao.getTime());

        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos, xuanJiao);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos, xuanJiao);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return xuanJiaos.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView xuan_content;
        private TextView xuan_time;

        public ViewHolder(View itemView) {
            super(itemView);
            xuan_content = (TextView) itemView.findViewById(R.id.xuan_content);
            xuan_time = (TextView) itemView.findViewById(R.id.xuan_time);
        }
    }

    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position, XuanJiao xuanJiao);

        void onItemLongClick(View view, int position, XuanJiao xuanJiao);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
