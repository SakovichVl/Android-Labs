package com.example.converter.ViewModel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.converter.Converter
import java.util.Objects

class ConverterViewModel(@NonNull application: Application?) : AndroidViewModel(application) {
    private val inputET: MutableLiveData<String> = MutableLiveData()
    private val outputET: MutableLiveData<String> = MutableLiveData()
    private val inputS: MutableLiveData<String> = MutableLiveData()
    private val outputS: MutableLiveData<String> = MutableLiveData()
    private val enumName: MutableLiveData<String> = MutableLiveData()
    fun setEnumName(name: String?) {
        enumName.setValue(name)
    }

    fun addValue(str: String) {
        inputET.setValue(inputET.getValue() + str)
    }

    fun addDot() {
        if (inputET.getValue().indexOf(".") < 1 && Objects.requireNonNull(inputET.getValue()).length() > 0) {
            inputET.setValue(inputET.getValue().toString() + ".")
        }
    }

    fun clearData() {
        inputET.setValue("")
        outputET.setValue("")
    }

    fun getInputET(): LiveData<String> {
        return inputET
    }

    fun getOutputET(): LiveData<String> {
        return outputET
    }

    fun setInputS(item: String?) {
        inputS.setValue(item)
    }

    fun setOutputS(item: String?) {
        outputS.setValue(item)
    }

    fun convert() {
        if (!inputET.getValue().equals("")) outputET.setValue(Converter.convert(enumName.getValue(), inputET.getValue(), inputS.getValue(), outputS.getValue()))
    }

    fun change() {
        val temp: String = inputET.getValue()
        inputET.setValue(outputET.getValue())
        outputET.setValue(temp)
    }

    fun save(field: Int, clipboardManager: ClipboardManager) {
        when (field) {
            1 -> {
                val clipData: ClipData = ClipData.newPlainText("text", inputET.getValue())
                clipboardManager.setPrimaryClip(clipData)
            }
            2 -> {
                clipData = ClipData.newPlainText("text", outputET.getValue())
                clipboardManager.setPrimaryClip(clipData)
            }
        }
        val toast: Toast = Toast.makeText(getApplication(), "Save in buffer", Toast.LENGTH_SHORT)
        toast.show()
    }

    init {
        inputET.setValue("")
        outputET.setValue("")
    }
}