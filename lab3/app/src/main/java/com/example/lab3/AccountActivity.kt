package com.example.lab3

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.File
import java.io.IOException

class AccountActivity : AppCompatActivity() {
    private val TAG = "StorageActivity"
    private val CHOOSING_IMAGE_REQUEST = 1234
    lateinit var btnChoosefile: Button
    lateinit var btnUploadfile: Button
    lateinit var btnSave: Button
    lateinit var fileURI: Uri
    lateinit var btnGravatar: Button
    lateinit var myBitmap: Bitmap
    lateinit  var editNickname: EditText
    var currentUser = FirebaseAuth.getInstance().currentUser
    lateinit var imageView: ImageView
    private var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        storageReference = FirebaseStorage.getInstance().reference.child("images")
        setupUI()
    }

    fun setupUI(){
        imageView = findViewById(R.id.imgFile)
        editNickname = findViewById(R.id.editName)
        editNickname.setText(currentUser?.displayName)
        btnChoosefile = findViewById(R.id.btn_choose_file)
        btnUploadfile = findViewById(R.id.btn_upload_file)
        btnSave = findViewById(R.id.setup_profile)
        btnGravatar= findViewById(R.id.gravatarButton)
        downloadFile()

        btnChoosefile.setOnClickListener {
            showChoosingFile()
        }

        btnSave.setOnClickListener {
            var nick = editNickname.text.toString()
            if(nick == ""){
                Toast.makeText(this, "Write your name!", Toast.LENGTH_SHORT).show()
            }
            else{
                val user = FirebaseAuth.getInstance().currentUser

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(nick).build()

                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this,"Profile updated!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this,"Error in profile update!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        btnGravatar.setOnClickListener {
            val hash = MyUtility.algo_MD5(Firebase.auth.currentUser?.email!!)
            Toast.makeText(this, Firebase.auth.currentUser?.email!!,Toast.LENGTH_SHORT).show()
            val gravatarUrl = "https://s.gravatar.com/avatar/$hash?s=80"
            Picasso.with(applicationContext)
                .load(gravatarUrl)
                .into(imageView)
        }

        btnUploadfile.setOnClickListener {
            uploadFile()
        }
    }

    private fun uploadFile() {
        val fileName = currentUser?.displayName

        if (!validateInputFileName(fileName)) {
            return
        }

        val fileRef = storageReference!!.child(fileName + "." + getFileExtension(fileURI!!))
        fileRef.putFile(fileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                Log.e(TAG, "Name: " + taskSnapshot.metadata!!.name)
                Toast.makeText(this, "File Uploaded ", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            }
            .addOnProgressListener { taskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                val intProgress = progress.toInt()
                Toast.makeText(this, "Uploaded $intProgress%...\"",Toast.LENGTH_SHORT).show()
            }
            .addOnPausedListener { System.out.println("Upload is paused!") }

    }

    private fun showChoosingFile() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), CHOOSING_IMAGE_REQUEST)
    }

    private fun validateInputFileName(fileName: String?): Boolean {
        if (TextUtils.isEmpty(fileName)) {
            Toast.makeText(this, "Enter file name!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun getFileExtension(uri: Uri): String? {
        val contentResolver = contentResolver
        val mime = MimeTypeMap.getSingleton()

        return mime.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null && data.data !=null){
            fileURI = data.data!!
            Log.v(TAG, "onActivityResult: not null ", )
        }
        myBitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileURI)

        Log.v(TAG, "onActivityResult: here ",  )
        if (requestCode == CHOOSING_IMAGE_REQUEST && resultCode == RESULT_OK) {
            try {

                imageView.setImageBitmap(myBitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun downloadFile() {
        var storageReference = FirebaseStorage.getInstance().reference
        val seriesRef = storageReference.child("images/"+currentUser?.displayName+".jpg")
        val localFile = File.createTempFile(currentUser?.displayName, "jpg")
        seriesRef.getFile(localFile).addOnSuccessListener { taskSnapshot ->
            imageView.setImageURI(Uri.fromFile(localFile.absoluteFile))
            Toast.makeText(this, localFile.absolutePath,Toast.LENGTH_SHORT).show()
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
}