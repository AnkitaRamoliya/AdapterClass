package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.listener.MyStockListener;
import com.oozeetech.bizdesk.models.mystock.GetMyStockResponse;
import com.oozeetech.bizdesk.utils.Utils;
import com.oozeetech.bizdesk.widget.DButton;
import com.oozeetech.bizdesk.widget.DTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetMyStockAdapter extends RecyclerSwipeAdapter<GetMyStockAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<GetMyStockResponse.Data> items = new ArrayList<>();
    private ArrayList<GetMyStockResponse.Data> filterItems;
    private LayoutInflater inflater;
    private ValueFilter valueFilter;
    private MyStockListener myStockListener;
    private String text;

    public GetMyStockAdapter(Context context, MyStockListener myStockListener) {
        this.context = context;
        this.myStockListener = myStockListener;
        this.filterItems = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_stock_raw_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int i) {
        final GetMyStockResponse.Data data = items.get(i);
        vh.txtItemName.setText(data.getItemName());
        Utils.setDrawableTint(vh.txtShippingPer, context.getResources().getDrawable(R.drawable.ic_shipping_), context.getResources().getColor(R.color.diamondGreen));
        vh.txtBizType.setTextColor(ContextCompat.getColor(context, data.getBizTypeID() == 2 ? R.color.diamondOrange : R.color.diamondGreen));
        vh.txtBizType.setText(data.getBizType());
        vh.txtCrt.setText(data.getCrt() + " Crt");

        if (data.getBizTypeID() == 2) {
            vh.txtCut.setText(data.getCut() + " Cut");
            text = "Premium(%) : <font color = #0488d1>" + data.getPremiumPer() + "</font> ";
            vh.txtPremium.setText(Html.fromHtml(text));
            vh.txtTotalAmount.setText(data.getTotalAmt() + data.getCurrencySymbol());
            vh.txtPricePerCrt.setText(data.getPricePerCrt() + " " + data.getCurrencySymbol() + "/Crt");

            if (data.isIsShipping()) {
                vh.txtShippingPer.setVisibility(View.VISIBLE);
                vh.txtShippingPer.setText(data.getShipPer() + "%");
            } else {
                vh.txtShippingPer.setVisibility(View.GONE);
            }
        } else {
            if (data.isIsShipping()) {
                vh.txtCut.setVisibility(View.GONE);
                vh.txtShippingPer.setVisibility(View.VISIBLE);
                vh.txtTotalAmount.setVisibility(View.VISIBLE);
                vh.txtShippingPer.setText(data.getShipPer() + "%");
                vh.txtTotalAmount.setText(data.getTotalAmt() + data.getCurrencySymbol());
                vh.txtPricePerCrt.setText(data.getPricePerCrt() + " " + data.getCurrencySymbol() + "/Crt");
            } else {
                vh.txtCut.setVisibility(View.VISIBLE);
                vh.txtShippingPer.setVisibility(View.GONE);
                vh.txtTotalAmount.setVisibility(View.GONE);
                vh.txtCut.setText(data.getPricePerCrt() + " " + data.getCurrencySymbol() + "/Crt");
                vh.txtPricePerCrt.setText(data.getTotalAmt() + data.getCurrencySymbol());
                vh.txtPricePerCrt.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            vh.txtPremium.setVisibility(View.GONE);
        }
//        vh.txtTotalAmount.setText(data.getTotalAmt() + data.getCurrencySymbol());

        vh.swipeBizDetail.setShowMode(SwipeLayout.ShowMode.LayDown);
        vh.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(vh.swipeBizDetail);
                myStockListener.onDeleteClickListener(data.getMyStockID(), i);
                notifyDataSetChanged();
                mItemManger.closeAllItems();
            }
        });

        mItemManger.bindView(vh.itemView, i);

    }

    public void addAll(List<GetMyStockResponse.Data> getPartyResponses) {
        items.addAll(getPartyResponses);
        notifyDataSetChanged();
    }

    public void add(GetMyStockResponse.Data data) {
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

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeBizDetail;
    }

    public GetMyStockResponse.Data get(int position) {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        @BindView(R.id.btnDelete)
        DButton btnDelete;
        @BindView(R.id.swipeLayout)
        CardView swipeLayout;
        @BindView(R.id.txtItemName)
        DTextView txtItemName;
        @BindView(R.id.txtBizType)
        DTextView txtBizType;
        @BindView(R.id.txtCrt)
        DTextView txtCrt;
        @BindView(R.id.txtCut)
        DTextView txtCut;
        @BindView(R.id.txtPremium)
        DTextView txtPremium;
        @BindView(R.id.txtShippingPer)
        DTextView txtShippingPer;
        @BindView(R.id.txtPricePerCrt)
        DTextView txtPricePerCrt;
        @BindView(R.id.txtTotalAmount)
        DTextView txtTotalAmount;
        @BindView(R.id.swipeBizDetail)
        SwipeLayout swipeBizDetail;
        @BindView(R.id.activity_main)
        LinearLayout activityMain;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence != null && charSequence.length() > 0) {
                ArrayList<GetMyStockResponse.Data> filterList = new ArrayList<>();
                for (int i = 0; i < filterItems.size(); i++) {
                    if ((filterItems.get(i).getItemName().toUpperCase())
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

            items = (ArrayList<GetMyStockResponse.Data>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
