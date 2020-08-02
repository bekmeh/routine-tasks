package uk.haystar.routinetasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.haystar.routinetasks.db.AppDatabase
import uk.haystar.routinetasks.db.entity.RoutineEntity
import uk.haystar.routinetasks.db.repository.RoutineRepository

class RoutineViewModel(application: Application): AndroidViewModel(application) {

    private val repository: RoutineRepository

    // Using LiveData and caching what getAll() returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allRoutines: LiveData<List<RoutineEntity>>

    init {
        val routineDao = AppDatabase.getDatabase(application, viewModelScope).routineDao()

        repository = RoutineRepository(routineDao)

        allRoutines = repository.allRoutines
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(routine: RoutineEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(routine)
    }

}