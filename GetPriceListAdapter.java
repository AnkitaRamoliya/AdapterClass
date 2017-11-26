package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.models.pricelist.GetPriceListResponse;
import com.oozeetech.bizdesk.ui.PriceDetailActivity;
import com.oozeetech.bizdesk.widget.DTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetPriceListAdapter extends RecyclerView.Adapter<GetPriceListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GetPriceListResponse.Data> items = new ArrayList<>();
    private LayoutInflater inflater;

    public GetPriceListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.price_list_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int i) {
        final GetPriceListResponse.Data data = items.get(i);
        vh.txtDrawerText.setText(data.getName());
        vh.llPriceListRawItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PriceDetailActivity.class);
                intent.putExtra("title", data.getName());
                intent.putExtra("id", data.getPriceMasterID());
                context.startActivity(intent);
            }
        });
    }

    public GetPriceListResponse.Data get(int position) {
        return items.get(position);
    }

    public void addAll(List<GetPriceListResponse.Data> getPartyResponses) {

        items.addAll(getPartyResponses);
        notifyDataSetChanged();
    }

    public void add(GetPriceListResponse.Data data) {
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

        @BindView(R.id.llPriceListRawItem)
        LinearLayout llPriceListRawItem;
        @BindView(R.id.txtDrawerText)
        DTextView txtDrawerText;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
