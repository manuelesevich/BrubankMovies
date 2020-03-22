package com.example.brubankmovies.mainScreen

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.brubankmovies.R
import com.example.brubankmovies.databinding.FragmentMainScreenBinding

/**
 * A simple [Fragment] subclass.
 * The movies are displayed in a RecyclerView
 *
 */
class MainScreenFragment : Fragment() {

    private lateinit var layoutManager: LinearLayoutManager
    private var isLastPage = false
    private var isLoading = false
    private var isSearching = false
    private val viewModel: MainScreenViewModel by lazy {
        ViewModelProvider(this).get(MainScreenViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layoutManager = LinearLayoutManager(context)
        val binding = FragmentMainScreenBinding.inflate(inflater)
        //val application = requireNotNull(this.activity).application
        //val datasource = MovieDatabase.getInstance(application).movieDatabaseDao
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        binding.mainScreenFragmentRecyclerview.layoutManager = layoutManager
        binding.mainScreenFragmentRecyclerview.adapter =
            MovieGridAdapter(MovieGridAdapter.OnClickListener {
                viewModel.displayMovieDetails(it)
            })

        scrollListener(binding, isSearching)

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                //TODO add navigation to detail fragment
                this.findNavController().navigate(
                    MainScreenFragmentDirections.actionMainScreenFragmentToDetailScreenFragment(it)
                )
                viewModel.displayMovieDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Listens on the RecyclerView and if it reached the bottom of the list, loads more movies.
     */
    private fun scrollListener(binding: FragmentMainScreenBinding, isSearching: Boolean) {
        binding.mainScreenFragmentRecyclerview.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        if (!isSearching) {
                            isLoading = true
                            viewModel.loadMoreTrendingMovies()
                            isLoading = false
                        }

                    }
                }
            }
        })
    }

    //creates the search menu and calls search movie method
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.search_menu)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchMovies(query!!)
                isSearching = true
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }


}
