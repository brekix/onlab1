package hu.mate.onlab.Home

import kotlinx.android.synthetic.main.login_activity.*

import android.support.v7.app.AppCompatActivity
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.content.Intent
import android.support.design.widget.BottomSheetDialog
import hu.mate.onlab.R
import kotlinx.android.synthetic.main.login_tips_dialog.view.*


/**
login_activity screen
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        //if (preferences.getString("pref_theme", "light") == "dark")
        //setTheme(R.style.ThemeBaseBlue)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        email.setText("T1000@skynet.com")
        password.setText("JohnConor")
        signin_button.setOnClickListener { attemptLogin() }


        val bottomSheetLayout = layoutInflater.inflate(R.layout.login_tips_dialog, null)
        val mBottomSheetDialog = BottomSheetDialog(this)
        mBottomSheetDialog.setContentView(bottomSheetLayout)
        bottomSheetLayout.button_close.setOnClickListener { mBottomSheetDialog.dismiss() }
        mBottomSheetDialog.show()
    }


    /**
    login_activity button
     */
    private fun attemptLogin() {

        // Reset errors.
        email.error = null
        password.error = null
        // Store values at the time of the login_activity attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()
        // cancel
        var cancel = false
        // focus
        var focusView: View? = null

        // validity
        if ( !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.login_error_invalid_password)
            focusView = password
            cancel = true
        }
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.login_error_required_field)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.login_error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login_activity and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // pre execute
            signin_button.setText(R.string.button_cancel)
            showProgress(true)

            // ASYNC
            // AUTHENTICATION
            // internal class
            mAuthTask = UserLoginTask(emailStr, passwordStr)
            mAuthTask!!.execute(null as Void?)

            // handle ?
        }
    }

    /**
    minimal validity
     */
    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 1
    }

    /**
    progressbar
     */
    private fun showProgress(show: Boolean) {
        // Animation
        login_progress.isIndeterminate = show
    }

    /**
    Auth task
     */
    private var mAuthTask: UserLoginTask? = null

    /**
    Auth class
     */
    inner class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String) :
        AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            try {
                // Simulate network access.
                Thread.sleep(500)
            }
            catch (e: InterruptedException) {
                return false
            }

            val isAuthencitated = mEmail+":"+mPassword == "T1000@skynet.com:JohnConor"
            return isAuthencitated
        }

        override fun onPostExecute(success: Boolean?) {
            showProgress(false)

            mAuthTask = null
            if (success!!) {
                finish()

                // maybe a wrong place to invoke
                // AUTH OK
                val next = Intent(this@LoginActivity, QueryActivity::class.java)
                next.putExtra("hi", "HI")
                next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(next)
                //Toast.makeText(applicationContext,"Welcome ..", Toast.LENGTH_SHORT).show()
                // BYE
            } else {
                signin_button.setText(R.string.login_button_label)
                password.error = getString(R.string.login_error_failed_authentication)
                password.requestFocus()
            }
        }

        override fun onCancelled() {
            mAuthTask = null
            showProgress(false)
        }
    }


}
