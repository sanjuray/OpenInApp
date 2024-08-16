package com.practice.openinapp.utils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.practice.openinapp.R
import com.practice.openinapp.databinding.DialogDurationPickerLayoutBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object CommonObjects {

    fun getGreeting(): String{
        var greeting = ""
        try {
            val date = Date()
            val cal = Calendar.getInstance()
            cal.time = date

            val hour = cal[Calendar.HOUR_OF_DAY]

            when (hour) {
                in 0..11 -> {
                    greeting = "Good Morning"
                }
                in 12..16 -> {
                    greeting = "Good Afternoon"
                }
                in 17..23 -> {
                    greeting = "Good Evening"
                }
            }
        }catch (e: Exception){
            errorLogs(e.toString())
        }
        return greeting
    }

    fun showToast(context: Context, msg: String){
        try {
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            errorLogs(e.toString())
        }
    }

    fun makeNetworkLogMessageFormat(statusCode: Int, responseMessage: String): String{
        return "Status Code: $statusCode : $responseMessage"
    }

    fun networkLogs(requestResponse: String){
        Log.d("SGNETWORK",requestResponse)
    }

    fun errorLogs(errorMessage: String){
        Log.e("SGERROR",errorMessage)
    }

    fun openInBrowserIntent(context: Context, url: String) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        } catch (e: Exception) {
            errorLogs(e.toString())
        }
    }

    fun showDurationSelectionDialog(
        context: Context,
        title: String,
        spinnerData: List<String>,
        fromPosition: Int,
        toPosition: Int,
        primaryActionText: String,
        alternativeActionText: String,
        primaryActionCallback: (from: Int, to: Int) -> Unit
        ):Dialog{
        val dialog = Dialog(context)
        try {
            val binding: DialogDurationPickerLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_duration_picker_layout,
                null,
                false
            )

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setContentView(binding.root)
            dialog.window!!.setGravity(Gravity.CENTER)

            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = lp

            binding.tvTitleTextView.text = title

            var from = fromPosition
            var to = toPosition
            val spinnerDataArray = spinnerData.toTypedArray()
            val fromArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item,
                spinnerDataArray
            )
            binding.spFromSpinner.adapter = fromArrayAdapter
            binding.spFromSpinner.setSelection(from)

            val toArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item,
                spinnerDataArray
            )
            binding.spToSpinner.adapter = toArrayAdapter
            binding.spToSpinner.setSelection(to)

            binding.spFromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    from = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

            binding.spToSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    to = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }


            binding.btActionButton.text = primaryActionText
            binding.tvAlternativeActionTextView.text = alternativeActionText

            binding.btActionButton.setOnClickListener {
                if (from <= to) {
                    primaryActionCallback(from, to)
                    dialog.dismiss()
                } else {
                    showToast(context, "Check the Duration Selection")
                }
            }

            binding.tvAlternativeActionTextView.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }catch (e: Exception){
            errorLogs(e.toString())
        }
        return dialog
    }

    fun getDateFormatted(date: String): String{
        var outputString = ""
        try {
            val inputFormat = SimpleDateFormat(Constants.DATE_FORMAT_FROM_SERVER, Locale.ENGLISH)
            val outputFormat = SimpleDateFormat(Constants.DATE_FORMAT_TO_DISPLAY, Locale.ENGLISH)
            val inputDate = date.split("T")[0]
            outputString = outputFormat.format(inputFormat.parse(inputDate)!!)
        }catch (e: Exception){
            errorLogs(e.toString())
        }
        return outputString
    }

}