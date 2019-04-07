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

abstract class BaseActivity : AppCompatActivity(){
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as ShotTrackerApplication).appComponent
    }

    fun onError(t:Throwable){
        when(t){
            is HttpException -> onUnkownError(t.message?:"Http Error")
            is SocketTimeoutException -> onTimeOut()
            is IOException -> onNetworkError()
            else -> onUnkownError(t.message?:"Uknown Error")
        }
    }

    fun onUnkownError(error:String) = showErrorMessage(error)
    fun onTimeOut()= showErrorMessage("Time Out Error")
    fun onNetworkError() = showErrorMessage("Network Error")

    fun showErrorMessage(message:String){
        val view:View = findViewById(android.R.id.content)
        val snackbar = Snackbar.make(view,message,
            Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}