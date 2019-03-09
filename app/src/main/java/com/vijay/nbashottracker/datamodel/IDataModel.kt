package com.vijay.nbashottracker.datamodel

import io.reactivex.Single
import io.reactivex.annotations.NonNull
import java.util.*

interface IDataModel {
    @NonNull
    fun getGameIds(): Single<List<String>>
}