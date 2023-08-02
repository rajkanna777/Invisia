package com.example.invisia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.invisia.R
import com.example.invisia.databinding.ItemMovieBinding
import com.example.invisia.model.Movie

class MovieAdapter(private val clickListener: OnMovieItemClickListener, isFirstScreen: Boolean) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie,clickListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovies(movies: List<Movie>) {
        movieList = movies
        notifyDataSetChanged()
    }

    fun update() {
        movieList = emptyList()
        notifyDataSetChanged()
    }

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, clickListener: OnMovieItemClickListener) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .centerCrop() // Crop the image to fill the ImageView bounds
                .placeholder(R.drawable.ic_baseline_face_24) // Placeholder image while loading
                .error(R.drawable.ic_baseline_error_outline_24) // Error image if the URL is invalid or image fails to load
                .into(binding.imageViewPoster)

// Set favorite icon based on the 'isFavorite' status
            val favoriteIcon =
                if (movie.isFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
            binding.imgFavorite.setImageResource(favoriteIcon)

            // Set click listener for favorite icon
            binding.imgFavorite.setOnClickListener {
                clickListener.onFavoriteClick(movie)
            }
            binding.textViewTitle.text = movie.title

        }
    }


    interface OnMovieItemClickListener {
        fun onItemClick(movie: Movie)
        fun onFavoriteClick(movie: Movie)
    }
}
