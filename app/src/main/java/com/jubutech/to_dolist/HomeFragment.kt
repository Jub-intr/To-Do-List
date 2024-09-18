package com.jubutech.to_dolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.jubutech.to_dolist.databinding.FragmentHomeBinding
import java.util.zip.Inflater

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var dataBase: NoteDataBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        dataBase = Room.databaseBuilder(requireActivity(), NoteDataBase::class.java, "Note-DB")
            .allowMainThreadQueries().build()


        val notes: List<Note> = dataBase.getNoteDao().getAllData()

        val adapter = NoteAdapter()
        adapter.submitList(notes)


        binding.recycler.adapter = adapter


        binding.addbtn.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }



        return binding.root
    }
}