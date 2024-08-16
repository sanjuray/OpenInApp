package com.practice.openinapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.NameNotFoundException
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.practice.openinapp.databinding.ActivityMainBinding
import com.practice.openinapp.databinding.FragmentDashboardBinding
import com.practice.openinapp.fragments.dashboard.DashboardFragment
import com.practice.openinapp.utils.CommonObjects


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        loadFragment(DashboardFragment.newInstance())
    }

    fun loadFragment(fragment: Fragment, bindingParent: Any? = null){
        try {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            if(bindingParent == null){
                fragmentTransaction.replace(
                    binding.flContainerForFragmentsFragmentHost.id,
                    fragment
                )
                if(fragment !is DashboardFragment){
                    fragmentTransaction.addToBackStack(fragment.javaClass.canonicalName)
                }
            }else{
                fragmentTransaction.replace(
                    (bindingParent as FragmentDashboardBinding).flContainerForNavFragmentsFragmentHost.id,
                    fragment
                )
            }
            fragmentTransaction.commit()
        }catch (e: Exception){
            CommonObjects.errorLogs(e.toString())
        }
    }

    fun onClickWhatsapp(phoneNo: String){
        try {
            val uri = Uri.parse("smsto:$phoneNo")
            val i = Intent(Intent.ACTION_SENDTO, uri)
            i.setPackage("com.whatsapp")
            startActivity(Intent.createChooser(i, ""))
        }catch (e: NameNotFoundException){
            CommonObjects.showToast(applicationContext,"Whatsapp Not Installed!")
        }
    }

    fun copyToClipboard(text: String){
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Link",text)
        clipboardManager.setPrimaryClip(clipData)
        CommonObjects.showToast(applicationContext,"Link Copied!")
    }
}