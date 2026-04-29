package com.example.flowfund

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flowfund.databinding.ActivityPhotoViewBinding
import java.io.File

class PhotoViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoPath = intent.getStringExtra("photoPath")

        if (photoPath != null){
            val  file = File(photoPath)
            if (file.exists()){
                binding.ivPhoto.setImageURI(Uri.fromFile(file))
            }
        }
    }
}