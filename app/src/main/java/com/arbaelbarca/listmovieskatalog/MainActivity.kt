package com.arbaelbarca.listmovieskatalog

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import butterknife.ButterKnife
import com.arbaelbarca.listmovieskatalog.adapter.AdapterListMovies
import com.arbaelbarca.listmovieskatalog.baseactivity.BaseActivity
import com.arbaelbarca.listmovieskatalog.database.room.FavoriteDatabase
import com.arbaelbarca.listmovieskatalog.model.movies.ResultsItem
import com.arbaelbarca.listmovieskatalog.onclick.OnClickItem
import com.arbaelbarca.listmovieskatalog.presenter.listmoviespresenter.ListMoviePresenter
import com.arbaelbarca.listmovieskatalog.presenter.listmoviespresenter.ListMoviesPresenterImpl
import com.arbaelbarca.listmovieskatalog.ui.activity.DetailMovieActivity
import com.arbaelbarca.listmovieskatalog.ui.activity.FavoriteMovieActivity
import com.arbaelbarca.listmovieskatalog.utils.Constants
import com.arbaelbarca.listmovieskatalog.view.MainContract
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*

class MainActivity : BaseActivity(), MainContract.MainView, PopupMenu.OnMenuItemClickListener {

    private lateinit var presenter: ListMoviePresenter
    private lateinit var adapterListMovies: AdapterListMovies

    private lateinit var linearLayoutManager: LinearLayoutManager


    var resultsItemsList = ArrayList<ResultsItem>()
    private lateinit var popupMenu: PopupMenu


    companion object {
        var favoriteDatabase: FavoriteDatabase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initial()

        btnCategory.setOnClickListener { view ->
            popupMenu.show()

        }


    }


    private fun getListType(type: String) {
        showProgress()
        resultsItemsList.clear()
        presenter.refreshData(type)
        supportActionBar?.title = type
    }

    private fun initial() {

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Popular"

        favoriteDatabase = Room.databaseBuilder(applicationContext, FavoriteDatabase::class.java, "myfavdbnew").allowMainThreadQueries().build()

        presenter = ListMoviePresenter(this, ListMoviesPresenterImpl(this@MainActivity))
        presenter.requestFromDataServer()

        popupMenu = PopupMenu(this@MainActivity, btnCategory)
        popupMenu.menuInflater.inflate(R.menu.item_more_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(this)

        adapterListMovies = AdapterListMovies(this)

        linearLayoutManager = LinearLayoutManager(this)
        rvListUser.layoutManager = linearLayoutManager
        rvListUser.setHasFixedSize(true)
        adapterListMovies.setOnClickItem(object : OnClickItem {
            override fun clickItemDetail(pos: Int) {
                val intent = Intent(applicationContext, DetailMovieActivity::class.java)
                intent.putExtra(Constants.DATA_ITEMS, resultsItemsList[pos])
                startActivity(intent)
            }

        })

    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setDataToRecyclerView(resultsItemArrayList: ArrayList<ResultsItem>) {
        resultsItemsList = resultsItemArrayList
        adapterListMovies.setMovies(resultsItemsList)
        rvListUser.adapter = adapterListMovies
    }

    override fun onResponseFailure(throwable: Throwable) {
        presenter.onFailure(throwable)
        progressBar.visibility = View.GONE
        showToast(getString(R.string.text_server_eror))
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


    override fun onMenuItemClick(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.ic_upcoming -> getListType(getString(R.string.text_upcoming_new))
            R.id.ic_nowplaying -> getListType(getString(R.string.text_nowplaying_api))
            R.id.ic_popular -> getListType(getString(R.string.text_popular_api))
            R.id.ic_toprated -> getListType(getString(R.string.text_toprated_api))
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.item_menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.ic_love_fav) {
            startActivity(Intent(applicationContext, FavoriteMovieActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}
