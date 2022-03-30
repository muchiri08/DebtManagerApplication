package com.example.debtmanagerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debtmanagerapp.R;
import com.example.debtmanagerapp.model.DebtorModel;

import java.util.ArrayList;

public class DebtorRecyViewAdapter extends RecyclerView.Adapter<DebtorRecyViewAdapter.viewHolder> {
    private static final String TAG = "Inside RecViewAdapter";
    private ArrayList<DebtorModel> debtors;
    private ItemClickInterface itemClickInterface;

    public DebtorRecyViewAdapter(ArrayList<DebtorModel> debtors, ItemClickInterface itemClickInterface) {
        this.debtors = debtors;
        this.itemClickInterface = itemClickInterface;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debtor_list_item, parent,false);
        return new viewHolder(view, itemClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.getLastName().setText(debtors.get(position).getLastName());
        holder.firstName.setText(debtors.get(position).getFirstName());
        holder.amount.setText(String.valueOf(debtors.get(position).getAmount()));

        //paid fragment
        holder.lastNamePaid.setText(debtors.get(position).getLastName());
        holder.firstNamePaid.setText(debtors.get(position).getFirstName());
        holder.amountPaid.setText(String.valueOf(debtors.get(position).getAmount()));

        holder.buttonDelete.setOnClickListener(v ->{
            debtors.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        });


    }

    @Override
    public int getItemCount() {
        return debtors.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView lastName, firstName, amount;
        private CardView parent;
        private ItemClickInterface itemClickInterface;

        //paid fragment
        private TextView lastNamePaid, firstNamePaid, amountPaid;
        private Button buttonDelete;

        public viewHolder(@NonNull View itemView, ItemClickInterface itemClickInterface) {
            super(itemView);
            lastName = itemView.findViewById(R.id.tv_last_name);
            firstName = itemView.findViewById(R.id.tv_first_name);
            amount = itemView.findViewById(R.id.tv_amount);
            parent = itemView.findViewById(R.id.parent);
            this.itemClickInterface = itemClickInterface;

            //paid fragment
            lastNamePaid = itemView.findViewById(R.id.tv_last_name_paid);
            firstNamePaid = itemView.findViewById(R.id.tv_first_name_paid);
            amountPaid = itemView.findViewById(R.id.tv_amount_paid);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);

            itemView.setOnClickListener(this);

        }
        public TextView getLastName(){return lastName;}

        @Override
        public void onClick(View v) {
            itemClickInterface.onItemClicked(getAbsoluteAdapterPosition());
        }
    }

}
