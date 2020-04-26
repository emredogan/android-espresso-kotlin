package com.emredogan.espresso_demonstration

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var playerImage: ImageView
    lateinit var playerDrawable: Drawable

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter(this)

        playerRecyclerList.adapter = recyclerViewAdapter
        playerRecyclerList.layoutManager = layoutManager

        addPlayerFab.setOnClickListener {
            showAddPlayerDialog()
        }
    }

    private fun showAddPlayerDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialogue_add_player)
        val add_image_button = dialog .findViewById(R.id.add_image_button) as Button
        val saveButton = dialog .findViewById(R.id.saveButton) as Button
        val playerName = dialog .findViewById<EditText>(R.id.location_name_edit_text)
        val playerNo = dialog .findViewById<EditText>(R.id.season_edit_text)
        playerImage = dialog .findViewById(R.id.playerImageView) as ImageView
        playerImage.setImageResource(R.drawable.kapadokya)

        add_image_button.setOnClickListener {
            //checkStoragePermission()
            playerImage.setImageDrawable(getDrawable(R.drawable.kaputas))
        }
        saveButton.setOnClickListener {
            playerDrawable = playerImage.drawable
            val newPlayer = Destination(playerName.text.toString(), playerNo.text.toString(), playerDrawable)
            LocationDatabase.location_list.add(newPlayer)
            recyclerViewAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        dialog .show()
    }

    fun checkStoragePermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_DENIED){
            //permission denied
            //show popup to request runtime permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_CODE)
        }
        else{
            //permission already granted
            pickImageFromGallery();
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            data?.data?.let {
                Glide.with(this).load(data.data)
                    .apply( RequestOptions()
                        .fitCenter()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(Target.SIZE_ORIGINAL))
                    .into(playerImage);

            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
