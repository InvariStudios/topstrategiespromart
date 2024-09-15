package invari.studios.promartretotecnico.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import invari.studios.promartretotecnico.data.model.MovieDB

@Database(entities = [MovieDB::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}