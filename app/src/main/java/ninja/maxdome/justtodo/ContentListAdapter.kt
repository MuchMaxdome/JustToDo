package ninja.maxdome.justtodo

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ninja.maxdome.justtodo.gridRecyclerList.AddView

/**
 * Created by Maximilian on 01.09.2017.
 */
class ContentListAdapter(val context: Context, var mUpperList: MutableList<UpperListEntry>): RecyclerView.Adapter<ContentListAdapter.ViewHolder>() {
    private val LAYOUT_TASK_REQUEST = 1

    override fun onBindViewHolder(holder: ContentListAdapter.ViewHolder?, position: Int) {
        val header = mUpperList[position].Header

        holder?.header?.text = header
    }

    override fun getItemCount(): Int {
        return this.mUpperList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContentListAdapter.ViewHolder {
        // weise dem ViewHolder das ParentLayout zu - über LayoutInflater
            val inflater = LayoutInflater.from(parent?.context)
            val v = inflater.inflate(R.layout.underlined_recycler_content, parent, false)
            val vh = ViewHolder(v)
            return vh
    }

    inner class ViewHolder(var layout: View): RecyclerView.ViewHolder(layout){
        var header: TextView
        var back: View

        init{
            header  = itemView.findViewById<TextView>(R.id.lilList_headline)
            back    = itemView.findViewById<AddView>(R.id.lilList)

            back.setOnClickListener { jumpSingleListActivity() }
        }
    }

    fun add(position: Int, item: UpperListEntry) {
        mUpperList.add(position, item)
        notifyItemInserted(position)

        // notify all changed holders to change the location for their remove method
        for (i in position..mUpperList.size-1){
            notifyItemChanged(i)
        }
    }

    fun remove(position: Int) {
        mUpperList.removeAt(position)
        notifyItemRemoved(position)

        // notify all changed holders to change the location for their remove method
        for (i in position..mUpperList.size-1){
            notifyItemChanged(i)
        }
    }

    private fun jumpSingleListActivity(){
        val jumpintent: Intent = Intent(context, SingleListActivity::class.java)
        (context as AppCompatActivity).startActivityForResult(jumpintent, LAYOUT_TASK_REQUEST)
    }

    fun saveData(position: Int){
        //TODO
    }
}