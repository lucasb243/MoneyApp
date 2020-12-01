package com.example.moneymanager


import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TransactionAdapter(var mContext: Context, var mCursor: Cursor) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val amountText:     TextView
        val dateText:       TextView
        val categorieText:  TextView

        init {
            amountText      = view.findViewById(com.example.moneymanager.R.id.tvAmount)
            dateText        = view.findViewById(com.example.moneymanager.R.id.tvDate)
            categorieText   = view.findViewById(com.example.moneymanager.R.id.tvCategorie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view: View = inflater.inflate(viewType, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        if (!mCursor.moveToPosition(position)) {
            return
        }
        val amount: String      = mCursor.getString(mCursor.getColumnIndex(TransactionList.TransactionEntry.COLUMN_AMOUNT))
        val date: String        = mCursor.getString(mCursor.getColumnIndex(TransactionList.TransactionEntry.COLUMN_CREATEDAT))
        val categorie: String   = mCursor.getString(mCursor.getColumnIndex(TransactionList.TransactionEntry.COLUMN_CATEGORIE))
        holder.amountText.text  = amount
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

}