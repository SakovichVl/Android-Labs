package com.example.converter.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.converter.R
import com.example.converter.ViewModel.ConverterViewModel

open class DFragment : Fragment() {
    open lateinit var converterViewModel: ConverterViewModel
    lateinit var inputET: EditText
    lateinit var outputET: EditText
    lateinit var inputS: Spinner
    lateinit var outputS: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        converterViewModel = ViewModelProviders.of(requireActivity()).get(ConverterViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_display, container, false)
        standardFunctionality(inflater, container, savedInstanceState, layout)
        return layout
    }

    protected fun standardFunctionality(inflater: LayoutInflater?, container: ViewGroup?,
                                        savedInstanceState: Bundle?, layout: View) {
        inputET = layout.findViewById(R.id.inputET)
        outputET = layout.findViewById(R.id.outputET)
        inputS = layout.findViewById(R.id.inputS)
        outputS = layout.findViewById(R.id.outputS)
        converterViewModel!!.inputET.observe(requireActivity(), { value: String? -> inputET.setText(value) })
        converterViewModel!!.outputET.observe(requireActivity(), { value: String? -> outputET.setText(value) })
        inputS.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        itemSelected: View, selectedItemPosition: Int, selectedId: Long) {
                converterViewModel!!.setInputS(inputS.getSelectedItem().toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        outputS.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        itemSelected: View, selectedItemPosition: Int, selectedId: Long) {
                converterViewModel!!.setOutputS(outputS.getSelectedItem().toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        layout.findViewById<View>(R.id.btnConvert).setOnClickListener { item: View? -> converterViewModel!!.convert() }
    }
}