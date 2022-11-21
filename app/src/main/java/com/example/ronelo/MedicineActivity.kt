package com.example.ronelo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import coil.load
import com.example.ronelo.body.UploadRequestBody
import com.example.ronelo.databinding.ActivityMedicineBinding
import com.example.ronelo.retrofit.Api
import com.example.ronelo.retrofit.CreatePostResponse
import com.example.ronelo.retrofit.RetrofitClient
import com.example.ronelo.util.getFileName
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MedicineActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {


    private val REQ_CODE_CAMERA = 120
    private val REQ_CODE_GALLERY = 121
    private lateinit var binding: ActivityMedicineBinding
    private lateinit var currentPhotoPath: String
    private var selectedImageFromGallery: Uri? = null
    private val mConfig = Api


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Medicine"
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


        //UPLOAD
        binding.btnUpload.setOnClickListener {
            createPost()
        }


        //GALLERY
        binding.btnGallery.setOnClickListener {
            checkGalleryPermission()
        }

        //CAMERA
        binding.btnCamera.setOnClickListener {
            checkCameraPermission()
        }

        binding.ivPic.setOnClickListener {
            //Title
            val chooseItemDialog = AlertDialog.Builder(this)
            chooseItemDialog.setTitle("Select Platform")
            //Choosing
            val chooseItem = arrayOf(
                "Choose Image from Gallery",
                "Using Camera"
            )
            chooseItemDialog.setItems(chooseItem) { dialog, which ->
                when (which) {
                    0 -> galleryApk()
                    1 -> cameraApk()
                }
            }
            chooseItemDialog.show()
        }
    }

    //GALLERY
    private fun checkGalleryPermission() {
        Dexter.withContext(this).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                galleryApk()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(
                    this@MedicineActivity,
                    "you haven't access to storage for selected image",
                    Toast.LENGTH_SHORT
                ).show()
                rotationalDialogPermission()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?, p1: PermissionToken?
            ) {
                rotationalDialogPermission()
            }
        }).onSameThread().check()
    }

    private fun galleryApk() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        startActivityForResult(intent, REQ_CODE_GALLERY)
    }


    //CAMERA
    private fun checkCameraPermission() {
        Dexter.withContext(this)
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            ).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()) {
                                cameraApk()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        rotationalDialogPermission()
                    }
                }
            ).onSameThread().check()
    }


    private fun rotationalDialogPermission() {
        AlertDialog.Builder(this)
            .setMessage("Please turn on Permission" + "please go to Setting :)")
            .setPositiveButton("go to Settings") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun cameraApk() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    getImageFile()
                } catch (ex: IOException) {
                    //TOAST
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.ronelo.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQ_CODE_CAMERA)
                }
            }
        }
    }

    private fun getImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    //RESULT
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                REQ_CODE_CAMERA -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    binding.ivPic
                        .load(bitmap) {
                            crossfade(true)
                            crossfade(1000)
                        }
                }
                REQ_CODE_GALLERY -> {
                    selectedImageFromGallery = data?.data!!
                    binding.ivPic
                        .load(selectedImageFromGallery) {
                            crossfade(true)
                            crossfade(1000)
                        }
                }
            }
        }
    }


    private fun createPost() {
        if (selectedImageFromGallery == null) {
            Toast.makeText(this, "select image first from gallery", Toast.LENGTH_SHORT).show()
        } else {
            val descriptor =
                contentResolver.openFileDescriptor(selectedImageFromGallery!!, "r", null) ?: return
            val input = FileInputStream(descriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(selectedImageFromGallery!!))
            val output = FileOutputStream(file)
            input.copyTo(output)

            binding.progBar.progress = 0
            val body = UploadRequestBody(file, "image", this)
//
//            Api().getDataPost(
//                MultipartBody.Part.createFormData("image", file.name, body),
//                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
//                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
//                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
//                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
//                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
//                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
//                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
//                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
//            ).enqueue(object : Callback<CreatePostResponse> {
//                override fun onResponse(
//                    call: Call<CreatePostResponse>,
//                    response: Response<CreatePostResponse>
//                ) {
//                    response.body()?.let {
//                        binding.btnCamera.snackbar(it.message)
//                        Log.d("Gallery", response.body().toString())
//                        Log.d("Gallery", response.code().toString())
//                        Log.d("Gallery", response.message())
//                        binding.progBar.progress = 100
//
//                        val diseasePrediction = response.body()!!.result.prediction
//                        if (response.code() == 200) {
//                            val intent =
//                                Intent(
//                                    this@MedicineActivity,
//                                    ResultMedicineActivity::class.java
//                                ).apply {
//                                    putExtra(ResultMedicineActivity.disease, diseasePrediction)
//                                    putExtra(
//                                        ResultMedicineActivity.image,
//                                        selectedImageUri.toString()
//                                    )
//                                    putExtra(ResultMedicineActivity.file, file.toString())
//                                }
//                            startActivity(intent)
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<CreatePostResponse>, t: Throwable) {
//                    binding.btnCamera.setOnClickListener {
//                        Toast.makeText(this, "")
//                    }
//                    binding.progBar.progress = 0
//                }
//            })
        }

//    private fun getUploadImage(){
//        var client = OkHttpClient().newBuilder()
//            .build()
//        var mediaType: MediaType = MediaType.parse("text/plain")!!
//        var body: RequestBody = Builder().setType(MultipartBody.FORM)
//            .addFormDataPart(
//                "image", "metformin.jpeg",
//                create(
//                    MediaType.parse("application/octet-stream"),
//                    File("/home/najie/Documents/ronelo/ronelo-backend/metformin.jpeg")
//                )
//            )
//            .build()
//        var request: Request = Builder()
//            .url("http://cfb6b2f9fd14.ngrok.io/upload_image")
//            .method("POST", body)
//            .build()
//        var response: Response = client.newCall(request).execute()
//    }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onProgressUpdate(percentage: Int) {
        TODO("Not yet implemented")
    }


}