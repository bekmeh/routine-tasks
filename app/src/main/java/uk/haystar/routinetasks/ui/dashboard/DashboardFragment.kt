package uk.haystar.routinetasks.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.haystar.routinetasks.R
import uk.haystar.routinetasks.viewmodel.RoutineViewModel

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private lateinit var routineViewModel: RoutineViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter =
            RoutineListAdapter(context!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        routineViewModel = ViewModelProviders.of(this).get(RoutineViewModel::class.java)
        routineViewModel.allRoutines.observe(this, Observer { routine ->
            // Update the cached copy of the routine in the adapter.
            routine?.let { adapter.setRoutines(it) }
        })

        return root
    }
}