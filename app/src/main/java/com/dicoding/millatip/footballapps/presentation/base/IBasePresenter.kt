package com.dicoding.millatip.footballapps.presentation.base

interface IBasePresenter <V : BaseView>{
    fun onAttach(view: V)
    fun onDetach()

}