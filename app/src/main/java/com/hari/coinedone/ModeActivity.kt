package com.hari.coinedone

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialog

class ModeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode)
        supportActionBar?.hide()



        val Edit_txt_btn = findViewById<Button>(R.id.Edittext_btn)
        val toggle_switch = findViewById<Switch>(R.id.switch1)
        val Delete_btn = findViewById<Button>(R.id.deletetext_btn)


        val intent: Intent
        intent = getIntent()

        val fromTime = intent.getStringExtra("fromDate")
        val toTime = intent.getStringExtra("toTime")
        val fromtime_txtview = findViewById<TextView>(R.id.starttime_txt)
        val totime_txtview = findViewById<TextView>(R.id.end_time_txt_m)


        fromtime_txtview.text = fromTime
        totime_txtview.text = toTime

//        val sharedPreference : SharedPreferences = this.getSharedPreferences("textpref", MODE_PRIVATE)
//
//        val edito: SharedPreferences.Editor= sharedPreference.edit()
//        edito.putString("fromvalue",fromTime.toString())
//        edito.putString("tovalue",toTime.toString())
//        edito.putBoolean("timesaved",true)
//        edito.commit()
//        edito.apply()
//
//
//        fromtime_txtview.text = sharedPreference.getString("fromvalue", fromTime.toString()).toString()
//        totime_txtview.text = sharedPreference.getString("tovalue", toTime.toString()).toString()








        Delete_btn.setOnClickListener {
            fromtime_txtview.text = "00. 00"
            totime_txtview.text = "00 . 00"
        }





        Edit_txt_btn.setOnClickListener {


            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.edittime,null)

            val button_close_edit = view.findViewById<ImageButton>(R.id.edit_time_cancelbtn)
            val button_save_edit = view.findViewById<Button>(R.id.edittime_savebtn)
            val from_edit_tv = view.findViewById<TextView>(R.id.from_time_edit)
            val to_edit_tv = view.findViewById<TextView>(R.id.to_time_txt_edit)
            val from_time_btn = view.findViewById<Button>(R.id.from_time_btn_ed)
            val to_time_btn = view.findViewById<Button>(R.id.totime_btn_ed)



            button_close_edit.setOnClickListener{
                dialog.dismiss()
            }
            dialog.setCancelable(false)

            dialog.setContentView(view)

            dialog.show()
            to_time_btn.setOnClickListener {
                //function
                // time is picked from the time picker dialog
                val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
                    object : TimePickerDialog.OnTimeSetListener {
                        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                            // logic to properly handle
                            // the picked timings by user
                            val formattedTime: String = when {
                                hourOfDay == 0 -> {
                                    if (minute < 10) {
                                        "${hourOfDay + 12}:0${minute} am"
                                    } else {
                                        "${hourOfDay + 12}:${minute} am"
                                    }
                                }
                                hourOfDay > 12 -> {
                                    if (minute < 10) {
                                        "${hourOfDay - 12}:0${minute} pm"
                                    } else {
                                        "${hourOfDay - 12}:${minute} pm"
                                    }
                                }
                                hourOfDay == 12 -> {
                                    if (minute < 10) {
                                        "${hourOfDay}:0${minute} pm"
                                    } else {
                                        "${hourOfDay}:${minute} pm"
                                    }
                                }
                                else -> {
                                    if (minute < 10) {
                                        "${hourOfDay}:${minute} am"
                                    } else {
                                        "${hourOfDay}:${minute} am"
                                    }
                                }
                            }
                            to_edit_tv.text = formattedTime

                        }
                    }
                val timePicker : TimePickerDialog = TimePickerDialog(this,timePickerDialogListener,12,10,false)
                timePicker.show()
            }

            from_time_btn.setOnClickListener {
                //function
                // time is picked from the time picker dialog
                val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
                    object : TimePickerDialog.OnTimeSetListener {
                        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                            // logic to properly handle
                            // the picked timings by user
                            val formattedTime: String = when {
                                hourOfDay == 0 -> {
                                    if (minute < 10) {
                                        "${hourOfDay + 12}:0${minute} am"
                                    } else {
                                        "${hourOfDay + 12}:${minute} am"
                                    }
                                }
                                hourOfDay > 12 -> {
                                    if (minute < 10) {
                                        "${hourOfDay - 12}:0${minute} pm"
                                    } else {
                                        "${hourOfDay - 12}:${minute} pm"
                                    }
                                }
                                hourOfDay == 12 -> {
                                    if (minute < 10) {
                                        "${hourOfDay}:0${minute} pm"
                                    } else {
                                        "${hourOfDay}:${minute} pm"
                                    }
                                }
                                else -> {
                                    if (minute < 10) {
                                        "${hourOfDay}:${minute} am"
                                    } else {
                                        "${hourOfDay}:${minute} am"
                                    }
                                }
                            }
                            from_edit_tv.text = formattedTime

                        }
                    }
                val timePicker : TimePickerDialog = TimePickerDialog(this,timePickerDialogListener,12,10,false)
                timePicker.show()
            }


            //save button
            button_save_edit.setOnClickListener {
                fromtime_txtview.text = from_edit_tv.text.toString()
                totime_txtview.text = to_edit_tv.text.toString()

            }


        }

        val sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        toggle_switch.isChecked = sharedPreferences.getBoolean("SwitchState",false)
        
        toggle_switch.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                editor.putBoolean("SwitchState",true)
                Toast.makeText(this, "please turn on Accessibiblity",
                    Toast.LENGTH_SHORT).show()
                val intent = Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(intent)

            }else{
                editor.putBoolean("SwitchState",false)
            }
            editor.apply()

        }


    }
}