package invari.studios.promartretotecnico.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
class MovieDB(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val title: String,
    val popularity: Double,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String
) :Parcelable