package com.example.lab3am

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class DisplayFragment : Fragment() {
    lateinit var converterViewModel: ConverterViewModel
    lateinit var inputET: EditText
    lateinit var outputET: EditText
    lateinit var inputS: Spinner
    lateinit var outputS: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        converterViewModel = ViewModelProviders.of(requireActivity()).get(ConverterViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout: View = inflater.inflate(R.layout.fragment_display, container, false)
        inputET = layout.findViewById<EditText>(R.id.inputET)
        outputET = layout.findViewById<EditText>(R.id.outputET)
        inputS = layout.findViewById<Spinner>(R.id.inputS)
        outputS = layout.findViewById<Spinner>(R.id.outputS)
        converterViewModel.getInputET().observe(requireActivity()) { value -> inputET.text =value }
        converterViewModel.getOutputET().observe(requireActivity()) { value -> outputET.text =value }
        inputS.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        itemSelected: View, selectedItemPosition: Int, selectedId: Long) {
                converterViewModel.setInputS(inputS.getSelectedItem().toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        outputS.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        itemSelected: View, selectedItemPosition: Int, selectedId: Long) {
                converterViewModel.setOutputS(outputS.getSelectedItem().toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        layout.findViewById<View>(R.id.btnConvert).setOnClickListener { item: View? -> converterViewModel.convert() }
        return layout
    }
}

private fun EditText.setText(value: Any) {

}
