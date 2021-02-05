package com.example.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import com.example.converter.Fragment.DFragment
import com.example.converter.ViewModel.ConverterViewModel

class DisplayFragment : DFragment() {
    var converterViewModel: ConverterViewModel? = null

    @Override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        converterViewModel = ViewModelProviders.of(requireActivity()).get(ConverterViewModel::class.java)
    }

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val layout: View = inflater.inflate(R.layout.fragment_display, container, false)
        standardFunctionality(inflater, container, savedInstanceState, layout)
        return layout
    }
}