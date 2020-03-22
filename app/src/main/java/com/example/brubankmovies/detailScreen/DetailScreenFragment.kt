package com.example.brubankmovies.detailScreen

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.brubankmovies.R
import com.example.brubankmovies.databinding.FragmentDetailScreenBinding


/**
 * A simple [Fragment] subclass.
 * Will display the details once a movie is clicked in the recycler view.
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

        //binding.viewModel.loadBackdropImage(binding.fragmentDetailScreenConstraintLayout)

        return binding.root
    }


}
