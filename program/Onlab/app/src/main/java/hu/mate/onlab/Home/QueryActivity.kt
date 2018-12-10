package hu.mate.onlab.Home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.MenuBuilder
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import hu.mate.onlab.R
import kotlinx.android.synthetic.main.content_filter.*
import kotlinx.android.synthetic.main.home_appbar_content.*
import kotlinx.android.synthetic.main.home_drawer.*
import java.sql.Connection
import java.sql.DriverManager

class QueryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {



    /**
     * Activity : OnCreate()
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_drawer)

        // TOOLBAR = 3 DOTS
        // Set the toolbar as support action bar
        setSupportActionBar(idea_toolbar)
        // colored icons = not used
        //idea_toolbar.overflowIcon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_tune_green)

        // NAVIGATION = HAMBURGER
        // colored icons = red heart icon
        idea_hamburger.itemIconTintList = null
        // needs v4 in drawer layout \ idea_drawer
        val toggle = ActionBarDrawerToggle(this, idea_drawer, idea_toolbar, R.string.main_navigation_drawer_open, R.string.main_navigation_drawer_close )
        toggle.syncState()
        idea_drawer.addDrawerListener(toggle)
        idea_hamburger.setNavigationItemSelectedListener(this)

        // DATA
        val customerArray = resources.getStringArray(R.array.customer_list).sortedArray()

        // AUTOCOMPLETE FILTER
        // var .. because write back from list
        var theFilter = findViewById<EditText>(R.id.searchFilter)
        val list = findViewById<ListView>(R.id.theList)
        val adapterArray = ArrayAdapter(this, R.layout.item_customer_list, customerArray)
        list.adapter = adapterArray
        theFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapterArray.getFilter().filter(charSequence)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        list.setOnItemClickListener { parent, view, position, id ->
            run {
                CUSTOMER = parent.getItemAtPosition(position).toString()
                val next = Intent(this@QueryActivity, HostActivity::class.java)
                next.putExtra("key", "fiscal")
                next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(next)
            }
        }


    }


    /**
     * Activity : AppCompatActivity()
     */
    // BACK
    override fun onBackPressed() {
        if (idea_drawer.isDrawerOpen(GravityCompat.START)) {
            idea_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // OPTION MENU
    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // ICONS with RESTRICTED API decorator
        if (menu is MenuBuilder) menu.setOptionalIconsVisible(true)
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_dots, menu)
        return true
    }

    // OPTION ACTIONS
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*
        return when (item.itemId) {
            R.id.option_license_action -> {
                val next = Intent(this@QueryActivity, LicenseActivity::class.java)
                next.putExtra("hi", "HI")
                next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(next)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        */
        return super.onOptionsItemSelected(item)
    }


    /**
     * Activity : NavigationView.OnNavigationItemSelectedListener()
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        idea_drawer.closeDrawer(GravityCompat.START)

        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.cust_tovisit_action -> {
                run {
                    val next = Intent(this@QueryActivity, HostActivity::class.java)
                    next.putExtra("key", "visit")
                    next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(next)
                }
                return true
            }
            R.id.sqltest_action -> {
                run {
                    val next = Intent(this@QueryActivity, HostActivity::class.java)
                    next.putExtra("key", "SQL")
                    val sqldata:String = Test()
                    next.putExtra("data",sqldata)
                    next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(next)
                }
                return true
            }
            R.id.option_settings_action -> {
                Toast.makeText(applicationContext,"Under construction ..", Toast.LENGTH_SHORT).show()
                val next = Intent(this@QueryActivity, PrefActivity::class.java)
                next.putExtra("hi", "HI")
                next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(next)
                return true
            }
            R.id.option_about_action -> {
                val next = Intent(this@QueryActivity, AboutActivity::class.java)
                next.putExtra("hi", "HI")
                next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(next)
                return true
            }
            R.id.option_license_action -> {
                val next = Intent(this@QueryActivity, LicenseActivity::class.java)
                next.putExtra("hi", "HI")
                next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(next)
               return true
            }
            R.id.option_exit_action -> {
                // ASYNC !!!!!!!!!
                AlertDialog.Builder(this)
                    .setTitle(R.string.exit_title)
                    .setMessage(R.string.exit_confirm)
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        Toast.makeText(applicationContext,"Logging off ... see you later.", Toast.LENGTH_SHORT).show()
                        // AUTH OK
                        val next = Intent(this@QueryActivity, LoginActivity::class.java)
                        next.putExtra("hi", "HI")
                        next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(next)
                    }
                    .setNegativeButton(android.R.string.no) { _, _ ->
                        Toast.makeText(applicationContext,"continue...", Toast.LENGTH_SHORT).show()
                    }
                    .show()
                return true
            }

        }
        return true
    }

    /**
     * fun
     */
    private fun attemptBrowser() {
        val url = "http://portquiz.net:1433/"
        startActivity(Intent (Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun attemptQuery() {
        // ASYNC
        // TODO - This starts the AsyncTask
        //MyTask().execute("my string parameter")
    }

    /**
     * THIS WORKS ONLY !
     */
    fun Test():String {
        // fix android.os.NetworkOnMainThreadException?
        // https://seagblog.wordpress.com/2017/08/15/connect-android-studio-to-mssql/
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val conn: Connection
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection(
                "jdbc:jtds:sqlserver://192.168.1.114:1433;databaseName=AdventureWorks2016",
                "admin",
                "a"
            )
            val statement = conn.createStatement()
            val queryString = "SELECT TOP (20) ProductID,Name FROM Production.Product"
            val rs = statement.executeQuery(queryString)
            // result
            val sb = StringBuilder()
            while (rs.next()) {
                sb.append(rs.getString(2) + "\n")
            }
            // return
            return sb.toString()
        } catch (e: Exception) {
            //Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
            return "nothing"
        }
    }

    /**
     * ASYNC
     */
    private fun showProgress(show: Boolean) {
        // Animation
        query_progress.isIndeterminate = show
    }
    // -------------------------------------------------------------------------------------------------------
    // TODO - first full featured SYNC code
    // Here is the AsyncTask class:
    //
    // AsyncTask<Params, Progress, Result>.
    //    Params – the type (Object/primitive) you pass to the AsyncTask from .execute()
    //    Progress – the type that gets passed to onProgressUpdate()
    //    Result – the type returns from doInBackground()
    // Any of them can be String, Integer, Void, etc.
    //
    // TODO - My Modifications and Comments
    // https://stackoverflow.com/questions/25647881/android-asynctask-example-and-explanation/25647882#25647882
    // "?" to end of each params to avoid error on (*values) in onProgressUpdate
    // "public" to avoid "redundant overriding method" error message
    //
    // TODO - AsyncTask<String        , Int             , String       >
    // TODO - AsyncTask<doInBackground, onProgressUpdate, onPostExecute>
    private inner class MyTask : AsyncTask<String, Int, String>() {

        // Runs in UI before background thread is called
        public override fun onPreExecute() {
            super.onPreExecute()
            // Do something like display a progress bar
            showProgress(true)
        }

        // This is run in a background thread
        override fun doInBackground(vararg params: String?): String {
            // get the string from params, which is an array
            val myString = params[0]

            return "no"
        }

        // This is called from background thread but runs in UI
        // TODO - get INT from publishProgress
        public override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        // TODO - get List<String> from doInBackground's return
        public override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            showProgress(false)
            //kotlin 1.3 provide isNullOrEmpty now!
            if (result.isNullOrEmpty()) {
                // nem ad vissza értéket
                // így még ezt meg kell néznem !!!
            }
            else {

            }
        }
    }


    /**
     * COMPANION
     */
    companion object {
        lateinit var CUSTOMER : String
    }
}