package com.oozeetech.bizdesk.adapter;

/**
 * Created by Vishal Sojitra on 29/07/15.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.models.NavDrawerItem;
import com.oozeetech.bizdesk.widget.DTextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.oozeetech.bizdesk.utils.FontUtils.fontName;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    Typeface font;
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        font = fontName(context, 1);
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        NavDrawerItem current = data.get(position);
        if (current.isShowNotify())
//            vh.llRawItem.setBackgroundColor(context.getColor(R.color.colorPrimary));
            vh.llRawItem.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        else
            vh.llRawItem.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
//        vh.llRawItem.setBackgroundColor(context.getColor(R.color.colorWhite));

        vh.txtDrawerText.setText(current.getTitle());
        vh.imgDrawerIcon.setImageResource(current.getRec());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgDrawerIcon)
        ImageView imgDrawerIcon;
        @BindView(R.id.txtDrawerText)
        DTextView txtDrawerText;
        @BindView(R.id.llRawItem)
        LinearLayout llRawItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
