package com.example.smultronstallet
import Maps.MapsPlace
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_review.*
private const val REQUEST_CODE = 42

@Suppress("DEPRECATION")
class CameraActivity : AppCompatActivity(){
    lateinit var smultronPlats: EditText
    lateinit var recenssion: EditText
    lateinit var ButtonSave: Button
    lateinit var cameraButton1: ImageButton



    var newListOfSmultron = mutableListOf<MapsPlace>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_review)
        val db = Firebase.firestore
        smultronPlats = DittsmultronPlace1
        recenssion = ReviewS
        ButtonSave = buttonSmultronSave
        cameraButton1 = findViewById(R.id.cameraButtonSmultron)




        ButtonSave.setOnClickListener {
            var newSmultronPlace =
                MapsPlace(name = smultronPlats.text.toString(), review = recenssion.text.toString())
            db.collection("newSmultronPlace").add(newSmultronPlace)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    finish()
                }

            }

            //  cameraButtonSmultron.setOnClickListener{
            //val intentCamerar = Intent(this, CameraActivity::class.java)
            //startActivity(intentCamerar)
            //}

            val takePicturesIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePicturesIntent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(takePicturesIntent, REQUEST_CODE)

            } else {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()

            }

        }

        /*  private fun getLocation() {
          locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
          if ((ContextCompat.checkSelfPermission(
                  this,
                  Manifest.permission.ACCESS_FINE_LOCATION
              ) != PackageManager.PERMISSION_GRANTED)
          ) {
              ActivityCompat.requestPermissions(
                  this,
                  arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                  locationPermissionCode
              )
          }
          locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
      }*/


        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                val takenImage = data?.extras?.get("data") as Bitmap

                imageView.setImageBitmap(takenImage)
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }

        }
    }
