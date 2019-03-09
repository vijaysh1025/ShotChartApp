package com.vijay.nbashottracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vijay.nbashottracker.model.APIClient
import com.vijay.nbashottracker.model.DataStore
import com.vijay.nbashottracker.model.Game
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.create

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*var disposable = APIClient.instance
            ?.create<DataStore>()
            ?.getScheduleOfDay()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({result->
                Log.d("Test", (result.games as List<Game>).get(0).id)
            },{
                error-> Log.d("Error", error.message)
            })*/

    }
}
