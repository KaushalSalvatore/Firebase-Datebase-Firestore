package com.firebase.firebasedatabase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.firebase.firebasedatabase.R
import com.firebase.firebasedatabase.data.Auther
import kotlinx.android.synthetic.main.dialog_add_auther_fragment.*

class EditAutherDialogFragment(
    private val auther: Auther
) : DialogFragment() {
    private lateinit var viewModel: AuthersViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AuthersViewModel::class.java)
        return inflater.inflate(R.layout.dialog_edit_auther_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        edit_text_name.setText(auther.name)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null) {
                getString(R.string.auther_added)
            } else {
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            dismiss()
        })
        button_addauther.setOnClickListener {
            val name = edit_text_name.text.toString().trim()

            if (name.isEmpty()) {
                input_layout_name.error = getString(R.string.error_field_required)
                return@setOnClickListener
            } else {

                auther.name = name
                viewModel.updateAuther(auther)
            }
        }
    }


}