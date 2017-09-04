package ninja.maxdome.justtodo

import android.app.Activity
import android.content.Context
import android.util.Log
import java.io.*
import java.lang.reflect.Array
import java.util.*


object UpperListStorage: Activity(){
    // private val PREFS_NAME = "MyPrefsFile"
    private val LOG_TAG = Storage::class.java.simpleName


    fun save(context: Context, tasks: List<UpperListEntry>?, PREFS_NAME: String){
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

    fun retrive(context: Context, PREFS_NAME: String) : MutableList<UpperListEntry>?{
        var tasks: MutableList<UpperListEntry>? = ArrayList()

        var fis: FileInputStream? = null
        var ois: ObjectInputStream? = null

        try{
            fis = context.openFileInput(PREFS_NAME)
            ois = ObjectInputStream(fis)

            tasks = ois?.readObject() as MutableList<UpperListEntry>
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

    fun keyGenerator(context: Context): String{
        // we want Storage keys with 8 tokens - 2 numbers - 3 letters - 5 numbers
        // they are stored in the list with the String Main

        val miniList: MutableList<UpperListEntry>? = this.retrive(context,"Main")

        return keyGeneratorHelper(miniList)
    }

    private fun keyGeneratorHelper(miniList: MutableList<UpperListEntry>?): String{
        // create an Array to save the chars
        val sArray: kotlin.Array<String?> = kotlin.Array(8, {_ -> null})
        // first generate a key
        var counter = 0
        val rdm: Random = Random()
        // first 2 numbers
        for (i in 0..1) sArray[counter++] = i.toString()
        // 3 letters
        for (i in 0..2) sArray[counter++] = (rdm.nextInt(26)+97).toChar().toString()
        // last 5 numbers
        for (i in 0..4) sArray[counter++] = i.toString()

        val finalString = sArray[0] + sArray[1] + sArray[2] + sArray[3] + sArray[4] + sArray[5] + sArray[6] +sArray[7]

        var isNew = true
        for (i in 0..miniList!!.size-1){ if (finalString == miniList[i].storageKey) isNew = false; break }

        if (isNew) return finalString
        // is recursive and slow, but this case will probably never happen
        else return keyGeneratorHelper(miniList)
    }
}