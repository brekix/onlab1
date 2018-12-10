package hu.mate.onlab.Home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import hu.mate.onlab.Fiscal.FiscalFragment
import hu.mate.onlab.Fiscal.FiscalHelper
import hu.mate.onlab.R
import hu.mate.onlab.SQL.SQLFragment
import hu.mate.onlab.Visit.VisitFragment
import hu.mate.onlab.Visit.VisitHelper

class HostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_activity)

        val value = intent.getStringExtra("key")
        val data = intent.getStringExtra("data")

        //Toast.makeText(this, "Fragment : " + value, Toast.LENGTH_SHORT).show()

        // NEW
        val newFragment = when (value) {
            "fiscal" -> {
                val fiscalArray = FiscalHelper.getMoviesFromJson("customer_fiscal.json", this)
                FiscalFragment.newInstance(fiscalArray)
            }
            "visit" -> {
                val visitArray = VisitHelper.getMoviesFromJson("customer_visit.json", this)
                VisitFragment.newInstance(visitArray)
            }
            else -> {
                SQLFragment.newInstance(data)
            }
        }
        /**
         * REPLACE FRAGMENT
         */
        val ft = supportFragmentManager.beginTransaction()
        // Replace whatever is in the fragment_container view with this fragment
        ft.replace(R.id.fragment_container, newFragment)
        // Add the transaction to the back stack so the user can navigate back
        ft.addToBackStack(null)
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        ft.commit()

    }


}