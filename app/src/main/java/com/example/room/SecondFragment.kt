package com.example.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.room.databinding.FragmentSecondBinding
import com.example.room.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {
    lateinit var binding: FragmentSecondBinding
    val args: SecondFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.note != null) {
            binding.btnAdd.setText("Update")
            binding.edTitle.setText(args.note!!.title)
            binding.edDescription.setText(args.note!!.description)
        }

        binding.btnAdd.setOnClickListener {
            if (args.note != null) {
                val note =
                    Note(args.note!!.id,binding.edTitle.text.toString(), binding.edDescription.text.toString())
                GlobalScope.launch(Dispatchers.IO) {
                    NoteDataBase.DatabaseBuilder.getDatabase(requireContext()).noteDao()
                        .updateNote(note)
                }
            } else {
                val note =
                    Note(0, binding.edTitle.text.toString(), binding.edDescription.text.toString())
                GlobalScope.launch(Dispatchers.IO) {
                    NoteDataBase.DatabaseBuilder.getDatabase(requireContext()).noteDao()
                        .insertNote(note)
                }
            }
            findNavController().popBackStack(R.id.secondFragment, true)
        }
    }

}