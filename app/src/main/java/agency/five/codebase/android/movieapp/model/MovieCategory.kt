package agency.five.codebase.android.movieapp.model

enum class MovieCategory {
    POPULAR_STREAMING,
    POPULAR_ONTV,
    POPULAR_FORRENT,
    POPULAR_INTHEATHERS,
    NOWPLAYING_MOVIES,
    NOWPLAYING_TV,
    UPCOMING_TODAY,
    UPCOMING_THISWEEK;

    companion object {
        private val VALUES = values()
        fun getByOrdinal(ordinal: Int) = VALUES.firstOrNull { it.ordinal == ordinal }
    }
}
