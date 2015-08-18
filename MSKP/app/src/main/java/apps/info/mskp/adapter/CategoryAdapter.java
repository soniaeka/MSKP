package apps.info.mskp.adapter;

/**
 * Created by SONIA on 14/04/2015.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;
import java.util.List;

//import info.androidhive.slidingmenu.GlobalActivity;
import apps.info.mskp.R;
import apps.info.mskp.model.CategoryItem;

public class CategoryAdapter extends BaseAdapter {
    private List<CategoryItem> mData=new ArrayList<CategoryItem>();
    private Context context;

    public CategoryAdapter (Context context){
        this.context=context;
    }
    public void setItem(List<CategoryItem> items){
        this.mData=items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return mData.size();
    }
    @Override
    public CategoryItem getItem(int position){
        return mData.get(position);
    }

    @Override
    public long getItemId(int position){
        return mData.get(position).getTc_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.category_row, null);
            holder.category = (TextView) convertView.findViewById(R.id.category);
            holder.pict = (ImageView) convertView.findViewById(R.id.pict);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.category.setText(mData.get(position).getCategory());

        //  AQuery aq=new AQuery(holder.foto_jenis);
        //aq.image( mData.get(position).getFoto_jenis());

        UrlImageViewHelper.setUrlDrawable(holder.pict, mData.get(position).getPict());

        return convertView;
    }

    public static class ViewHolder {
        public TextView category;
        public ImageView pict;

    }


}

