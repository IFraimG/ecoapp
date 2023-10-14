package com.example.ecoapp.presentation.fragments;

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
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.data.models.EventCustom;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateEventFragment extends Fragment {
    private FragmentCreateEventBinding fragmentCreateEventBinding;
    private EventViewModel eventViewModel;
    private StorageHandler storageHandler;

    private int SELECT_PHOTO_PROFILE = 1;
    private String address;

    private Uri uri;
    private File fileImage;
    private double longt;
    private double lat;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCreateEventBinding = FragmentCreateEventBinding.inflate(getLayoutInflater());
        storageHandler = new StorageHandler(requireContext());

        Bundle args = getArguments();
        if (args != null) {
            address = args.getString("address");
            longt = args.getDouble("long");
            lat = args.getDouble("lat");
            if (address != null) {
                fragmentCreateEventBinding.eventAddressText.setVisibility(View.VISIBLE);
                fragmentCreateEventBinding.eventAddressText.setText(address);
            }

            EventCustom eventCustom = storageHandler.getIntermediateData();
            String title = eventCustom.getTitle();
            String description = eventCustom.getDescription();
            String date = eventCustom.getDate();
            String time = eventCustom.getTime();
            Integer peopleLen = eventCustom.getMaxUsers();
            Integer scores = eventCustom.getScores();
            String link = eventCustom.getPhoto();
            if (!title.isEmpty()) fragmentCreateEventBinding.eventNameEditText.setText(title);
            if (!description.isEmpty()) fragmentCreateEventBinding.eventDescriptionEditText.setText(description);
            if (!date.isEmpty()) fragmentCreateEventBinding.eventDateEditText.setText(date);
            if (!time.isEmpty()) fragmentCreateEventBinding.eventTimeEditText.setText(time);
            if (peopleLen > 0) fragmentCreateEventBinding.eventPeopleEditText.setText(String.valueOf(peopleLen));
            if (scores > 0) fragmentCreateEventBinding.eventPointsToAPersonEditText.setText(String.valueOf(scores));
            if (!link.isEmpty()) {
                fileImage = new File(eventCustom.getPhoto());
                String filePath = fileImage.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                fragmentCreateEventBinding.createEventPhoto.setImageBitmap(bitmap);
            }
        }

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        fragmentCreateEventBinding.createEventBackToEventFragmentButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        fragmentCreateEventBinding.eventFindMap.setOnClickListener(v -> {
            String title = fragmentCreateEventBinding.eventNameEditText.getText().toString();
            String description = fragmentCreateEventBinding.eventDescriptionEditText.getText().toString();
            String date = fragmentCreateEventBinding.eventDateEditText.getText().toString();
            String time = fragmentCreateEventBinding.eventTimeEditText.getText().toString();
            String peopleLen = fragmentCreateEventBinding.eventPeopleEditText.getText().toString().isEmpty() ? "0" : fragmentCreateEventBinding.eventPeopleEditText.getText().toString();
            String scores = fragmentCreateEventBinding.eventPointsToAPersonEditText.getText().toString().isEmpty() ? "0" : fragmentCreateEventBinding.eventPointsToAPersonEditText.getText().toString();

            storageHandler.saveIntermediateData(title, description, date, time, Integer.parseInt(peopleLen), fileImage == null ? "" : fileImage.getAbsolutePath(), Integer.parseInt(scores));

            Navigation.findNavController(v).navigate(R.id.mapFragment);
        });

        fragmentCreateEventBinding.createEventButtonFragmentCreateEvent.setOnClickListener(v -> {;
            String title = fragmentCreateEventBinding.eventNameEditText.getText().toString();
            String description = fragmentCreateEventBinding.eventDescriptionEditText.getText().toString();
            String date = fragmentCreateEventBinding.eventDateEditText.getText().toString();
            String time = fragmentCreateEventBinding.eventTimeEditText.getText().toString();
            String lenPeople = fragmentCreateEventBinding.eventPeopleEditText.getText().toString();
            String scores = fragmentCreateEventBinding.eventPointsToAPersonEditText.getText().toString();

            if (title.isEmpty() || date.isEmpty() || description.isEmpty() || time.isEmpty() || lenPeople.isEmpty() || address.isEmpty() || fileImage == null || scores.isEmpty()) {
                Toast.makeText(requireContext(), "Вы не заполнили все данные", Toast.LENGTH_LONG).show();
            } else if (!isValidDateFormat(date)) {
                Toast.makeText(requireContext(), "Вы ввели дату в неправильном формате", Toast.LENGTH_LONG).show();
            } else if (!isValidTimeFormat(time)) {
                Toast.makeText(requireContext(), "Вы ввели время в неправильном формате", Toast.LENGTH_LONG).show();
            } else if (Integer.parseInt(scores) < 100) {
                Toast.makeText(requireContext(), "Вы ввели слишком маленькое количество баллов", Toast.LENGTH_LONG).show();
            } else {
               eventViewModel.sendData(title, description, date, time, fileImage, lenPeople, address, lat, longt, Integer.parseInt(scores)).observe(requireActivity(), statusCode -> {
                   if (statusCode < 400 && statusCode != 0) {
                       Toast.makeText(requireContext(), "Успешно!", Toast.LENGTH_SHORT).show();
                       fragmentCreateEventBinding.eventNameEditText.setText("");
                       fragmentCreateEventBinding.eventDescriptionEditText.setText("");
                       fragmentCreateEventBinding.eventDateEditText.setText("");
                       fragmentCreateEventBinding.eventTimeEditText.setText("");
                       fragmentCreateEventBinding.eventPeopleEditText.setText("");
                       fragmentCreateEventBinding.eventPointsToAPersonEditText.setText("");

                       try {
                           Navigation.findNavController(v).navigate(R.id.eventsFragment);
                       } catch (IllegalStateException err) {
                           Toast.makeText(requireContext(), "Попробуйте еще раз", Toast.LENGTH_SHORT).show();
                       }
                   } else if (statusCode >= 400) {
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

                    fileImage = new File(imagePath);
                    fragmentCreateEventBinding.createEventPhoto.setImageBitmap(originalBitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                fileImage = new File(requireContext().getCacheDir(), "test");
                try {
                    fileImage.createNewFile();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);

                    byte[] bitmapdata = bos.toByteArray();

                    FileOutputStream fos = new FileOutputStream(fileImage);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                    fragmentCreateEventBinding.createEventPhoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public boolean isValidDateFormat(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        try {
            Date date = dateFormat.parse(dateString);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isValidTimeFormat(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        try {
            Date date = dateFormat.parse(dateString);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}