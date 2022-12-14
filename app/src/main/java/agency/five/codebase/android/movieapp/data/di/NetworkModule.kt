package agency.five.codebase.android.movieapp.data.di

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.logging.*
import org.koin.dsl.module

val networkModule = module {

    single<MovieService> { MoviesServiceImpl(client = get()) }

    single {
        HttpClient(Android) {
            expectSuccess = true
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }
        }
    }
}
