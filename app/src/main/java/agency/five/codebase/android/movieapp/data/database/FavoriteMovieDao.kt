package agency.five.codebase.android.movieapp.data.database

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieDao {

    @Query()
    fun favorites(): Flow<List<DbFavoriteMovie>>
}
