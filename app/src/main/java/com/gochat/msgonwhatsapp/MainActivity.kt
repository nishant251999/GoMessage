package com.gochat.msgonwhatsapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gochat.msgonwhatsapp.databinding.ActivityMainBinding
import com.hbb20.CountryCodePicker

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var ccp : CountryCodePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        ccp = binding.ccp
        ccp.registerCarrierNumberEditText(binding.etNum)
        setContentView(binding.root)

        val packageName = "com.whatsapp"
        var countryCode = ccp.selectedCountryCode
        binding.tiNum.prefixText = "+$countryCode "
        ccp.setOnCountryChangeListener {
            countryCode = ccp.selectedCountryCode
            binding.tiNum.prefixText = "+$countryCode "
        }

        if(!isPackageInstalled(packageName, packageManager))
            Toast.makeText(this, "Please install WhatsApp first", Toast.LENGTH_SHORT).show()

        binding.btnOpen.setOnClickListener {
            val num = ccp.fullNumber
            if(ccp.isValidFullNumber) {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    setPackage(packageName)
                    data = Uri.parse("https://wa.me/$num")
                }
                if(isPackageInstalled(packageName, packageManager))
                    startActivity(sendIntent)
                else
                    Toast.makeText(this, "Please install WhatsApp first", Toast.LENGTH_SHORT).show()
            } else if(num.length < 4){
                Toast.makeText(this, "Enter number please", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }
    }
}