package agency.five.codebase.android.movieapp.mock

import kotlinx.coroutines.flow.MutableStateFlow

object FavoritesDBMock {

    val favoriteIds = MutableStateFlow(setOf(1,2,3,5))
    fun insert(movieId: Int) {
        favoriteIds.value += movieId
    }
    fun delete(movieId: Int) {
        favoriteIds.value -= movieId
    }
}
