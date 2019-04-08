package com.vijay.nbashottracker.core.platform

import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.vijay.nbashottracker.ShotTrackerApplication
import com.vijay.nbashottracker.core.di.ApplicationComponent
import io.reactivex.annotations.NonNull
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * BaseActivity that all Activities should derive from
 */
abstract class BaseActivity : AppCompatActivity(){

    /**
     * Access to DI ApplicationComponent so that each activity can inject itself.
     */
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as ShotTrackerApplication).appComponent
    }

    /**
     * General Error handling function for all Observable subscribers that will
     * display a message in a snackbar depending on the error.
     */
    fun onError(t:Throwable){
        when(t){
            is HttpException -> onUnkownError(t.message?:"Http Error")
            is SocketTimeoutException -> onTimeOut()
            is IOException -> onNetworkError()
            else -> onUnkownError(t.message?:"Uknown Error")
        }
    }

    /**
     * Error Type responses for different Exceptions.
     */
    fun onUnkownError(error:String) = showErrorMessage(error)
    fun onTimeOut()= showErrorMessage("Time Out Error")
    fun onNetworkError() = showErrorMessage("Network Error")

    /**
     * SnackBar create and show.
     */
    fun showErrorMessage(message:String){
        val view:View = findViewById(android.R.id.content)
        val snackbar = Snackbar.make(view,message,
            Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}