package ie.setu.tazq

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

class TazqApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        Firebase.initialize(this)

        // Use Timber for logging in debug builds
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting Tazq Application")
    }
}