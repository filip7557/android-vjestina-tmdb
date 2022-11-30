package agency.five.codebase.android.movieapp.mock

import kotlinx.coroutines.flow.MutableStateFlow

object FavoritesDBMock {

    val favoriteIds = MutableStateFlow(setOf<Int>())
    fun insert(movieId: Int) {
        favoriteIds.value.plusElement(movieId)
    }
    fun delete(movieId: Int) {
        favoriteIds.value.minusElement(movieId)
    }
}
