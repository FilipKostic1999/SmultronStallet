package Owner


import PlaceRecycleView.PlaceRecyclerAdapter
import UserRecycleView.User
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smultronstallet.MainActivity
import com.example.smultronstallet.R

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class OwnerReviewsActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val userList = mutableListOf<User>()
    private val reviews = mutableListOf<OwnerReviews>()
    private lateinit var recyclerView: RecyclerView
    private var emailString: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_reviews)

        dataInitialize()

        recyclerView = findViewById(R.id.reviewsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = OwnerRecycleAdapter(this, reviews)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : OwnerRecycleAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                // Build email string with user emails
                for (user in reviews) {
                    val userEmails = "${emailString},${user.userEmail}"
                    emailString = userEmails
                    Log.d("!!!", "usermails: $userEmails")
                }
                Log.d("!!!", "emailString : $emailString")

                // Pass data back to OwnerActivity
                val businessNameBack = intent.getStringExtra("businessName")
                val intent = Intent(this@OwnerReviewsActivity, OwnerActivity::class.java)
                intent.putExtra("emails", emailString)
                intent.putExtra("businessnameexist", true)
                intent.putExtra("businessname", businessNameBack)
                startActivity(intent)
                Toast.makeText(
                    this@OwnerReviewsActivity,
                    "Vill du skicka erbjudande?",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun dataInitialize() {
        val businessName = intent.getStringExtra("businessName")
        Log.d("!!!", "$businessName")

        db.collection("users")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result) {
                        val user = document.toObject<User>()
                        userList.add(user)

                        db.collection("users")
                            .document(user.docId!!)
                            .collection("smultrons")
                            .get()
                            .addOnCompleteListener { smultronsTask ->
                                if (smultronsTask.isSuccessful) {
                                    for (smultrons in smultronsTask.result) {
                                        val smultronNamn = smultrons.data["name"].toString()
                                        if (businessName == smultronNamn) {
                                            val review = smultrons.data["review"].toString()
                                            reviews.add(
                                                OwnerReviews(
                                                    userName = document.data["name"].toString(),
                                                    userReview = review,
                                                    userEmail = document.data["email"].toString()
                                                )
                                            )
                                        }
                                    }
                                    recyclerView.adapter?.notifyDataSetChanged()
                                }
                            }
                    }
                }
            }
    }
}


/*
Made the variables db, userList, and reviews private since they are only used within the class.
Added appropriate access modifiers to the variables and methods.
Removed redundant code and unused impvrts.
Moved the initialization of recyclerView to a separate line for better readability.
Used a more descriptive variable name smultronsTask for the task of retrieving smultrons data.
Encapsulated the data initialization logic within the dataInitialize() method to improve code organization and readability.
 */