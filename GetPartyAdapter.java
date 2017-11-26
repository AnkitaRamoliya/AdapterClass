package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.listener.PartyListener;
import com.oozeetech.bizdesk.models.party.GetPartyResponse;
import com.oozeetech.bizdesk.widget.DTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetPartyAdapter extends RecyclerView.Adapter<GetPartyAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<GetPartyResponse.Data> items = new ArrayList<>();
    private LayoutInflater inflater;
    private PartyListener partyListener;
    private ValueFilter valueFilter;
    private ArrayList<GetPartyResponse.Data> filterItems;

    public GetPartyAdapter(Context context, PartyListener partyListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.filterItems = items;
        this.partyListener = partyListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_party_raw_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int i) {
        final GetPartyResponse.Data data = items.get(i);
        if (!data.getName().isEmpty())
            vh.txtSingleChar.setText(data.getName().substring(0, 1).toUpperCase());
        else
            vh.txtSingleChar.setText("P");
        vh.txtPartyName.setText(data.getName());
        vh.tvCompanyName.setText(data.getCompanyName());
        vh.txtMobile.setText(String.format("%s,%s", data.getContact1(), data.getContact2()));
        vh.imgEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partyListener.onPartyEditClickListener(i);
            }
        });
        vh.imgCallUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partyListener.onCallClickListener(i);
            }
        });
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    @Override
    public Filter getFilter() {

        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence != null && charSequence.length() > 0) {
                ArrayList<GetPartyResponse.Data> filterList = new ArrayList<>();
                for (int i = 0; i < filterItems.size(); i++) {
                    if ((filterItems.get(i).getName().toUpperCase())
                            .contains(charSequence.toString().toUpperCase())) {
                        filterList.add(filterItems.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = filterItems.size();
                results.values = filterItems;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            items = (ArrayList<GetPartyResponse.Data>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtSingleChar)
        DTextView txtSingleChar;
        @BindView(R.id.txtPartyName)
        DTextView txtPartyName;
        @BindView(R.id.tvCompanyName)
        DTextView tvCompanyName;
        @BindView(R.id.txtMobile)
        DTextView txtMobile;
        @BindView(R.id.imgEditUser)
        ImageView imgEditUser;
        @BindView(R.id.imgCallUser)
        ImageView imgCallUser;
        @BindView(R.id.activity_main)
        LinearLayout activityMain;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
