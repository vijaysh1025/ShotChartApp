package com.vijay.nbashottracker.datamodel

import com.vijay.nbashottracker.model.dailyschedule.Game
import io.reactivex.Single
import io.reactivex.annotations.NonNull
import java.time.LocalDate

interface IDataModel {
    @NonNull
    fun getGames(localDate:LocalDate): Single<List<Game>>?


}