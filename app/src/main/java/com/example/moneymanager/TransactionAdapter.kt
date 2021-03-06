package com.example.moneymanager


import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class TransactionAdapter(var mContext: Context, var mCursor: Cursor) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val amountText:     TextView
        val dateText:       TextView
        val categorieText:  TextView

        init {
            amountText      = view.findViewById(R.id.tvAmount)
            dateText        = view.findViewById(R.id.tvDate)
            categorieText   = view.findViewById(R.id.tvCategorie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view: View = inflater.inflate(R.layout.transaction_list_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        if (!mCursor.moveToPosition(position)) {
            return
        }

        var amount: String      = mCursor.getString(mCursor.getColumnIndex(TransactionList.TransactionEntry.COLUMN_AMOUNT))
        val date: String        = mCursor.getString(mCursor.getColumnIndex(TransactionList.TransactionEntry.COLUMN_CREATEDAT))
        val categorie: String   = mCursor.getString(mCursor.getColumnIndex(TransactionList.TransactionEntry.COLUMN_CATEGORIE))
        val type: String        = mCursor.getString(mCursor.getColumnIndex(TransactionList.TransactionEntry.COLUMN_TYPE))
        val _id: Float          = mCursor.getFloat(mCursor.getColumnIndex(TransactionList.TransactionEntry._ID))

        amount = manipulateAmountView(holder,amount, type)
        holder.amountText.text  = amount + "€"
        holder.dateText.text    = date.toString()
        holder.categorieText.text = categorie
    }

    override fun getItemCount(): Int {
        return mCursor.getCount()
    }

    fun swapCursor(newCursor: Cursor) {
        if (mCursor != null) {
            mCursor.close()
        }
        mCursor = newCursor
        if (newCursor != null) {
            notifyDataSetChanged()
        }
    }

    private fun manipulateAmountView(holder:TransactionViewHolder, aAmount:String, type:String):String{
        var amount=aAmount
        if (type=="e"){
            holder.amountText.setTextColor(ContextCompat.getColor(mContext,R.color.expense))
            amount = "- "+amount
        }else if (type=="ne"){
            holder.amountText.setTextColor(ContextCompat.getColor(mContext,R.color.mainYellow))
            amount = "- "+amount
        }else if (type=="nr"){
            holder.amountText.setTextColor(ContextCompat.getColor(mContext,R.color.mainYellow))
            amount = "+ "+amount
        }else{
            amount = "+ "+amount
            holder.amountText.setTextColor(ContextCompat.getColor(mContext,R.color.revenue))
        }
        return amount
    }

}