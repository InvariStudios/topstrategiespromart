package invari.studios.promartretotecnico.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import invari.studios.promartretotecnico.data.model.MovieDB

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDB>)

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieDB>

    @Query("SELECT * FROM movies WHERE LOWER(title) LIKE LOWER('%' || :query || '%')")
    suspend fun searchMovies(query: String): List<MovieDB>

}