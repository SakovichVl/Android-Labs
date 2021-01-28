package com.example.lab2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateViewModel : ViewModel() {
    val name = MutableLiveData("Название")
    val preparationTime = MutableLiveData(10)
    val workingTime = MutableLiveData(50)
    val restingTime = MutableLiveData(60)
    val numOfRestSets = MutableLiveData(5)
    val numOfCycles = MutableLiveData(1)
    val numOfSets = MutableLiveData(1)
    val colorDif = MutableLiveData(-16777215)
    fun setName(name: String) {
        name.setValue(name)
    }

    fun setColorDif(colorDif: Int) {
        colorDif.setValue(colorDif)
    }

    fun setPreparingTime(preparingTime: Int) {
        preparationTime.value = preparingTime
    }

    fun setWorkingTime(workingTime: Int) {
        workingTime.setValue(workingTime)
    }

    fun setRestingTime(restingTime: Int) {
        restingTime.setValue(restingTime)
    }

    fun setNumOfRestSets(numOfRestSets: Int) {
        numOfRestSets.setValue(numOfRestSets)
    }

    fun setNumOfCycles(numOfCycles: Int) {
        numOfCycles.setValue(numOfCycles)
    }

    fun setNumOfSets(numOfSetsSets: Int) {
        numOfSets.value = numOfSetsSets
    }

    fun preparationIncrement() {
        incrementation(preparationTime)
    }

    fun preparationDecrement() {
        decreasing(preparationTime, 0)
    }

    fun workTimeIncrement() {
        incrementation(workingTime)
    }

    fun workTimeDecrement() {
        decreasing(workingTime, 0)
    }

    fun restTimeIncrement() {
        incrementation(restingTime)
    }

    fun restTimeDecrement() {
        decreasing(restingTime, 0)
    }

    fun restSetsIncrement() {
        incrementation(numOfRestSets)
    }

    fun restSetsDecrement() {
        decreasing(numOfRestSets, 0)
    }

    fun cycleIncrement() {
        incrementation(numOfCycles)
    }

    fun cycleDecrement() {
        decreasing(numOfCycles, 0)
    }

    fun setsIncrement() {
        incrementation(numOfSets)
    }

    fun setsDecrement() {
        decreasing(numOfSets, 1)
    }

    fun incrementation(data: MutableLiveData<Int?>) {
        assert(data.value != null)
        data.value = data.value!! + 1
    }

    fun decreasing(data: MutableLiveData<Int?>, condition: Int) {
        assert(data.value != null)
        if (data.value != condition) {
            data.value = data.value!! - 1
        }
    }
}