package diiage.potherat.demo.demoapp3.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import diiage.potherat.demo.demoapp3.R;
import diiage.potherat.demo.demoapp3.databinding.FragmentHomeBinding;


@AndroidEntryPoint
public class HomeFragment extends Fragment {

    @Inject
    public HomeViewModel homeViewModel;
    public FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        ready();
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel.LaunchCountQuoteProcess();
        homeViewModel.LaunchCountDistinctAuthorsProcess();
        homeViewModel.LaunchLastQuoteProcess();
    }

    private void ready(){
        if (binding != null && homeViewModel != null){
            binding.setLifecycleOwner(this);
            binding.setViewModel(homeViewModel);
        }
    }
}