package com.firebase.firebasedatabase.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.firebase.firebasedatabase.R
import com.firebase.firebasedatabase.adapter.AuthersAdapter
import com.firebase.firebasedatabase.data.Auther
import com.firebase.firebasedatabase.utils.RecyclerViewClickListener
import kotlinx.android.synthetic.main.auther_fragment.*

class AuthorsFragment :Fragment() , RecyclerViewClickListener {

    private lateinit var viewModel: AuthersViewModel
    private val adapter = AuthersAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AuthersViewModel::class.java)
        return inflater.inflate(R.layout.auther_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.listener = this
        recycler_view_authors.adapter = adapter
        viewModel.fetchAuther()
        viewModel.getRealtimeUpdates()
        viewModel.auther.observe(viewLifecycleOwner, Observer {
            adapter.setAuthers(it)
        })
        viewModel.singleauther.observe(viewLifecycleOwner , Observer{
            adapter.addAuther(it)
        })


        button_add.setOnClickListener {
            AddAuthorDialogFragment().show(childFragmentManager,"")
        }
    }

    override fun onRecyclerViewItemClicked(view: View, auther: Auther) {
       when (view.id)
       {
           R.id.button_edit ->{
                EditAutherDialogFragment(auther).show(childFragmentManager,"")
           }
           R.id.button_delete ->{

               AlertDialog.Builder(requireContext()).also {
                it.setTitle("do you want to delete auther name")
                   it.setPositiveButton("ok"){
                       dialog, which ->
                       viewModel.deleteAuther(auther)
                   }
               }.create().show()
           }

       }
    }
}