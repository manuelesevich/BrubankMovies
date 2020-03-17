package com.example.brubankmovies.detailScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.example.brubankmovies.R
import com.example.brubankmovies.databinding.FragmentDetailScreenBinding

/**
 * A simple [Fragment] subclass.
 */
class DetailScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val application = requireNotNull(activity).application
        val binding = FragmentDetailScreenBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val movie = DetailScreenFragmentArgs.fromBundle(arguments!!).selectedMovie
        val viewModelFactory = DetailScreenViewModelFactory(movie, application)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(DetailScreenViewModel::class.java)
        return binding.root
    }

}
