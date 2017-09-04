package ninja.maxdome.justtodo

// to make this saveable
import java.io.Serializable

/**
 * Created by Maximilian on 27.08.2017.
 */
data class ListEntry(var shortInfo: String, var  description: String?, var date: String?, var time: String?) : Serializable