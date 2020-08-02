package uk.haystar.routinetasks.db.repository

import androidx.lifecycle.LiveData
import uk.haystar.routinetasks.db.dao.RoutineDao
import uk.haystar.routinetasks.db.entity.RoutineEntity

class RoutineRepository(private val routineDao: RoutineDao) {

    val allRoutines: LiveData<List<RoutineEntity>> = routineDao.getAll()

    suspend fun insert(routineEntity: RoutineEntity) {
        routineDao.insert(routineEntity)
    }

    suspend fun delete(routineId: Int) {
        routineDao.delete(routineId)
    }
}