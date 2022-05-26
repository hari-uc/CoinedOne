package com.hari.coinedone

import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialog

 class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
//        val sharedPreference : SharedPreferences = this.getSharedPreferences("textpref", MODE_PRIVATE)
//        var gettimeboolean: Boolean = sharedPreference.getBoolean("timesaved",false)
//
//        if (gettimeboolean){
//            val intent = Intent(applicationContext,ModeActivity::class.java)
//            startActivity(intent)
//
//        }


        val title_txt  = findViewById<TextView>(R.id.heading_txt)


        val plus_btn = findViewById<Button>(R.id.btn_plus)





        plus_btn.setOnClickListener {

            val dialog = BottomSheetDialog(this)

            val view = layoutInflater.inflate(R.layout.add_time,null)
            val button_close = view.findViewById<ImageButton>(R.id.schedule_cancelbtn)
            val from_time_text = view.findViewById<TextView>(R.id.from_time)
            val from_time = view.findViewById<Button>(R.id.from_time_btn_ed)
            val to_time_btn = view.findViewById<Button>(R.id.totime_btn_ed)
            val to_time_txt = view.findViewById<TextView>(R.id.to_time_txt)
            val schedule_save_btn = view.findViewById<Button>(R.id.schedule_savebtn_add)

            button_close.setOnClickListener{
                dialog.dismiss()
            }

            dialog.setCancelable(false)

            dialog.setContentView(view)

            dialog.show()



            //from time click listener

            from_time.setOnClickListener {
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
                            from_time_text.text = formattedTime

                        }
                    }
                val timePicker : TimePickerDialog = TimePickerDialog(this,timePickerDialogListener,12,10,false)
                timePicker.show()


            }
            to_time_btn.setOnClickListener {

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
                            to_time_txt.text = formattedTime

                        }
                    }
                val timePicker : TimePickerDialog = TimePickerDialog(this,timePickerDialogListener,12,10,false)
                timePicker.show()

            }
            schedule_save_btn.setOnClickListener {
                val intent = Intent(applicationContext,ModeActivity::class.java)
                intent.putExtra("fromDate",from_time_text.text.toString())
                intent.putExtra("toTime",to_time_txt.text.toString())
                startActivity(intent)
            }



        }







    }



}