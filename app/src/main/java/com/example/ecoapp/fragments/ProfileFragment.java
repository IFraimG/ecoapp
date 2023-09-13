package com.example.ecoapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ecoapp.MainActivity;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentProfileBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.models.User;
import com.example.ecoapp.presentation.viewmodels.ProfileViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private int SELECT_PHOTO_PROFILE = 1;
    private Uri uri;
    private ActionBar actionBar;
    private StorageHandler storageHandler;
    private ProfileViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storageHandler = new StorageHandler(requireContext());
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        viewModel.getUserData(storageHandler.getToken(), storageHandler.getUserID()).observe(requireActivity(), user -> {
            if (user != null && !user.getImage().isEmpty()) {
                // update text values ...
                String url = "http://192.168.0.100:8080/image/" + user.getImage();
                Picasso.get().load(url).into(binding.profileImageButton);
//                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
//                photoProfileImageView.setImageBitmap(bitmap);
            }
        });


        binding.profileImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(intent, "Choose Photo");

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

            startActivityForResult(chooserIntent, SELECT_PHOTO_PROFILE);
        });

        storageHandler = new StorageHandler(requireContext());

        binding.logout.setOnClickListener(v -> {
            storageHandler.logout();
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).changeMenu(false);
            }

            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_authSignupFragment);
            Navigation.findNavController(v).popBackStack(R.id.profileFragment, true);

        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO_PROFILE && resultCode == RESULT_OK && data != null) {
            if (data.getData() != null) {
                uri = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = requireActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imagePath = cursor.getString(columnIndex);
                cursor.close();

                try {
                    Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                    File file = new File(imagePath);

                    viewModel.savePhoto(storageHandler.getToken(), file, storageHandler.getUserID()).observe(requireActivity(), statusCode -> {
                        if (statusCode == 0) {
//                            photoProfileImageView.setImageBitmap();

                        } else if (statusCode < 400) {
                            binding.profileImageButton.setImageBitmap(originalBitmap);
                        } else Snackbar.make(requireView(), "Произошла ошибка", Snackbar.LENGTH_SHORT).show();
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    binding.profileImageButton.setImageBitmap(bitmap);
                }
            }
        }
    }
}