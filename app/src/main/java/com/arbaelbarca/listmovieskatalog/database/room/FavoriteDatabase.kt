package com.arbaelbarca.listmovieskatalog.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arbaelbarca.listmovieskatalog.model.movies.ResultsItem

@Database(entities = [ResultsItem::class], version = 4, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
