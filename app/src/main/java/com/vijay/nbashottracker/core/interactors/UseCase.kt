package com.vijay.nbashottracker.core.interactors

import io.reactivex.Single

abstract class UseCase<Type, in Params>
        where Type : Any {
    abstract fun For(params:Params):Single<Type>
}