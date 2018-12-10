package hu.mate.onlab.Fiscal

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object FiscalHelper {

    // JSON KEYS (not VALUES)
    val KEY_TITLE = "title"
    val KEY_RATING = "rating"
    val KEY_POSTER_URI = "posterUri"
    val KEY_OVERVIEW = "overview"

    // MainActivity = not used
    fun getMoviesFromJson(fileName: String, context: Context): ArrayList<FiscalData> {

        val movies = ArrayList<FiscalData>()

        try {
            // Load the JSONArray from the file
            val jsonString = loadJsonFromFile(fileName, context)
            val json = JSONObject(jsonString)
            val jsonMovies = json.getJSONArray("fiscal")

            // Create the list of Movies
            for (index in 0 until jsonMovies.length()) {
                val movieTitle = jsonMovies.getJSONObject(index).getString(KEY_TITLE)
                val movieRating = jsonMovies.getJSONObject(index).getInt(KEY_RATING)
                val moviePosterUri = jsonMovies.getJSONObject(index).getString(KEY_POSTER_URI)
                val movieOverview = jsonMovies.getJSONObject(index).getString(KEY_OVERVIEW)
                movies.add(FiscalData(movieTitle, movieRating, moviePosterUri, movieOverview))
            }
        } catch (e: JSONException) {
            return movies
        }

        return movies
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