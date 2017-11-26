package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.listener.DeletePaymentListener;
import com.oozeetech.bizdesk.models.payment.GetPaymentOutstandingDetailResponse;
import com.oozeetech.bizdesk.widget.DTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddOutstandingReceiptAdapter extends RecyclerView.Adapter<AddOutstandingReceiptAdapter.ViewHolder> {


    private Context context;
    private ArrayList<GetPaymentOutstandingDetailResponse.Data.ReceivePayment> items = new ArrayList<>();
    private LayoutInflater inflater;
    private DeletePaymentListener deletePaymentListener;

    public AddOutstandingReceiptAdapter(Context context, DeletePaymentListener deletePaymentListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.deletePaymentListener = deletePaymentListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.add_outstanding_receipt_raw_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int i) {
        final GetPaymentOutstandingDetailResponse.Data.ReceivePayment payment = items.get(i);

        vh.txtAmount.setText(payment.getAmount());
        vh.txtDate.setText(payment.getPaymentDate());
        vh.btndeleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePaymentListener.onDeleteTapListener(payment.getPaymentID());
            }
        });
    }


    public GetPaymentOutstandingDetailResponse.Data.ReceivePayment get(int position) {
        return items.get(position);
    }

    public void addAll(List<GetPaymentOutstandingDetailResponse.Data.ReceivePayment> list) {

        items.addAll(list);
        notifyDataSetChanged();
    }

    public void add(GetPaymentOutstandingDetailResponse.Data.ReceivePayment data) {
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
        @BindView(R.id.txtDate)
        DTextView txtDate;
        @BindView(R.id.txtAmount)
        DTextView txtAmount;
        @BindView(R.id.btndeleteRecord)
        ImageView btndeleteRecord;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
