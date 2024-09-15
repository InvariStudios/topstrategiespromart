package invari.studios.promartretotecnico.presentation.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import invari.studios.promartretotecnico.BuildConfig
import invari.studios.promartretotecnico.data.model.PopularMovie
import invari.studios.promartretotecnico.databinding.FragmentDetailMoviewBinding

@AndroidEntryPoint
class DetailMoviewFragment : Fragment() {
    private lateinit var binding : FragmentDetailMoviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMoviewBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            val movie = bundle.getParcelable<PopularMovie>("popularMovie")
            setUpView(movie)
        }
    }
    private fun setUpView(movie:PopularMovie?){
        movie.let {
            with(binding){
                Glide.with(requireContext())
                    .load(BuildConfig.baseUrlImage+movie?.backdropPath)
                    .into(ivMovie)
                tvTitle.text = movie?.originalTitle ?: "-"
                tvAdult.text = if(movie?.adult==true) "Para adultos" else "Todo público"
                tvOverView.text = movie?.overview ?: "-"
            }

        }
    }
}