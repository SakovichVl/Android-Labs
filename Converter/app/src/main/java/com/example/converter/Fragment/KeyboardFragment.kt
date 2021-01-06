package com.example.converter.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.converter.R
import com.example.converter.ViewModel.ConverterViewModel

class KeyboardFragment : Fragment() {
    var converterViewModel: ConverterViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        converterViewModel = ViewModelProviders.of(requireActivity()).get(ConverterViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_keyboard, container, false)
        layout.findViewById<View>(R.id.btn0).setOnClickListener { item: View? -> converterViewModel!!.addValue("0") }
        layout.findViewById<View>(R.id.btn1).setOnClickListener { item: View? -> converterViewModel!!.addValue("1") }
        layout.findViewById<View>(R.id.btn2).setOnClickListener { item: View? -> converterViewModel!!.addValue("2") }
        layout.findViewById<View>(R.id.btn3).setOnClickListener { item: View? -> converterViewModel!!.addValue("3") }
        layout.findViewById<View>(R.id.btn4).setOnClickListener { item: View? -> converterViewModel!!.addValue("4") }
        layout.findViewById<View>(R.id.btn5).setOnClickListener { item: View? -> converterViewModel!!.addValue("5") }
        layout.findViewById<View>(R.id.btn6).setOnClickListener { item: View? -> converterViewModel!!.addValue("6") }
        layout.findViewById<View>(R.id.btn7).setOnClickListener { item: View? -> converterViewModel!!.addValue("7") }
        layout.findViewById<View>(R.id.btn8).setOnClickListener { item: View? -> converterViewModel!!.addValue("8") }
        layout.findViewById<View>(R.id.btn9).setOnClickListener { item: View? -> converterViewModel!!.addValue("9") }
        layout.findViewById<View>(R.id.btnDot).setOnClickListener { item: View? -> converterViewModel!!.addDot() }
        layout.findViewById<View>(R.id.btnClear).setOnClickListener { item: View? -> converterViewModel!!.clearData() }
        return layout
    }
}