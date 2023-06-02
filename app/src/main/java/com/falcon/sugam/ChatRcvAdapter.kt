package com.falcon.sugam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatRcvAdapter(private val arrayListData: ArrayList<String>) : RecyclerView.Adapter<ChatRcvAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textMessageUser1: TextView = itemView.findViewById(R.id.user1text)
        val textMessageUser2: TextView = itemView.findViewById(R.id.user2text)
        val ll1: LinearLayout = itemView.findViewById(R.id.ll1)
        val ll2: LinearLayout = itemView.findViewById(R.id.ll2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayListData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position / 2 == 0) {
            holder.textMessageUser1.text = arrayListData[position]
            holder.ll1.visibility = View.VISIBLE
            holder.ll2.visibility = View.GONE

        } else {
            holder.textMessageUser2.text = arrayListData[position]
            holder.ll2.visibility = View.VISIBLE
            holder.ll1.visibility = View.GONE
        }

    }


}
