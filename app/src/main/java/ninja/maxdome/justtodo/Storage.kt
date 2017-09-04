package ninja.maxdome.justtodo

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.*
import java.util.*


object Storage: Activity(){
    // private val PREFS_NAME = "MyPrefsFile"
    private val LOG_TAG = Storage::class.java.simpleName


    fun save(context: Context, tasks: List<ListEntry>?,PREFS_NAME: String){
        var fos: FileOutputStream? = null
        var oos: ObjectOutputStream? = null

        try {
            fos = context.openFileOutput(PREFS_NAME, Context.MODE_PRIVATE)
            oos = ObjectOutputStream(fos)
            oos.writeObject(tasks)
        } catch (e: Exception){
            Log.e(LOG_TAG, "Could not save file.")
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
                oos?.close()
            } catch (e: Exception){
                Log.e(LOG_TAG, "Could not close file.")
                e.printStackTrace()
            }
        }
    }

    fun retrive(context: Context, PREFS_NAME: String) : MutableList<ListEntry>?{
        var tasks: MutableList<ListEntry>? = ArrayList()

        var fis: FileInputStream? = null
        var ois: ObjectInputStream? = null

        try{
            fis = context.openFileInput(PREFS_NAME)
            ois = ObjectInputStream(fis)

            tasks = ois?.readObject() as MutableList<ListEntry>
        } catch (e: Exception){
            Log.e(LOG_TAG, "Could not read from the file.")
            e.printStackTrace()
        } finally {
            try {
                ois?.close()
                fis?.close()
            } catch (e: Exception){
                Log.e(LOG_TAG, "Could not close the file.")
                e.printStackTrace()
            }
        }

        return tasks
    }
}