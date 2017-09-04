package ninja.maxdome.justtodo

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import kotlinx.android.synthetic.main.activity_add_entry.*
import java.text.SimpleDateFormat
import java.util.*
import android.view.inputmethod.InputMethodManager


class AddEntryActivity : AppCompatActivity() {
    private var mDatePicker: DatePickerDialog? = null
    private var mTimePicker: TimePickerDialog? = null

    private var mDateFormatter: SimpleDateFormat? = null
    private var mTimeFormatter: SimpleDateFormat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)


        mDateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        createTimeFields()

        this.add_commit.setOnClickListener { acceptOnClick() }
        this.add_cancel.setOnClickListener { cancelOnClick() }

        this.add_date.inputType = InputType.TYPE_NULL
        this.add_date.setOnFocusChangeListener { _, b -> if(b) dateOnClick() }
        this.add_date.setOnClickListener { dateOnClick() }

        this.add_time.inputType = InputType.TYPE_NULL
        this.add_time.setOnFocusChangeListener { _, b -> if(b) timeOnClick() }
        this.add_time.setOnClickListener { timeOnClick() }
    }

    private fun acceptOnClick(){
        val result = Intent()

        // read and treat the different inputs
        val shortInfo   = this.add_shorttext.text.toString()
        if (!shortInfo.isEmpty()){
            val lengthControl = shortInfo.toCharArray()
            // if the text is to long shorten it
            if (lengthControl.size <= 28) result.putExtra("shortInfo", shortInfo)
            else {
                var lesslengthInfo = ""
                for (i in 0..19) lesslengthInfo += lengthControl[i]
                lesslengthInfo += "..."
                result.putExtra("shortInfo", lesslengthInfo)
            }
        }
        // cancel the Result if there is no input
        else { setResult(Activity.RESULT_CANCELED); finish() }

        // submit the long description
        val description = this.add_longtext.text.toString()
        if (!description.isEmpty()) result.putExtra("description", description)
        else setResult(Activity.RESULT_CANCELED)

        // check if the date is enabled and submit it
        if (add_switch_date.isChecked){
            val date = this.add_date.text.toString()
            result.putExtra("date",date)
        }
        // check if the time is enabled and submit it
        if (add_switch_time.isChecked){
            val time = this.add_time.text.toString()
            result.putExtra("time",time)
        }

        setResult(Activity.RESULT_OK, result)
        finish()
    }

    private fun cancelOnClick(){
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun dateOnClick(){
        // close the keyboard if its up
        val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(add_date.windowToken, 0)

        // show the DatePicker
        mDatePicker!!.show()
    }

    private fun timeOnClick(){
        // close the keyboard if its up
        val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(add_date.windowToken, 0)

        // show the DatePicker
        mTimePicker!!.show()
    }

    private fun createTimeFields(){
        val newCalendar = Calendar.getInstance()


        mDatePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = Calendar.getInstance()
            newDate.set(year, monthOfYear, dayOfMonth)
            this.add_date.setText(mDateFormatter!!.format(newDate.time))
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))


        mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hours, minutes ->
            val hoursStr: String = if (hours < 10) "0" + hours.toString() else hours.toString()
            val minutesStr: String = if (minutes < 10) "0" + minutes.toString() else minutes.toString()

            this.add_time.setText("$hoursStr:$minutesStr")
        }, newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), false)
    }
}
