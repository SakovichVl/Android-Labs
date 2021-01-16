package com.example.converter.ViewModel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.converter.Converter
import java.util.*

class ConverterViewModel(application: Application) : AndroidViewModel(application) {
    private val inputET = MutableLiveData<String?>()
    private val outputET = MutableLiveData<String?>()
    private val inputS = MutableLiveData<String>()
    private val outputS = MutableLiveData<String>()
    private val enumName = MutableLiveData<String>()
    fun setEnumName(name: String) {
        enumName.value = name
    }

    fun addValue(str: String) {
        inputET.value = inputET.value.toString() + str
    }

    fun addDot() {
        if (inputET.value!!.indexOf(".") < 1 && Objects.requireNonNull(inputET.value)?.length!! > 0) {
            inputET.value = inputET.value.toString() + "."
        }
    }

    fun clearData() {
        inputET.value = ""
        outputET.value = ""
    }

    fun getInputET(): LiveData<String?> {
        return inputET
    }

    fun getOutputET(): LiveData<String?> {
        return outputET
    }

    fun setInputS(item: String) {
        inputS.value = item
    }

    fun setOutputS(item: String) {
        outputS.value = item
    }

    fun convert() {
        if (inputET.value != "") outputET.value = Converter.convert(enumName.value, inputET.value, inputS.value, outputS.value)
    }

    fun change() {
        val temp = inputET.value
        inputET.value = outputET.value
        outputET.value = temp
    }

    fun save(field: Int, clipboardManager: ClipboardManager) {
        when (field) {
            1 -> {
                val clipData = ClipData.newPlainText("text", inputET.value)
                clipboardManager.setPrimaryClip(clipData)
            }
            2 -> {
                val clipData = ClipData.newPlainText("text", outputET.value)
                clipboardManager.setPrimaryClip(clipData)
            }
        }
        val toast = Toast.makeText(getApplication(), "Save in buffer", Toast.LENGTH_SHORT)
        toast.show()
    }

    init {
        inputET.value = ""
        outputET.value = ""
    }
}