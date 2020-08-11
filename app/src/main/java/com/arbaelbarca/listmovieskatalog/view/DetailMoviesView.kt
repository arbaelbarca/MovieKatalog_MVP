package com.arbaelbarca.listmovieskatalog.view

import com.arbaelbarca.listmovieskatalog.model.modeldetailmovies.GenresItem
import com.arbaelbarca.listmovieskatalog.model.modeldetailmovies.ResponseDetailMovies
import com.arbaelbarca.listmovieskatalog.model.modelreviewmovies.ResultsItemReviews

import java.util.ArrayList

interface DetailMoviesView {

    interface presenter {
        fun onDestroy()

        fun requestFromDataServer(movieId: String)

        fun refreshData(type: String)
    }

    interface MainView {

        fun showProgress()

        fun hideProgress()

        fun setDataTags(responseDetailMovies: ResponseDetailMovies)

        fun setDataReview(resultsItemArrayList: ArrayList<ResultsItemReviews>)

        fun onResponseFailure(throwable: Throwable)
    }


    interface GetNoticeIntractorDetailMovies {

        interface OnFinishedListener {
            fun onFinished(responseDetailMovies: ResponseDetailMovies)

            fun onFailure(t: Throwable)

            fun setDataTags(genresItems: ArrayList<GenresItem>)

            fun setDataReview(resultsItemReviews: ArrayList<ResultsItemReviews>)

        }

        fun getDetailMovies(onFinishedListener: OnFinishedListener, movie_id: String)

        fun getDetailReviews(onFinishedListener: OnFinishedListener, movie_id: String)


    }

}
