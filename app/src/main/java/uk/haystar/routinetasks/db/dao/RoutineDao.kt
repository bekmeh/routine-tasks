package uk.haystar.routinetasks.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.haystar.routinetasks.db.entity.RoutineEntity

@Dao
interface RoutineDao {

    @Query("SELECT * FROM routine_table")
    fun getAll(): LiveData<List<RoutineEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(routineEntity: RoutineEntity)

    @Query("DELETE FROM routine_table where ID = :routineId")
    suspend fun delete(routineId: Int)

}