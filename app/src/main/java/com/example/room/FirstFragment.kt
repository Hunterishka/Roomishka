package com.example.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.room.databinding.FragmentFirstBinding
import com.example.room.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {
    lateinit var binding: FragmentFirstBinding
    lateinit var adapter: Rec_Adapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Rec_Adapter()

        adapter.onItemClickListener(object : Rec_Adapter.OnItemClickListener {
            override fun delete(note: Note) {
                GlobalScope.launch(Dispatchers.IO) {
                    deleteNote(note)
                    setList()
                }
            }

            override fun update(note: Note) {
                val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(note)
                findNavController().navigate(action)
            }

        })

        GlobalScope.launch(Dispatchers.IO) {
            setList()

            binding.recyclerview.adapter = adapter
            binding.btnAdd.setOnClickListener {
                findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
            }
        }
    }
    fun setList() {
        val notes =
            NoteDataBase.DatabaseBuilder.getDatabase(requireContext()).noteDao().getAllNotes()
        adapter.submitList(notes)
    }

    fun deleteNote(note: Note) {

        NoteDataBase.DatabaseBuilder.getDatabase(requireContext()).noteDao().deleteNote(note)
    }
}