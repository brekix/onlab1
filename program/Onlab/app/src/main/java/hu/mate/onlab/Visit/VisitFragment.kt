package hu.mate.onlab.Visit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.mate.onlab.R

//import kotlinx.android.synthetic.main.visit_fragment.view.*
import kotlinx.android.synthetic.main.visit_fragment.view.*


class VisitFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val args = arguments

        // Creates the view controlled by the fragment
        val view = inflater.inflate(R.layout.visit_fragment, container, false)

        //val mRecyclerView = container?.rv_visit
        val mRecyclerView = view.rv_visit

        //mRecyclerView?.layoutManager = LinearLayoutManager(container?.context)
        mRecyclerView.layoutManager = LinearLayoutManager(inflater.context)
        mRecyclerView.addItemDecoration(DividerItemDecoration(inflater.context, DividerItemDecoration.VERTICAL))
        mRecyclerView.adapter = VisitAdapter(inflater.context, VISITS)

        return view // mRecyclerView
    }

    companion object {

        lateinit var VISITS : ArrayList<VisitData>

        // Method for creating new instances of the fragment
        fun newInstance(visits: ArrayList<VisitData>): VisitFragment {

            // Store the movie data in a Bundle object
            val args = Bundle()
            VISITS = visits

            // Create a new FiscalFragment and set the Bundle as the arguments
            // to be retrieved and displayed when the view is created
            val fragment = VisitFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
