package com.jubutech.to_dolist

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.jubutech.to_dolist.databinding.FragmentAddBinding
import com.jubutech.to_dolist.databinding.FragmentHomeBinding
import java.util.Calendar

class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding

    var showTime: String? = null
    var showDate: String? = null

    lateinit var dataBase: NoteDataBase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)


        dataBase = Room.databaseBuilder(requireActivity(), NoteDataBase::class.java, "Note-DB")
            .allowMainThreadQueries().build()


        binding.datebtn.setOnClickListener {

            pickADate()


            binding.timebtn.setOnClickListener {
                pickATime()
            }

            binding.submitbtn.setOnClickListener {

                val titlestr = binding.titleedt.text.toString()
                val timestr = showTime ?: "00.00"
                val datestr = showDate ?: "00.00.00"

                val note= Note(title = titlestr, time = timestr, date = datestr)
                dataBase.getNoteDao().insertData(note)

                findNavController().navigate(R.id.action_addFragment_to_homeFragment)

            }

        }

        return binding.root
    }

    private fun pickATime() {

        val calendar = Calendar.getInstance()

        val minute = calendar.get(Calendar.MINUTE)
        val hour = calendar.get(Calendar.HOUR)

        val showTimeDialog = TimePickerDialog(
            requireActivity(), TimePickerDialog.OnTimeSetListener { _, hour, minute ->

                showTime = "${hour} : $minute"
                binding.timebtn.text = showTime


            }, minute, hour, true
        )
        showTimeDialog.show()

    }

    private fun pickADate() {


        val calendar = Calendar.getInstance()

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)


        val showDatePicker = DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->


                showDate = "$dayOfMonth.${month + 1}.$year"
                binding.datebtn.text = showDate

            },
            year,
            month,
            day
        )


        showDatePicker.show()


    }


}