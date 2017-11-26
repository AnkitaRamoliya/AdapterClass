package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.models.notification.GetNotificationResponse;
import com.oozeetech.bizdesk.widget.DTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GetNotificationResponse.Data> items = new ArrayList<>();
    private LayoutInflater inflater;

    public NotificationAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notification_raw_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int i) {

        GetNotificationResponse.Data data = items.get(i);
        if (!data.getTitle().isEmpty())
            vh.txtSingleChar.setText(data.getTitle().substring(0, 1).toUpperCase());
        else
            vh.txtSingleChar.setText("P");
        vh.txtTitle.setText(data.getTitle());
        vh.txtText.setText(data.getText());
        vh.txtTime.setText(String.format("%s", data.getTime()));

    }

    public GetNotificationResponse.Data get(int position) {
        return items.get(position);
    }

    public void addAll(List<GetNotificationResponse.Data> data) {

        items.addAll(data);
        notifyDataSetChanged();
    }

    public void add(GetNotificationResponse.Data data) {
        items.add(data);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtSingleChar)
        DTextView txtSingleChar;
        @BindView(R.id.txtTitle)
        DTextView txtTitle;
        @BindView(R.id.txtText)
        DTextView txtText;
        @BindView(R.id.txtTime)
        DTextView txtTime;
        @BindView(R.id.activity_main)
        LinearLayout activityMain;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
