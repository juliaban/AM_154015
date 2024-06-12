package com.example.sladamiprzygod

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.sladamiprzygod.databinding.ActivityDetailsBinding

class DetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val SMS_PERMISSION_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLayout(R.layout.activity_details)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title") ?: "Brak tytuĹ‚u"
        val image = intent.getStringExtra("image") ?: ""
        val description = intent.getStringExtra("description") ?: "Brak opisu"
        val time = intent.getStringExtra("time") ?: "Brak czasu"

        val szlakFragment = SzlakFragment().apply {
            arguments = Bundle().apply {
                putString("title", title)
                putString("image", image)
                putString("description", description)
                putString("time", time)
            }
        }


        val stopwatchFragment = StoperFragment()

        supportFragmentManager.inTransaction {
            replace(R.id.fragmentContainerDetail, szlakFragment)
            replace(R.id.fragmentContainerStopwatch, stopwatchFragment)
        }

        binding.fabSendSms.setOnClickListener {
            sendSms(title)
        }
    }

    private fun sendSms(title: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), SMS_PERMISSION_CODE)
        } else {
            val smsIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("sms:")
                putExtra("sms_body", "Pozdrowienia ze szlaku: $title")
            }
            try {
                startActivity(smsIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "Nie moĹĽna wysĹ‚aÄ‡ SMS: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val title = intent.getStringExtra("title") ?: "Brak tytuĹ‚u"
                sendSms(title)
            } else {
                Toast.makeText(this, "Brak pozwolenia na wysyĹ‚anie SMS", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }
}
