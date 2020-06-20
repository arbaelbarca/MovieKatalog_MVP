package com.arbaelbarca.listmovieskatalog.view;

import com.arbaelbarca.listmovieskatalog.model.modeldetailmovies.GenresItem;
import com.arbaelbarca.listmovieskatalog.model.modeldetailmovies.ResponseDetailMovies;
import com.arbaelbarca.listmovieskatalog.model.modelreviewmovies.ResultsItemReviews;

import java.util.ArrayList;

public interface DetailMoviesView {

    interface presenter {
        void onDestroy();

        void requestFromDataServer(String movieId);

        void refreshData(String type);
    }

    interface MainView {

        void showProgress();

        void hideProgress();

        void setDataTags(ResponseDetailMovies responseDetailMovies);

        void setDataReview(ArrayList<ResultsItemReviews> resultsItemArrayList);

        void onResponseFailure(Throwable throwable);
    }


    interface GetNoticeIntractorDetailMovies {

        interface OnFinishedListener {
            void onFinished(ResponseDetailMovies responseDetailMovies);

            void onFailure(Throwable t);

            void setDataTags(ArrayList<GenresItem> genresItems);

            void setDataReview(ArrayList<ResultsItemReviews> resultsItemReviews);

        }

        void getDetailMovies(OnFinishedListener onFinishedListener, String movie_id);

        void getDetailReviews(OnFinishedListener onFinishedListener, String movie_id);


    }

}