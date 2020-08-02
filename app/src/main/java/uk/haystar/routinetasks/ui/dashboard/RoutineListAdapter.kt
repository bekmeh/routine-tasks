package uk.haystar.routinetasks.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.haystar.routinetasks.R
import uk.haystar.routinetasks.db.entity.RoutineEntity

class RoutineListAdapter internal constructor(context: Context): RecyclerView.Adapter<RoutineListAdapter.RoutineViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    // Cached copy of routines
    private var routines = emptyList<RoutineEntity>()

    inner class RoutineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val routineItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return RoutineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val current = routines[position]
        holder.routineItemView.text = current.name
    }

    internal fun setRoutines(routines: List<RoutineEntity>) {
        this.routines = routines
        notifyDataSetChanged()
    }

    override fun getItemCount() = routines.size

}