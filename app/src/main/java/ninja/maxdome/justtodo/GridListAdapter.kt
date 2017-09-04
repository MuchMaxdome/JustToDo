package ninja.maxdome.justtodo

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Maximilian on 24.08.2017.
 */
class GridListAdapter(val context: Context,val intent: Intent, val mValues: MutableList<ListEntry>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val ADD_TASK_REQUEST = 1

    val ITEM_TYPE_NORMAL = 0
    val ITEM_TYPE_ADD = 1

    init{
        if (mValues.isEmpty()){
            mValues.add(0, ListEntry("Start", "", "", ""))
            notifyItemInserted(0)
        }
    }

    inner class AddHolder(var layout: View) : RecyclerView.ViewHolder(layout){
        var txt:  TextView
        var back: AddView

        init{
            txt  = itemView.findViewById<TextView>(R.id.ex_text)
            back = itemView.findViewById<AddView>(R.id.ex_back)

            back.setOnClickListener { jumpEntryAddActivity() }
        }
    }
    inner class NormalHolder(var layout: View) : RecyclerView.ViewHolder(layout){
        var txt:  TextView
        var dateTime: TextView
        var back: ExampleView

        init{
            txt  = itemView.findViewById<TextView>(R.id.ex_text)
            back = itemView.findViewById<ExampleView>(R.id.ex_back)
            dateTime = itemView.findViewById<TextView>(R.id.ex_date)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val itemType = getItemViewType(position)

        val name = mValues[position].shortInfo

        if (itemType == ITEM_TYPE_NORMAL){
            (holder as NormalHolder).txt.text = name

            val date: String? = mValues[position].date
            val time: String? = mValues[position].time

            if (date != null){
                if (time != null) holder.dateTime.text = "$date, $time"
                else              holder.dateTime.text = "$date"
            }
            else if(time != null) holder.dateTime.text = "$time"

            holder.back.setOnClickListener { remove(position) }
        }
        else if (itemType == ITEM_TYPE_ADD) (holder as AddHolder).txt.text = name
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // weise dem ViewHolder das ParentLayout zu - über LayoutInflater

        if (viewType == ITEM_TYPE_NORMAL){
            val inflater = LayoutInflater.from(parent.context)
            val v = inflater.inflate(R.layout.ex_layout, parent, false)
            val vh = NormalHolder(v)
            return vh
        }
        else {
            val inflater = LayoutInflater.from(parent.context)
            val v = inflater.inflate(R.layout.add_layout, parent, false)
            val vh = AddHolder(v)
            return vh
        }
    }

    fun add(position: Int, item: ListEntry) {
        mValues.add(position+1, item)
        notifyItemInserted(position+1)

        for (i in position..mValues.size-1){
            notifyItemChanged(i)
        }
    }

    fun remove(position: Int) {
        mValues.removeAt(position)
        notifyItemRemoved(position)

        // notify all changed holders to change the location for their remove method
        for (i in position..mValues.size-1){
            notifyItemChanged(i)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ITEM_TYPE_ADD else ITEM_TYPE_NORMAL
    }

    fun jumpEntryAddActivity(){
        (context as AppCompatActivity).startActivityForResult(intent, ADD_TASK_REQUEST)
    }
}