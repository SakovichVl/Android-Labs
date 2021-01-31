package com.example.timer.Model

class Timer {
    var id: Long = 0
    var name: String
    var duration: Int

    constructor(name: String, duration: Int) {
        this.name = name
        this.duration = duration
    }

    constructor(id: Long, name: String, duration: Int) {
        this.id = id
        this.name = name
        this.duration = duration
    }
}