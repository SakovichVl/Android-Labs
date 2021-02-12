package com.example.lab3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ServersActivity : AppCompatActivity() {
    lateinit var listViewOfLobbies : ListView
    lateinit var btnCreateLobby : Button
    var lobbyList = mutableListOf<String>()
    lateinit var lobbyName : String
    var user = FirebaseAuth.getInstance().currentUser
    var playerName = user?.displayName
    lateinit var database : FirebaseDatabase
    lateinit var lobbyRef : DatabaseReference
    lateinit var lobbiesRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servers)
        database = FirebaseDatabase.getInstance()
        lobbyName = playerName.toString()
        setupUI()
        addLobbiesEventListener()
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, ServersActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    fun setupUI(){
        listViewOfLobbies = findViewById(R.id.lobbyListView)
        btnCreateLobby = findViewById(R.id.createLobby)
        lobbyList = mutableListOf<String>()
        btnCreateLobby.setOnClickListener {
            btnCreateLobby.isEnabled = false
            lobbyName = playerName.toString()
            lobbyRef = database.getReference("rooms/$lobbyName/player1")
            addLobbyEventListener()
            lobbyRef.setValue(playerName)
        }
        listViewOfLobbies.setOnItemClickListener { parent, view, position, id ->
            lobbyName = lobbyList.get(position)
            lobbyRef = database.getReference("room/$lobbyName/player2")
            addLobbyEventListener()
            lobbyRef.setValue(playerName)
        }
    }

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.action_settings) {
            val intent = Intent(this, AccountActivity::class.java)
            intent.putExtra("roomName", item.title)
            startActivityForResult(intent, 1)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addLobbyEventListener(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                btnCreateLobby.isEnabled = true
                var intent = Intent(applicationContext, PlayActivity::class.java)
                intent.putExtra("roomName", lobbyName)
                startActivity(intent)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                btnCreateLobby.text = "CREATE ROOM"
                btnCreateLobby.isEnabled = true
                Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show()
            }
        }
        lobbyRef.addValueEventListener(postListener)
    }

    private fun addLobbiesEventListener(){
        lobbiesRef = database.getReference("rooms")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                lobbyList.clear()
                var lobbies = dataSnapshot.children//tut
                for(snapshot in lobbies){
                    lobbyList.add(snapshot.key.toString())
                    var adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, lobbyList)//tut
                    listViewOfLobbies.adapter = adapter
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Error lobbies!", Toast.LENGTH_SHORT).show()
            }
        }
        lobbiesRef.addValueEventListener(postListener)
    }
}