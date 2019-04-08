package com.vijay.nbashottracker.core.interactors

import io.reactivex.Single

/**
 * Base UseCase class
 * Type -> Observable Type that will be returned to this UseCase from the Repository
 * Params -> Parameters that will be passed to the Repository to resolve endpoint request.
 */
abstract class UseCase<Type, in Params>
        where Type : Any {
    abstract fun For(params:Params):Single<Type>
}