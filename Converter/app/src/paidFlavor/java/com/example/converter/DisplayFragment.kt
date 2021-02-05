package com.example.converter

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.converter.Fragment.DFragment
import com.example.converter.ViewModel.ConverterViewModel

class DisplayFragment : DFragment() {
    override lateinit var converterViewModel: ConverterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        converterViewModel = ViewModelProviders.of(requireActivity()).get(ConverterViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_display, container, false)
        standardFunctionality(inflater, container, savedInstanceState, layout)
        val clipboard = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        layout.findViewById<View>(R.id.btnChange).setOnClickListener { item: View? -> converterViewModel!!.change() }
        layout.findViewById<View>(R.id.btnInputC).setOnClickListener { item: View? -> converterViewModel!!.save(1, clipboard) }
        layout.findViewById<View>(R.id.btnOutputC).setOnClickListener { item: View? -> converterViewModel!!.save(2, clipboard) }
        return layout
    }
}