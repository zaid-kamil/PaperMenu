package com.podium.papermenu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.podium.papermenu.databinding.ActivityScannerBinding


class ScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScannerBinding
    private lateinit var prefs: MyUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = MyUtil(this)
        val barcodeLauncher =
            registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                    launchMenu()
                }
            }

        binding.fabScan.setOnClickListener {
            val options = ScanOptions()
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            options.setPrompt("Find your table's QR image")
            options.setBeepEnabled(true)

            options.setBarcodeImageEnabled(false)
            barcodeLauncher.launch(options)
        }
    }



    private fun launchMenu() {

        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}