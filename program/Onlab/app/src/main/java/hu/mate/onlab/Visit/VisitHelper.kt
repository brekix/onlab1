package hu.mate.onlab.Visit

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object VisitHelper {

    // JSON KEYS (not VALUES)
    val KEY_SENDER = "sender"
    val KEY_TITLE = "title"
    val KEY_DETAILS = "details"
    val KEY_TIME = "time"

    // MainActivity = not used
    fun getMoviesFromJson(fileName: String, context: Context): ArrayList<VisitData> {

        val visits = ArrayList<VisitData>()

        try {
            // Load the JSONArray from the file
            val jsonString = loadJsonFromFile(fileName, context)
            val jsonObject = JSONObject(jsonString)
            val jsonArray = jsonObject.getJSONArray("visits")

            // Create the list of Movies
            for (index in 0 until jsonArray.length()) {
                val sender = jsonArray.getJSONObject(index).getString(KEY_SENDER)
                val title = jsonArray.getJSONObject(index).getString(KEY_TITLE)
                val details = jsonArray.getJSONObject(index).getString(KEY_DETAILS)
                val time = jsonArray.getJSONObject(index).getString(KEY_TIME)
                visits.add(VisitData(sender, title, details, time))
            }
        } catch (e: JSONException) {
            return visits
        }

        return visits
    }

    // used here only
    private fun loadJsonFromFile(filename: String, context: Context): String {
        var json = ""

        try {
            val input = context.assets.open(filename)
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            json = buffer.toString(Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return json
    }
}