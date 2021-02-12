package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.InputStream

class PlayActivity : AppCompatActivity() {
    var user = FirebaseAuth.getInstance().currentUser
    lateinit var editTextWord : EditText
    lateinit var text : TextView
    var playerName = user?.displayName
    var lobbyName = ""
    var myRole = ""
    var secondRole =""
    var game = false
    var usedWords  = mutableListOf("")
    lateinit var database : FirebaseDatabase
    lateinit var messageRef : DatabaseReference
    lateinit var btnSendWord: Button
    lateinit var WordFile : File
    lateinit var btnSurrender:Button
    var globalLastWord =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        database = FirebaseDatabase.getInstance()
        downloadFile()
        setupUI()
        var extras = intent.extras
        if(extras!= null){
            lobbyName = extras.getString("roomName").toString()
            if(lobbyName.equals(playerName)){
                myRole = "host"
                secondRole = "guest"
            }
            else{
                myRole="guest"
                secondRole = "host"
            }
        }
        messageRef = database.getReference("rooms/$lobbyName/newword")
        messageRef.setValue("hello")
        Toast.makeText(applicationContext,myRole,Toast.LENGTH_SHORT).show()
        addWordEventListener()
        addTurnEventListener()
    }

    fun downloadFile() {
        var storageReference = FirebaseStorage.getInstance().reference
        val seriesRef = storageReference.child("words.txt")
        val localFile = File.createTempFile("MyFile", "txt")
        seriesRef.getFile(localFile).addOnSuccessListener { taskSnapshot ->
            WordFile = localFile
        }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            }
            .addOnProgressListener { taskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                val intProgress = progress.toInt()
                if (intProgress==100){
                   Toast.makeText(this,"File is here", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun checkWord(word : String): Boolean {
        WordFile
        val inputStream: InputStream = File(WordFile.path).inputStream()
        val lineList = mutableListOf<String>()
        inputStream.bufferedReader().forEachLine { lineList.add(it) }
        return lineList.contains(word)
    }

    fun setupUI(){
        text = findViewById(R.id.textLastWord)
        editTextWord = findViewById(R.id.editTextWord)
        btnSurrender = findViewById(R.id.btnSurrender)
        btnSendWord = findViewById(R.id.btnSendWord)
        btnSendWord.setOnClickListener {
            sendWord(editTextWord.text.toString())
        }
        btnSurrender.setOnClickListener {
            surrender()
        }
    }

    fun sendWord(word : String){
        if(globalLastWord != "") {
            if (checkWord(word) && !usedWords.contains(word) && globalLastWord.takeLast(1) == word[0].toString()) {
                usedWords.add(word)
                messageRef = database.getReference("rooms/$lobbyName/newword")
                messageRef.setValue(word)
                Toast.makeText(applicationContext, "EXISTS", Toast.LENGTH_SHORT).show()
                messageRef = database.getReference("rooms/$lobbyName/turn")
                messageRef.setValue(myRole)
            }
            else {
                Toast.makeText(
                    applicationContext,
                    globalLastWord,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        else{
            if (checkWord(word) && !usedWords.contains(word)) {
                usedWords.add(word)
                messageRef = database.getReference("rooms/$lobbyName/newword")
                messageRef.setValue(word)
                Toast.makeText(applicationContext, "EXISTS", Toast.LENGTH_SHORT).show()
                messageRef = database.getReference("rooms/$lobbyName/turn")
                messageRef.setValue(myRole)
            } else {
                Toast.makeText(
                    applicationContext,
                    "DOESN'T EXIST / Already used / Words don't match",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    fun surrender(){
        messageRef = database.getReference("rooms/$lobbyName/surrender")
        messageRef.setValue(myRole)
        val intent = Intent(this, ServersActivity::class.java)
        startActivityForResult(intent, 1)
    }

    fun setLastWord(word:String){
        text.text = "Last word: $word"
        globalLastWord = word
    }

    fun addWordEventListener(){
        var messageWordRef = database.getReference("rooms/$lobbyName/newword")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
              var newword = dataSnapshot.value.toString()
                setLastWord(newword)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText( applicationContext,"Fail in event listener", Toast.LENGTH_SHORT).show()
            }
        }
        messageWordRef.addValueEventListener(postListener)
    }

    fun addTurnEventListener(){
        var messageWordRef = database.getReference("rooms/$lobbyName/turn")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var newword = dataSnapshot.value.toString()
                btnSendWord.isEnabled = newword != myRole
                //addSurrenderEventListener()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText( applicationContext,"Fail in event listener", Toast.LENGTH_SHORT).show()
            }
        }
        messageWordRef.addValueEventListener(postListener)
    }

    fun addSurrenderEventListener(){
        var messageWordRef = database.getReference("rooms/$lobbyName/surrender")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var newword = dataSnapshot.value.toString()
                if(newword == myRole){
                    Toast.makeText(applicationContext,"Your lose",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext,"Your win",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText( applicationContext,"Fail in event listener", Toast.LENGTH_SHORT).show()
            }
        }
        messageWordRef.addValueEventListener(postListener)
    }
}