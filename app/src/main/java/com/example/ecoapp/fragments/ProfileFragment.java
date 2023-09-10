package com.example.ecoapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ecoapp.MainActivity;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentProfileBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private int SELECT_PHOTO_PROFILE = 1;
    private Uri uri;
    private ImageView photoProfileImageView;
    private ActionBar actionBar;
    private StorageHandler storageHandler;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        photoProfileImageView = binding.profileImageButton;

        photoProfileImageView.setOnClickListener(v -> {
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
                try {
                    Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                    photoProfileImageView.setImageBitmap(originalBitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    photoProfileImageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}