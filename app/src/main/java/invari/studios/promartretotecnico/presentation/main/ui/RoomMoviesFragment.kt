package invari.studios.promartretotecnico.presentation.main.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import invari.studios.promartretotecnico.R
import invari.studios.promartretotecnico.base.BaseFragment
import invari.studios.promartretotecnico.data.model.MovieDB
import invari.studios.promartretotecnico.data.model.PopularMovie
import invari.studios.promartretotecnico.databinding.FragmentRoomMoviesBinding
import invari.studios.promartretotecnico.presentation.main.adapter.MoviesAdapter
import invari.studios.promartretotecnico.presentation.main.adapter.RoomMoviesAdapter

@AndroidEntryPoint
class RoomMoviesFragment : BaseFragment() {
    private lateinit var binding: FragmentRoomMoviesBinding
    private lateinit var adapterMovie: RoomMoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomMoviesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            val movieList = bundle.getParcelableArrayList<MovieDB>("movies")
            setPopularList(movieList?.toList() ?: listOf())
        }
    }
    private fun setPopularList(response: List<MovieDB>) {
        adapterMovie = RoomMoviesAdapter(requireContext()) {

        }
        adapterMovie.setPopularMovies(response)
        binding.rvRoomMovies.adapter = adapterMovie
    }
}