package com.app.mymainapp.localdatabaseservice

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.mymainapp.localdatabaseservice.entities.StudentEntity

/** Created by Jishnu P Dileep on 27-05-2021 */

@Dao
interface AppLocalRoomDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: StudentEntity): Long

    @Query("select * From student ORDER BY studentId ASC")
    suspend fun fetch(): List<StudentEntity>
}