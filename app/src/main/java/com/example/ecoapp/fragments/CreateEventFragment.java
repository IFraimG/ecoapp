package com.example.ecoapp.fragments;

import static android.app.Activity.RESULT_OK;

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

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecoapp.databinding.FragmentCreateEventBinding;
import com.example.ecoapp.R;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CreateEventFragment extends Fragment {
    private FragmentCreateEventBinding fragmentCreateEventBinding;
    private EventViewModel eventViewModel;

    private int SELECT_PHOTO_PROFILE = 1;

    private Uri uri;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCreateEventBinding = FragmentCreateEventBinding.inflate(getLayoutInflater());

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        fragmentCreateEventBinding.eventFindMap.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_createEventFragment_to_mapFragment);
        });

        fragmentCreateEventBinding.createEventButtonFragmentCreateEvent.setOnClickListener(v -> {;
            String title = fragmentCreateEventBinding.createEventTitle.getText().toString();
            String description = fragmentCreateEventBinding.eventDescriptionEditText.getText().toString();
            String date = fragmentCreateEventBinding.eventDateEditText.getText().toString();
            String time = fragmentCreateEventBinding.eventTimeEditText.getText().toString();

            if (title.isEmpty() || date.isEmpty() || description.isEmpty() || time.isEmpty()) {
                Toast.makeText(requireContext(), "Вы не заполнили все данные", Toast.LENGTH_LONG).show();
            } else if (isValidDateFormat(date)) {
                Toast.makeText(requireContext(), "Вы ввели дату в неправильном формате", Toast.LENGTH_LONG).show();
            } else if(isValidTimeFormat(time)) {
                Toast.makeText(requireContext(), "Вы ввели время в неправильном формате", Toast.LENGTH_LONG).show();
            } else {
               eventViewModel.sendData(title, description, date, time).observe(requireActivity(), statusCode -> {
                   if (statusCode < 400 && statusCode != 0) {
                       Toast.makeText(requireContext(), "Успешно!", Toast.LENGTH_SHORT).show();
                       Navigation.findNavController(v).navigate(R.id.action_createEventFragment_to_eventsFragment);
                   } else {
                       Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });

        fragmentCreateEventBinding.createEventPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(intent, "Choose Photo");

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

            startActivityForResult(chooserIntent, SELECT_PHOTO_PROFILE);
        });

        return fragmentCreateEventBinding.getRoot();
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
                    //saveImage(file, originalBitmap);
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

                    //saveImage(f, bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public boolean isValidDateFormat(String inputDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(inputDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isValidTimeFormat(String inputTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeFormat.setLenient(false);

        try {
            timeFormat.parse(inputTime);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}