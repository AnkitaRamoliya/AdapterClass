package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.listener.FilterListener;
import com.oozeetech.bizdesk.models.party.GetPartyResponse;
import com.oozeetech.bizdesk.widget.DCheckBox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterPartyAdapter extends RecyclerView.Adapter<FilterPartyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GetPartyResponse.Data> items = new ArrayList<>();
    private LayoutInflater inflater;
    private FilterListener filterListener;

    public FilterPartyAdapter(Context context, FilterListener filterListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.filterListener = filterListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.filter_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int i) {
        final GetPartyResponse.Data data = items.get(i);
        vh.ckbrowItem.setText(data.getName());
        vh.ckbrowItem.setChecked(items.get(i).isSelected());
        vh.ckbrowItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(i).setSelected(vh.ckbrowItem.isChecked());
                filterListener.onCheckChangeListener();
                notifyDataSetChanged();
            }
        });
    }

    public GetPartyResponse.Data get(int position) {
        return items.get(position);
    }

    public void addAll(List<GetPartyResponse.Data> getPartyResponses) {

        items.addAll(getPartyResponses);
        notifyDataSetChanged();
    }

    public List<GetPartyResponse.Data> getAll() {
        return items;
    }

    public void add(GetPartyResponse.Data data) {
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
        @BindView(R.id.ckbrowItem)
        DCheckBox ckbrowItem;
        @BindView(R.id.activity_main)
        LinearLayout activityMain;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
