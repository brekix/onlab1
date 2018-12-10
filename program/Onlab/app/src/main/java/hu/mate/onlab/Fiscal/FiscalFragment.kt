package hu.mate.onlab.Fiscal

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import hu.mate.onlab.Home.QueryActivity
import hu.mate.onlab.R
import kotlinx.android.synthetic.main.fiscal_fragment.view.*

class FiscalFragment : Fragment() {

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Creates the view controlled by the fragment
        val view = inflater.inflate(R.layout.fiscal_fragment, container, false)
        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val ratingTextView = view.findViewById<TextView>(R.id.ratingTextView)
        val posterImageView = view.findViewById<ImageView>(R.id.posterImageView)
        val overviewTextView = view.findViewById<TextView>(R.id.overviewTextView)

        // Retrieve and display the movie data from the Bundle
        val args = arguments
        titleTextView.text = args?.getString(FiscalHelper.KEY_TITLE)
        ratingTextView.text = String.format("Rate is %d/10", args?.getInt(FiscalHelper.KEY_RATING))
        overviewTextView.text = args?.getString(FiscalHelper.KEY_OVERVIEW)

        // Download the image and display it using Picasso
        Picasso.with(activity)
            .load(resources.getIdentifier(args?.getString(FiscalHelper.KEY_POSTER_URI), "drawable", activity?.packageName))
            .into(posterImageView)


        Toast.makeText(activity,"Fiscal result", Toast.LENGTH_SHORT).show()

        // FAB
        view.email_fab.setOnClickListener { view ->

            // extension method
            Snackbar.make(view, "Email sent for approval ..",Snackbar.LENGTH_LONG).setTextColor(Color.WHITE).show()
        }

        return view
    }

    //Created this kotlin extention method
    //any name can be eg. setTextColor or withTextColor
    fun Snackbar.setTextColor(color: Int): Snackbar {
        val tv = view.findViewById(android.support.design.R.id.snackbar_text) as TextView
        tv.setTextColor(color)
        return this
    }

    fun String.nagbetu() : String {
        return this.toUpperCase()
    }

    /**
     * COMPANION
     */
    companion object {

        // Method for creating new instances of the fragment
        // passes Array
        fun newInstance(fiscalData: ArrayList<FiscalData>): FiscalFragment {
            // Store the movie data in a Bundle object
            val args = Bundle()

            val wanted = when (QueryActivity.CUSTOMER) {
                "Tesla" -> 0
                "BMW" -> 1
                else -> 2
            }
            args.putString(FiscalHelper.KEY_TITLE, fiscalData[wanted].title)
            args.putInt(FiscalHelper.KEY_RATING, fiscalData[wanted].rating)
            args.putString(FiscalHelper.KEY_POSTER_URI, fiscalData[wanted].posterUri)
            args.putString(FiscalHelper.KEY_OVERVIEW, fiscalData[wanted].overview)

            // Create a new FiscalFragment and set the Bundle as the arguments
            // to be retrieved and displayed when the view is created
            val fragment = FiscalFragment()
            fragment.arguments = args
            return fragment
        }
    }


}
