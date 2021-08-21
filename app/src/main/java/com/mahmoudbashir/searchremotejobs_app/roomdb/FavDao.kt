package com.mahmoudbashir.searchremotejobs_app.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mahmoudbashir.searchremotejobs_app.models.FavouriteJob
import retrofit2.http.DELETE

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteJob(job:FavouriteJob)
    @Query("SELECT * FROM fav_job ORDER BY id DESC")
    fun getAllFav():LiveData<List<FavouriteJob>>
    @Delete
    suspend fun deleteFaveJob(job: FavouriteJob)

}