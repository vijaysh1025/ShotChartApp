package com.vijay.nbashottracker.core.functional

sealed class Fork<out L, out R>{
    data class Left<out L>(val a:L):Fork<L,Nothing>()
    data class Right<out R>(val b:R):Fork<Nothing, R>()

    val isRight get()= this is Right<R>
    val isLeft get()=this is Left<L>

    fun <L> left(a:L)=Fork.Left(a)
    fun <R> right(b:R) = Fork.Right(b)

    fun fork(fnL:(L)->Any, fnR:(R)->Any):Any=
            when (this){
                is Left -> fnL(a)
                is Right -> fnR(b)
            }
}