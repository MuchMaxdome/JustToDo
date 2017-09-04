package ninja.maxdome.justtodo

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.underlined_recycler_content.view.*
import ninja.maxdome.justtodo.gridRecyclerList.GridListAdapter

class MainActivity : AppCompatActivity() {
    private var mList: MutableList<ListEntry> = ArrayList()

    private var mTestRecycler: RecyclerView? = null
    private var mTestAdapter: GridListAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    private val ADD_TASK_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // read the SaveFile
        val tasks = Storage.retrive(this@MainActivity, "wow")

        if (tasks != null && (mTestAdapter?.mValues?.isEmpty() ?: true)) {
            debugToast("It worked.")
            mList = tasks
        }


        mTestAdapter = GridListAdapter(this, mList)

        // create the contents of RecyclerView and its Adapter
        mTestRecycler = this.lilList.lilList_RecycleHorizontal
        mTestRecycler!!.setHasFixedSize(true)

        // bind the layoutManager on the RecyclerView
        mLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        mTestRecycler!!.layoutManager = mLayoutManager

        // now bind RecyclerView with its Adapter and LayoutManager
        mTestRecycler!!.adapter = mTestAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_TASK_REQUEST && resultCode == Activity.RESULT_OK) {
            // 3 - The user entered a task. Add a task to the list.
            val info: String = data!!.getStringExtra("shortInfo")
            val description: String? = data.getStringExtra("description")
            val date: String? = data.getStringExtra("date")
            val time: String? = data.getStringExtra("time")


            mTestAdapter!!.add(0, ListEntry(info,description,date,time))
        }
    }

    private fun debugToast(text: String){
        val context = applicationContext
        val text = text
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }

    override fun onPause() {
        super.onPause()
        Storage.save(this, mTestAdapter?.mValues,"wow")
    }

    override fun onStop() {
        super.onStop()
        Storage.save(this, mTestAdapter?.mValues,"wow")
    }

    override fun onResume() {
        super.onResume()
    }
}
