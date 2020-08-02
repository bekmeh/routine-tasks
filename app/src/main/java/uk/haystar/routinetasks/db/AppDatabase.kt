package uk.haystar.routinetasks.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uk.haystar.routinetasks.db.dao.RoutineDao
import uk.haystar.routinetasks.db.entity.RoutineEntity

// Note: When you modify the database schema, you'll need to update the version number and define
// a migration strategy
@Database(entities = [ RoutineEntity::class ], version = 1, exportSchema = false)
public abstract class AppDatabase: RoomDatabase() {

    abstract fun routineDao(): RoutineDao

    companion object {
        // We want a singleton database, so we only have one instance at any one time
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(RoutineDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }

    private class RoutineDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val routineDao = database.routineDao()
                    routineDao.delete(1)
                    routineDao.delete(2)
                    routineDao.delete(3)
                    routineDao.delete(4)
                    routineDao.delete(5)
                    routineDao.insert(RoutineEntity(1, "Bloop0", 1))
                    routineDao.insert(RoutineEntity(2, "Bloop1", 2))
                    routineDao.insert(RoutineEntity(3, "Bloop2", 3))
                    routineDao.insert(RoutineEntity(4, "Bloop3", 4))
                    routineDao.insert(RoutineEntity(5, "Bloop4", 5))
                }
            }
        }
    }
}