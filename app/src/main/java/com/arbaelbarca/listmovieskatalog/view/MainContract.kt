package com.arbaelbarca.listmovieskatalog.view

import com.arbaelbarca.listmovieskatalog.model.movies.ResultsItem

import java.util.ArrayList

interface MainContract {

    interface presenter {
        fun onDestroy()

        fun requestFromDataServer()

        fun refreshData(type: String)
    }

    interface MainView {

        fun showProgress()

        fun hideProgress()

        fun setDataToRecyclerView(resultsItemArrayList: ArrayList<ResultsItem>)

        fun onResponseFailure(throwable: Throwable)

    }

    interface GetNoticeIntractor {

        interface OnFinishedListener {
            fun onFinished(resultsItems: ArrayList<ResultsItem>)

            fun onFailure(t: Throwable)
        }

        fun getNoticeArrayList(onFinishedListener: OnFinishedListener, textType: String)

    }
}
