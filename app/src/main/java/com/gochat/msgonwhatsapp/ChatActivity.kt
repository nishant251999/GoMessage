package com.gochat.msgonwhatsapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.gochat.msgonwhatsapp.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val packageName = "com.whatsapp"
        if(!isPackageInstalled(packageName, packageManager))
            Toast.makeText(this, "Please install WhatsApp first", Toast.LENGTH_SHORT).show()

        if(intent.action == Intent.ACTION_PROCESS_TEXT) {
            var num = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT).toString()
            if(num.isDigitsOnly() && num.length == 10) {
                num = "91$num"
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    setPackage(packageName)
                    data = Uri.parse("https://wa.me/$num")
                }
                if(isPackageInstalled(packageName, packageManager))
                    startActivity(sendIntent)
                else
                    Toast.makeText(this, "Please install WhatsApp first", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Select a valid number", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}