package agency.five.codebase.android.movieapp

import agency.five.codebase.android.movieapp.ui.favorites.di.favoritesModule
import agency.five.codebase.android.movieapp.ui.home.di.homeModule
import agency.five.codebase.android.movieapp.ui.moviedetails.di.movieDetailsModule
import android.app.Application
import android.util.Log
import org.koin.core.context.startKoin

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(favoritesModule)
            modules(homeModule)
            modules(movieDetailsModule)
        }

        Log.d("MovieApp", "App started")
    }
}
