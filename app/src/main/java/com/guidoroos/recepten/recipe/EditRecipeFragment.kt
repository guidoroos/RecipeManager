package com.guidoroos.recepten.recipe

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.guidoroos.recepten.R
import com.guidoroos.recepten.databinding.EditRecipeFragmentBinding
import com.guidoroos.recepten.db.Recipe
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


private const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100




@AndroidEntryPoint
class EditRecipeFragment : Fragment() {
    enum class Mode { ADD, EDIT }

    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var binding: EditRecipeFragmentBinding
    private var currentPhotoPath: String? = null
    private val args: RecipeFragmentArgs by navArgs()
    private lateinit var mode: Mode

    private val imageRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val intent = it.data ?: return@registerForActivityResult
                val imageUri = intent.data
                currentPhotoPath = imageUri.toString()
                binding.recipePhoto.setImageURI(imageUri)
            }
        }

    private val takePhotoRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                binding.recipePhoto.setImageURI(currentPhotoPath?.toUri())
                addPictureToGallery()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditRecipeFragmentBinding.inflate(layoutInflater, container, false)

        mode = if (args.recipe == null) Mode.ADD else Mode.EDIT
        binding.recipe = args.recipe

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.toolbar.inflateMenu(R.menu.menu_modify_finish)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.cancel_icon -> {
                    if (mode == Mode.EDIT) {
                        findNavController().navigate(
                            EditRecipeFragmentDirections.actionEditRecipeFragmentToRecipeFragment(
                                args.recipe
                            )
                        )
                    } else {
                        findNavController().navigate(
                            EditRecipeFragmentDirections.actionEditRecipeFragmentToRecipeOverviewFragment()
                        )
                    }
                    true
                }
                R.id.finish_icon -> {
                    val recipe = Recipe(
                        title = "dummy",
                        imageResourceUri = currentPhotoPath,
                        description = binding.descriptionTextView.text.toString(),
                        cuisine = binding.countryEdittext.text.toString(),
                        recipeType = binding.recipeTypeTextView.text.toString(),
                        level = 1,
                        minutesDuration = 10
                    )

                    viewModel.storeRecipe(recipe)
                    addPictureToGallery()

                    findNavController().navigate(
                        EditRecipeFragmentDirections.actionEditRecipeFragmentToRecipeFragment(
                            recipe
                        )
                    )
                    true
                }
                else -> {
                    true
                }
            }
        }
        binding.recipePhoto.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.add_image)
                .setMessage(R.string.select_option)
                .setPositiveButton(
                    R.string.image_from_device
                ) { _, _ ->
                    if (ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            listOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                    } else {
                        val intent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.INTERNAL_CONTENT_URI
                        )
                        imageRequest.launch(intent)
                    }
                }
                .setNegativeButton(
                    R.string.image_from_photo
                ) { _, _ ->
                    if (requireContext().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                        dispatchTakePictureIntent()
                    } else {
                        Toast.makeText(requireContext(), R.string.no_camera, Toast.LENGTH_LONG)
                            .show()
                    }
                }
                .setNeutralButton(R.string.cancel)
                { _, _ ->
                    //just dismiss
                }
                .show()
        }

        return binding.root
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.guidoroos.recepten.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePhotoRequest.launch(takePictureIntent)
                }
        }
    }


    private fun addPictureToGallery() {
       val file = File(currentPhotoPath ?: return)
       MediaScannerConnection.scanFile(context, arrayOf(file.toString()),
            null, null)
    }





    private fun navigateToEditRecipeFragment() {

    }


}