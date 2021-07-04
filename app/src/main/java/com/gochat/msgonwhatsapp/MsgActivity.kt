package com.gochat.msgonwhatsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MsgActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg)

        val packageName = "com.whatsapp"
        if(!isPackageInstalled(packageName, packageManager))
            Toast.makeText(this, "Please install WhatsApp first", Toast.LENGTH_SHORT).show()
        if(intent.action == Intent.ACTION_PROCESS_TEXT) {
            val input = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT).toString()
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                setPackage(packageName)
                putExtra(Intent.EXTRA_TEXT, input)
                type = "text/*"
            }
            if(isPackageInstalled(packageName, packageManager))
                startActivity(sendIntent)
            else
                Toast.makeText(this, "Please install WhatsApp first", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}