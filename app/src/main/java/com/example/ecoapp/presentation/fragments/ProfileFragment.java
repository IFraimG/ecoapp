package com.example.ecoapp.presentation.fragments;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentProfileBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.viewmodels.ProfileViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

        storageHandler = new StorageHandler(requireContext());
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        viewModel.getUserData(storageHandler.getToken(), storageHandler.getUserID()).observe(requireActivity(), user -> {
            if (user != null) {
                binding.personName.setText(user.getName());
                binding.personPoints.setText("Баллы: " + user.getScores());
            }
            if (user != null && user.getImage() != null) {
                // update text values ...
                binding.profileImageButton.setVisibility(View.VISIBLE);
                binding.profileLoadImage.setVisibility(View.GONE);
                String url = "https://test123-production-e08e.up.railway.app/image/" + user.getImage();
                Picasso.get().load(url).into(binding.profileImageButton);
            } else if (user == null) {
                NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_main_fragment);
                navHostFragment.getNavController().navigate(R.id.noNetworkFragment);
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


        binding.logout.setOnClickListener(v -> {
            storageHandler.logout();
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).changeMenu(false);
            }

            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_authSignupFragment);
            Navigation.findNavController(v).popBackStack(R.id.profileFragment, true);
        });

        binding.whiteTheme.setOnClickListener(View -> storageHandler.setTheme(0));
        binding.blackTheme.setOnClickListener(View -> storageHandler.setTheme(1));
        binding.greenTheme.setOnClickListener(View -> storageHandler.setTheme(2));

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (((requestCode == SELECT_PHOTO_PROFILE && resultCode == RESULT_OK)) && data != null) {
            if (data.getData() != null) {
                uri = data.getData();

                try {
                    final InputStream imageStream = requireActivity().getContentResolver().openInputStream(uri);
                    final Bitmap originalBitmap = BitmapFactory.decodeStream(imageStream);

                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = requireActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imagePath = cursor.getString(columnIndex);
                    cursor.close();

                    File file = new File(imagePath);
                    saveImage(file, originalBitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                File f = new File(requireContext().getCacheDir(), "test");
                try {
                    f.createNewFile();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);

                    byte[] bitmapdata = bos.toByteArray();

                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                    saveImage(f, bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void saveImage(File file, Bitmap originalBitmap) {
        viewModel.savePhoto(storageHandler.getToken(), file, storageHandler.getUserID()).observe(requireActivity(), statusCode -> {
            if (statusCode == 0) {
                binding.profileImageButton.setVisibility(View.GONE);
                binding.profileLoadImage.setVisibility(View.VISIBLE);
            } else if (statusCode < 400) {
                binding.profileImageButton.setImageBitmap(originalBitmap);
                binding.profileImageButton.setVisibility(View.VISIBLE);
                binding.profileLoadImage.setVisibility(View.GONE);
            } else {
                Toast.makeText(requireContext(),"Произошла ошибка" + Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
                binding.profileImageButton.setVisibility(View.VISIBLE);
                binding.profileLoadImage.setVisibility(View.GONE);
            }
        });
    }
}