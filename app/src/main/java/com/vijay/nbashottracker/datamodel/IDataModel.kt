package com.vijay.nbashottracker.datamodel

import com.vijay.nbashottracker.model.Game
import io.reactivex.Single
import io.reactivex.annotations.NonNull
import java.time.LocalDate
import java.util.*

interface IDataModel {
    @NonNull
    fun getGames(localDate:LocalDate): Single<List<Game>>?


}