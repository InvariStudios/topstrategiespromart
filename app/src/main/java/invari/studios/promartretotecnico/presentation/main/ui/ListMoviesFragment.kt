package invari.studios.promartretotecnico.presentation.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import invari.studios.promartretotecnico.R
import invari.studios.promartretotecnico.base.BaseFragment
import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.model.MovieDB
import invari.studios.promartretotecnico.data.model.PopularMovie
import invari.studios.promartretotecnico.databinding.FragmentListMoviesBinding
import invari.studios.promartretotecnico.presentation.main.adapter.MoviesAdapter
import invari.studios.promartretotecnico.presentation.main.viewmodel.MovieViewModel
import kotlinx.coroutines.launch
import java.util.ArrayList

@AndroidEntryPoint
class ListMoviesFragment : BaseFragment() {
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding : FragmentListMoviesBinding
    private lateinit var adapterMovie: MoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListMoviesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        //setObserverFlow()


        //Function using live data option
        if(isOnline(requireContext())){
            viewModel.getPopularMovies()
        }
        //Function using flows option
        //viewModel.getPopularMoviesWithFlows()


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getMoviesFromDb(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Acción cuando el texto cambia
                return true
            }
        })

    }
    private fun navigateToRoomResults(list:List<MovieDB>){
        val bundle = Bundle().apply {
            putParcelableArrayList("movies", ArrayList(list))
        }
        findNavController().navigate(R.id.action_listMoviesFragment_to_roomMoviesFragment,bundle)
    }
    private fun navigateToDetail(popularMovie: PopularMovie) {
        val bundle = Bundle().apply {
            putParcelable("popularMovie", popularMovie)
        }
        findNavController().navigate(R.id.action_listMoviesFragment_to_detailMoviewFragment,bundle)
    }
    private fun setPopularList(response: List<PopularMovie>) {
        adapterMovie = MoviesAdapter(requireContext()) {
            navigateToDetail(it)
        }
        adapterMovie.setPopularMovies(response)
        binding.rvMovies.adapter = adapterMovie
    }
    private fun setObserverFlow(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.popularMovieFlowResult.collect { result ->
                    when (result) {
                        is ServiceResult.Success -> {
                            hideProgressDialog()
                            val list = result.data.results
                            setPopularList(list)
                        }
                        is ServiceResult.Error -> {
                            hideProgressDialog()
                        }
                        ServiceResult.Loading -> {
                            showProgressDialog()
                        }

                        null -> {
                            //nothing
                        }
                    }
                }
            }
        }

    }
    private fun setObservers(){
        //Case when use livedata
        viewModel.popularMovieResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ServiceResult.Success -> {
                    hideProgressDialog()
                    val list = result.data.results
                    setPopularList(list)
                    viewModel.cleanRequest()
                }
                is ServiceResult.Error -> {
                    hideProgressDialog()
                }
                ServiceResult.Loading -> {
                    showProgressDialog()
                }

                null -> {
                    //nothing
                }
            }
        }
        viewModel.moviesDbResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ServiceResult.Success -> {
                    hideProgressDialog()
                    viewModel.cleanDb()
                    navigateToRoomResults(result.data)
                }
                is ServiceResult.Error -> {
                    hideProgressDialog()
                }
                ServiceResult.Loading -> {
                    showProgressDialog()
                }

                null -> {
                    //nothing
                }
            }
        }
    }
}