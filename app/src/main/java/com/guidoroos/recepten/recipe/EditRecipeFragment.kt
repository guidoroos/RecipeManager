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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import com.guidoroos.recepten.R
import com.guidoroos.recepten.databinding.EditRecipeFragmentBinding
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.util.setDurationText
import com.guidoroos.recepten.util.setFilled
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


private const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100




@AndroidEntryPoint
class EditRecipeFragment : Fragment(), View.OnClickListener {
    enum class Mode { ADD, EDIT }

    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var binding: EditRecipeFragmentBinding
    private val args: RecipeFragmentArgs by navArgs()
    private lateinit var mode: Mode

    private val takePhotoRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                binding.recipePhoto.setImageURI(viewModel.currentPhotoPath?.toUri())
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
        viewModel.levelSelected = args.recipe?.level ?: 0
        viewModel.minutesDuration = args.recipe?.minutesDuration ?: 0
        viewModel.currentPhotoPath = args.recipe?.imageResourceUri

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.toolbar.inflateMenu(R.menu.menu_modify_finish)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.cancel_icon -> {
                    if (mode == Mode.EDIT) {
                            findNavController().popBackStack(R.id.recipeFragment,false)
                    } else {
                        findNavController().popBackStack(R.id.recipeOverviewFragment,false)
                    }
                    true
                }
                R.id.finish_icon -> {
                    val recipe = Recipe(
                        title = binding.titleText.text.toString(),
                        imageResourceUri = viewModel.currentPhotoPath,
                        description = binding.descriptionEdittext.text.toString(),
                        cuisine = binding.countryEdittext.text.toString(),
                        recipeType = binding.recipeTypeEdittext.text.toString(),
                        level = viewModel.levelSelected,
                        minutesDuration = viewModel.minutesDuration,
                        isFavorite = viewModel.isFavorite
                    )

                    if (mode == Mode.ADD) {
                        viewModel.storeRecipe(recipe)
                        findNavController().navigate(EditRecipeFragmentDirections.actionEditRecipeFragmentToRecipeFragment(recipe))

                    } else {
                        recipe.id = args.recipe?.id ?: 0
                        viewModel.updateRecipe(recipe)
                        navController.previousBackStackEntry?.savedStateHandle?.set("recipe", recipe)
                        findNavController().popBackStack(R.id.recipeFragment,false)
                    }

                    navController.previousBackStackEntry?.savedStateHandle?.set("recipe", recipe)
                    findNavController().popBackStack(R.id.recipeFragment,false)

                    true
                }
                else -> {
                    true
                }
            }
        }

        binding.starImage1.setOnClickListener(this)
        binding.starImage2.setOnClickListener(this)
        binding.starImage3.setOnClickListener(this)
        binding.likeImage.setOnClickListener(this)
        binding.durationImage.setOnClickListener(this)
        binding.durationTextView.setOnClickListener(this)
        binding.recipePhoto.setOnClickListener(this)


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
            viewModel.currentPhotoPath = absolutePath
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
       val file = File(viewModel.currentPhotoPath ?: return)
       MediaScannerConnection.scanFile(context, arrayOf(file.toString()),
            null, null)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.starImage1 -> {
                viewModel.levelSelected = 1
                setFilled(binding.starImage1, true)
                setFilled(binding.starImage2, false)
                setFilled(binding.starImage3, false)
            }
            binding.starImage2 -> {
                viewModel.levelSelected = 2
                setFilled(binding.starImage1, true)
                setFilled(binding.starImage2, true)
                setFilled(binding.starImage3, false)
            }
            binding.starImage3 -> {
                viewModel.levelSelected = 3
                setFilled(binding.starImage1, true)
                setFilled(binding.starImage2, true)
                setFilled(binding.starImage3, true)
            }
            binding.recipePhoto -> {
                handlePhotoClick()
            }
            binding.likeImage -> {
                viewModel.isFavorite = !viewModel.isFavorite
                setFilled (binding.likeImage, !viewModel.isFavorite)
            }
            binding.durationImage, binding.durationTextView -> {
                showTimePicker()
            }

        }
    }

    private fun showTimePicker() {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(0)
                .setMinute(viewModel.minutesDuration)
                .setTitleText(R.string.setDuration)
                .setInputMode(INPUT_MODE_KEYBOARD)
                .build()

        picker.addOnPositiveButtonClickListener {
            val minutes = picker.hour * 60 + picker.minute
            setDurationText(binding.durationTextView, minutes)
            viewModel.minutesDuration = minutes
        }

        picker.show(parentFragmentManager, "tag");
    }

    private fun handlePhotoClick() {
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
            if (requireContext().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(requireContext(), R.string.no_camera, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


}