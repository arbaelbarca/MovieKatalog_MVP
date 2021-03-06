package com.arbaelbarca.listmovieskatalog.presenter.listmoviespresenter

import com.arbaelbarca.listmovieskatalog.model.movies.ResultsItem
import com.arbaelbarca.listmovieskatalog.view.MainContract
import java.util.*

class ListMoviePresenter(private var mainView: MainContract.MainView?, private val getNoticeIntractor: MainContract.GetNoticeIntractor) : MainContract.presenter, MainContract.GetNoticeIntractor.OnFinishedListener {

    override fun onDestroy() {
        mainView = null
    }


    override fun requestFromDataServer() {
        getNoticeIntractor.getNoticeArrayList(this, "popular")
    }

    override fun refreshData(type: String) {
        getNoticeIntractor.getNoticeArrayList(this, type)

    }


    override fun onFinished(resultsItemArrayList: ArrayList<ResultsItem>) {
        if (mainView != null) {
            mainView!!.setDataToRecyclerView(resultsItemArrayList)
            mainView!!.hideProgress()
        }
    }

    override fun onFailure(t: Throwable) {
        if (mainView != null)
            mainView!!.onResponseFailure(t)
    }
}
