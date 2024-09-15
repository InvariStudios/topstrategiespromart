package invari.studios.promartretotecnico.presentation.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import invari.studios.promartretotecnico.BuildConfig
import invari.studios.promartretotecnico.data.model.PopularMovie
import invari.studios.promartretotecnico.databinding.ItemMovieBinding

class MoviesAdapter(
    private val context:Context,
    private val onItemClick: (PopularMovie) -> Unit)
    : RecyclerView.Adapter<MoviesAdapter.CategoryViewHolder>() {
    private var listMovies = listOf<PopularMovie>()

    fun setPopularMovies(list: List<PopularMovie>) {
        listMovies = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    inner class CategoryViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: PopularMovie) {
            binding.apply {
                containerMovie.setOnClickListener{
                    onItemClick.invoke(movie)
                }
                tvMovieTitle.text = movie.originalTitle
                Glide.with(context)
                    .load(BuildConfig.baseUrlImage+movie.posterPath)
                    .into(ivMovie)
            }
        }
    }
}