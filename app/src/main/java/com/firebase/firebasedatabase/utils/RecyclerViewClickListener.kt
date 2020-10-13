package com.firebase.firebasedatabase.utils

import android.view.View
import com.firebase.firebasedatabase.data.Auther

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view: View, auther:Auther)
}