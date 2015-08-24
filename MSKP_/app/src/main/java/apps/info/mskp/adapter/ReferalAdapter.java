package apps.info.mskp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import apps.info.mskp.R;
import apps.info.mskp.model.ReferalItem;

/**
 * Created by ACER on 13/08/2015.
 */
public class ReferalAdapter extends BaseAdapter {
    private List<ReferalItem> mData = new ArrayList<ReferalItem>();
    private Context context;

    public ReferalAdapter(Context context){
        this.context = context;
    }

    public void setItem(List<ReferalItem> items){
        this.mData = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return mData.size();
    }
    @Override
    public ReferalItem getItem(int position){
        return mData.get(position);
    }

    @Override
    public long getItemId(int position){
        return mData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.referal_row, null);
            holder.id_member = (TextView) convertView.findViewById(R.id.id_member);
            holder.nama = (TextView) convertView.findViewById(R.id.nama);
            holder.alamat = (TextView) convertView.findViewById(R.id.alamat);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.id_member.setText(mData.get(position).getId_member());
        holder.nama.setText(mData.get(position).getNama());
        holder.alamat.setText(mData.get(position).getAlamat());



        return convertView;
    }

    public static class ViewHolder {
        public TextView id_member;
        public TextView nama;
        public TextView alamat;
    }
}
