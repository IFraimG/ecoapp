package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.ecoapp.data.models.Rating;
import com.example.ecoapp.data.models.User;
import com.example.ecoapp.databinding.FragmentGuideBinding;
import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.presentation.viewmodels.GuideViewModel;
import com.example.ecoapp.presentation.viewmodels.ProfileViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class GuideFragment extends Fragment {
    private FragmentGuideBinding binding;
    private GuideViewModel viewModel;
    private ProfileViewModel profileViewModel;
    private String guideID;

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) requireActivity()).changeMenu(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) requireActivity()).changeMenu(true);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGuideBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(GuideViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding.guideBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        Bundle args = getArguments();
        guideID = args != null ? args.getString("guideID") : null;

        if (guideID != null) {
            viewModel.getGuideByID(guideID).observe(requireActivity(), guide -> {
                if (guide != null) {
                    profileViewModel.getUserData("", "").observe(requireActivity(), user -> {
                        if (user != null) {
                            binding.guideAuthorName.setText(user.getName());
                            showBookmark(user);
                        }
                    });
                    viewModel.getRating(guideID).observe(requireActivity(), rating -> {
                        if (rating != null) binding.guideRatingBar.setRating(rating.getMark());
                    });

                    binding.guideSourceName.setText(guide.getSource());
                    binding.articleTv.setText(guide.getSource());
                    binding.guideTitleTitle.setText(guide.getTitle());

                    String url = "https://test123-production-e08e.up.railway.app/image/" + guide.getPhoto();
                    Picasso.get().load(url).into(binding.adviceImage);
                }
            });

            binding.guideRatingBar.setOnRatingBarChangeListener((ratingBar, rating, b) -> {
                viewModel.setRating(guideID, rating);
            });
        }

        return binding.getRoot();
    }

    private void updateGuideToUser() {
        profileViewModel.updateGuideToUser(guideID).observe(requireActivity(), user -> {
            if (user != null) showBookmark(user);
        });
    }

    private void showBookmark(User user) {
        if (user.getGuidesList().contains(guideID)) {
            binding.unactivatedBookmark.setVisibility(View.GONE);
            binding.activatedBookmark.setVisibility(View.VISIBLE);
        } else {
            binding.unactivatedBookmark.setVisibility(View.VISIBLE);
            binding.activatedBookmark.setVisibility(View.GONE);
        }

        binding.unactivatedBookmark.setOnClickListener(View -> updateGuideToUser());
        binding.activatedBookmark.setOnClickListener(View -> updateGuideToUser());
    }
}