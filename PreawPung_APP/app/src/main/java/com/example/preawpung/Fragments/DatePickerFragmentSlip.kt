package com.example.preawpung

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val year = c.get(Calendar.YEAR)
        return DatePickerDialog(
            requireContext(),
            0,
            this,
            year,
            month,
            day
        )
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        var tv: TextView? = activity?.findViewById(R.id.date)
        var m = month + 1
        tv!!.text = "$year-$m-$day"
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        var calendar: Calendar = Calendar.getInstance(); calendar.set(year, month, day)
        val chosenDate = calendar.time
        val df = DateFormat.getDateInstance(DateFormat.SHORT)
        return df.format(chosenDate)
    }

    override fun onCancel(dialog: DialogInterface) {
        Toast.makeText(activity, "Please select a date.", Toast.LENGTH_SHORT).show()
        super.onCancel(dialog)
    }
}