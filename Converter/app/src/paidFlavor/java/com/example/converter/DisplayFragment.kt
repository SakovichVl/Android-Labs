package com.example.converter

import android.content.ClipboardManager

class DisplayFragment : Fragment() {
    var converterViewModel: ConverterViewModel? = null
    var inputET: EditText? = null
    var outputET: EditText? = null
    var inputS: Spinner? = null
    var outputS: Spinner? = null
    @Override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        converterViewModel = ViewModelProviders.of(requireActivity()).get(ConverterViewModel::class.java)
    }

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val layout: View = inflater.inflate(R.layout.fragment_display, container, false)
        inputET = layout.findViewById(R.id.inputET)
        outputET = layout.findViewById(R.id.outputET)
        inputS = layout.findViewById(R.id.inputS)
        outputS = layout.findViewById(R.id.outputS)
        converterViewModel.getInputET().observe(requireActivity()) { value -> inputET.setText(value) }
        converterViewModel.getOutputET().observe(requireActivity()) { value -> outputET.setText(value) }
        inputS.setOnItemSelectedListener(object : OnItemSelectedListener() {
            fun onItemSelected(parent: AdapterView<*>?,
                               itemSelected: View?, selectedItemPosition: Int, selectedId: Long) {
                converterViewModel.setInputS(inputS.getSelectedItem().toString())
            }

            fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        outputS.setOnItemSelectedListener(object : OnItemSelectedListener() {
            fun onItemSelected(parent: AdapterView<*>?,
                               itemSelected: View?, selectedItemPosition: Int, selectedId: Long) {
                converterViewModel.setOutputS(outputS.getSelectedItem().toString())
            }

            fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        val clipboard: ClipboardManager = getContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        layout.findViewById(R.id.btnConvert).setOnClickListener { item -> converterViewModel.convert() }
        layout.findViewById(R.id.btnChange).setOnClickListener { item -> converterViewModel.change() }
        layout.findViewById(R.id.btnInputC).setOnClickListener { item -> converterViewModel.save(1, clipboard) }
        layout.findViewById(R.id.btnOutputC).setOnClickListener { item -> converterViewModel.save(2, clipboard) }
        return layout
    }
}