package com.example.ecoapp;

import static android.app.Activity.RESULT_OK;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileFragment extends Fragment {
    int SELECT_PHOTO_PROFILE = 1;
    Uri uri;
    ImageView photoProfileImageView;
    ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        photoProfileImageView = view.findViewById(R.id.profile_image_button);

        photoProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(intent, "Choose Photo");

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

                startActivityForResult(chooserIntent, SELECT_PHOTO_PROFILE);
            }
        });

        return view;
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