package com.example.mygithubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: FavUser)

    @Delete
    fun delete(favUser: FavUser)

    @Query("SELECT * from FavUser ORDER BY username ASC")
    fun getAllFavUser(): LiveData<List<FavUser>>

    @Query("SELECT * FROM FavUser WHERE username = :username")
    fun getFavUserByUsername(username: String): LiveData<FavUser>

    @Query("SELECT EXISTS (SELECT 1 FROM FavUser WHERE username = :username)")
    fun checkIfUserExists(username: String): LiveData<Boolean>
}