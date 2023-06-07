package Owner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.smultronstallet.R
import kotlinx.android.synthetic.main.activity_owner.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class OwnerActivity : AppCompatActivity() {

    private var businessNameExist: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)

        // Disable buttons initially
        sendOfferBtn.isEnabled = false
        showReviewsBtn.isEnabled = false

        val logOutBtn = findViewById<Button>(R.id.logOutBtn)
        logOutBtn.setOnClickListener {
            val intent = Intent(this, Login.SignInActivity::class.java)
            startActivity(intent)
        }

        val businessName = findViewById<EditText>(R.id.businessNameEditText)
        val businessNameButton = findViewById<Button>(R.id.businessNameBtn)
        businessNameButton.setOnClickListener {
            // Disable the button and clear its appearance
            businessNameButton.isEnabled = false
            businessNameButton.hint = ""
            businessNameButton.text = null
            businessNameButton.setBackgroundResource(android.R.color.transparent)

            businessName.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
                if (id == EditorInfo.IME_ACTION_DONE) {
                    val nameText = businessName.text.toString()
                    val intent = Intent(this, OwnerReviewsActivity::class.java)
                    intent.putExtra("businessName", nameText)
                    startActivity(intent)

                    // Enable buttons and set businessNameExist flag to true
                    sendOfferBtn.isEnabled = true
                    showReviewsBtn.isEnabled = true
                    businessNameExist = true
                    true
                } else {
                    false
                }
            })
        }

        // Get the value of businessnameexist from intent
        businessNameExist = intent.getBooleanExtra("businessnameexist", false)
        Log.d("!!!", "$businessNameExist")

        if (businessNameExist) {
            // If businessNameExist is true, disable the button and clear its appearance
            businessNameButton.isEnabled = false
            businessNameButton.hint = ""
            businessNameButton.text = null
            businessNameButton.setBackgroundResource(android.R.color.transparent)

            // Enable buttons
            sendOfferBtn.isEnabled = true
            showReviewsBtn.isEnabled = true
        }

        val emails = intent.getStringExtra("emails")

        val sendOfferBtn = findViewById<Button>(R.id.sendOfferBtn)
        sendOfferBtn.setOnClickListener {
            val intent = Intent(this, OwnerSendOfferActivity::class.java)
            intent.putExtra("emails", emails)
            startActivity(intent)
        }

        val showReviewsBtn = findViewById<Button>(R.id.showReviewsBtn)
        showReviewsBtn.setOnClickListener {
            val nameText = businessName.text.toString()
            val intent = Intent(this, OwnerReviewsActivity::class.java)
            intent.putExtra("businessName", nameText)
            startActivity(intent)
        }
    }
}
/*
Added appropriate access modifiers (private) to the businessNameExist property.
Removed the unused nameText variable declaration and unnecessary setEnabled(false) calls.
Removed unnecessary null checks in the OnEditorActionListener lambda.
Moved the initialization of sendOfferBtn and showReviewsBtn buttons to the appropriate location to avoid referencing them before initialization.
 */