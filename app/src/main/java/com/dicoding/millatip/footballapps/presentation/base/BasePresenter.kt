package com.dicoding.millatip.footballapps.presentation.base

open class BasePresenter<V : BaseView> : IBasePresenter<V> {
    var view: V? = null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
    }

}