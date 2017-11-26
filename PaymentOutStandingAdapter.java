package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.listener.CallListener;
import com.oozeetech.bizdesk.models.payment.GetPaymentOutstandingResponse;
import com.oozeetech.bizdesk.ui.PaymentOutstandingDetailActivity;
import com.oozeetech.bizdesk.utils.Constants;
import com.oozeetech.bizdesk.widget.DTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentOutStandingAdapter extends RecyclerView.Adapter<PaymentOutStandingAdapter.ViewHolder> {


    private Context context;
    private ArrayList<GetPaymentOutstandingResponse.Data.List> items = new ArrayList<>();
    private LayoutInflater inflater;
    private CallListener callListener;
    private String type;

    public PaymentOutStandingAdapter(Context context, CallListener callListener, String type) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.callListener = callListener;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = inflater.inflate(R.layout.brokerages_raw_item_temp, parent, false);
       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaymentOutstandingDetailActivity.class);
                intent.putExtra(Constants.TYPE, type);
                intent.putExtra(Constants.ID, items.getSize(viewType).getID());
                context.startActivity(intent);
            }
        });*/
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int i) {
        final GetPaymentOutstandingResponse.Data.List data = items.get(i);

        vh.txtCustomerName.setText(data.getCustomerName());
//        vh.imgDiamond.setColorFilter(ContextCompat.getColor(context, data.getBizType().equals("Rough") ? R.color.diamondOrange : R.color.diamondGreen));
//        vh.txtPolishedItem.setText(data.getBizType() + "\n" + data.getCrt() + " Crt");
        vh.txtBrokerageAmt.setText(data.getAmount());
//        vh.txtBrokerageReceipt.setText(data.getReceiptAmount());
        vh.txtBrokerageOutstanding.setText(data.getOutstandingAmount());
        vh.txtSaleDate.setText(data.getSaleDate());
        vh.txtDueDays.setText("Due Days : " + data.getDueDay());
        vh.txtPaymentDate.setText(data.getPaymentDate());

        vh.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callListener.onCallTapListener(i);
            }
        });
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaymentOutstandingDetailActivity.class);
                intent.putExtra(Constants.TYPE, type);
                intent.putExtra(Constants.ID, items.get(i).getID());
                context.startActivity(intent);
            }
        });
    }

    public GetPaymentOutstandingResponse.Data.List get(int position) {
        return items.get(position);
    }

    public void addAll(List<GetPaymentOutstandingResponse.Data.List> list) {

        items.addAll(list);
        notifyDataSetChanged();
    }

    public void add(GetPaymentOutstandingResponse.Data.List data) {
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
        @BindView(R.id.txtCustomerName)
        DTextView txtCustomerName;
        @BindView(R.id.btnCall)
        ImageView btnCall;
        @BindView(R.id.txtSaleDate)
        DTextView txtSaleDate;
        @BindView(R.id.txtDueDays)
        DTextView txtDueDays;
        @BindView(R.id.txtPaymentDate)
        DTextView txtPaymentDate;
        @BindView(R.id.txtBrokerageAmt)
        DTextView txtBrokerageAmt;
        @BindView(R.id.txtBrokerageOutstanding)
        DTextView txtBrokerageOutstanding;
        @BindView(R.id.activity_main)
        LinearLayout activityMain;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
