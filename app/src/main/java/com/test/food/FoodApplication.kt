package com.test.food

import android.app.Application
import android.os.Process
import com.test.food.data.db.AppDatabase
import com.test.food.data.network.MyApi
import com.test.food.data.network.NetworkConnectionInterceptor
import com.test.food.data.preferences.PreferenceProvider
import com.test.food.data.repositories.UserRepository
import com.test.food.ui.auth.AuthViewModelFactory
import com.test.food.ui.fragments.book.BookViewModelFactory
import com.test.food.ui.fragments.favorites.FavoritesViewModelFactory
import com.test.food.ui.fragments.home.HomeViewModelFactory
import com.test.food.ui.fragments.profile.ProfileViewModelFactory
import com.test.food.ui.fragments.search.SearchViewModelFactory
import com.test.food.ui.main.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FoodApplication : Application(), KodeinAware {

    /**
     * root handling of exception
     */
    private val unCaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, ex ->
        try {
            Process.killProcess(Process.myPid())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate() {
        super.onCreate()
        // set default handler exception to catch
        Thread.setDefaultUncaughtExceptionHandler(unCaughtExceptionHandler)
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@FoodApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }

        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { MainViewModelFactory() }
        bind() from provider { HomeViewModelFactory() }
        bind() from provider { SearchViewModelFactory() }
        bind() from provider { BookViewModelFactory() }
        bind() from provider { FavoritesViewModelFactory() }
        bind() from provider { ProfileViewModelFactory() }

    }

}