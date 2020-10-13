package com.firebase.firebasedatabase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.firebasedatabase.R
import com.firebase.firebasedatabase.data.Auther
import com.firebase.firebasedatabase.utils.RecyclerViewClickListener
import kotlinx.android.synthetic.main.auther_fragment.view.*
import kotlinx.android.synthetic.main.recycler_view_authors.view.*

class AuthersAdapter : RecyclerView.Adapter<AuthersAdapter.AutherViewModel>(){

    private var authers = mutableListOf<Auther>()
     var listener : RecyclerViewClickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    )=AutherViewModel (LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_authors,parent,false))


    override fun getItemCount() = authers.size

    override fun onBindViewHolder(holder: AuthersAdapter.AutherViewModel, position: Int) {
        holder.view.text_view_name.text = authers[position].name
        holder.view.button_edit.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, authers[position])
        }
        holder.view.button_delete.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, authers[position])

        }
        }

    fun setAuthers(authers:List<Auther>)
    {
            this.authers = authers as MutableList<Auther>
            notifyDataSetChanged()
    }

    fun addAuther(auther: Auther)
    {
        if(!authers.contains(auther))
        {
        authers.add(auther)
    }
        else{
            val index = authers.indexOf(auther)
            if(auther.isDeleted){
                authers.removeAt(index)
            }
            else{
                authers[index] = auther
            }

        }
        notifyDataSetChanged()

    }

    class AutherViewModel(val view: View) : RecyclerView.ViewHolder(view)

}