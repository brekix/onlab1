package hu.mate.onlab.SQL

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.mate.onlab.R
import kotlinx.android.synthetic.main.sql_fragment.view.*

class SQLFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val args = arguments

        // Creates the view controlled by the fragment
        val view = inflater.inflate(R.layout.sql_fragment, container, false)

        // Activity passes SQL result via Bundle
        view.query_body.text = args?.getString(SQLHelper.KEY_DATA,"fragment no data")

        //Itt is futhatna, de inkább argument-el adom át, hogy gyakoroljam
        //view.query_body.text = Test()

        return view
    }

    /*
    companion object {
        // Method for creating new instances of the fragment
        fun newInstance(data:String) = SQLFragment().apply {

            // Store the movie data in a Bundle object
            val args = Bundle()
            args.putString(SQLHelper.KEY_DATA,data)
            arguments = args
        }
    }
    */

    companion object {
        // Method for creating new instances of the fragment
        fun newInstance(data:String): SQLFragment {

            // Store the movie data in a Bundle object
            val args = Bundle()
            args.putString(SQLHelper.KEY_DATA,data)

            // Create a new FiscalFragment and set the Bundle as the arguments
            // to be retrieved and displayed when the view is created
            val fragment = SQLFragment()
            fragment.arguments = args
            return fragment
        }
    }

}