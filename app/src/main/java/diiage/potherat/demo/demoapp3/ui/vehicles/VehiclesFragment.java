package diiage.potherat.demo.demoapp3.ui.vehicles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import diiage.potherat.demo.demoapp3.R;
import diiage.potherat.demo.demoapp3.databinding.FragmentVehiclesBinding;
import diiage.potherat.demo.demoapp3.model.sw.Vehicle;

@AndroidEntryPoint
public class VehiclesFragment extends Fragment {

    @Inject
    public VehiclesViewModel vehiclesViewModel;
    private FragmentVehiclesBinding binding;

    private ProgressBar progress;
    private EditText input;
    private TextView make;
    private TextView model;
    private TextView error;
    private Button button;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentVehiclesBinding.inflate(inflater,container,false);

        ready();

        View root = binding.getRoot();

        progress = root.findViewById(R.id.progress);
        input = root.findViewById(R.id.input);
        make = root.findViewById(R.id.make);
        model = root.findViewById(R.id.model);
        error = root.findViewById(R.id.error);
        button = root.findViewById(R.id.button);

        vehiclesViewModel.getLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                progress.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!input.getText().toString().equals("") && Integer.parseInt(input.getText().toString()) > 0 ) {
                    vehiclesViewModel.loadVehicle(Integer.parseInt(input.getText().toString()));
                    vehiclesViewModel.getVehicle().observe(getViewLifecycleOwner(), new Observer<Vehicle>() {
                        @Override
                        public void onChanged(Vehicle vehicle) {
                            if ( vehicle == null ) {
                                make.setVisibility(View.INVISIBLE);
                                model.setVisibility(View.INVISIBLE);
                                error.setVisibility(View.VISIBLE);
                                error.setText("Vehicle does not exists !");
                            } else {
                                error.setVisibility(View.INVISIBLE);
                                make.setVisibility(View.VISIBLE);
                                model.setVisibility(View.VISIBLE);

                                make.setText(vehicle.name);
                                model.setText(vehicle.model);
                            }
                            input.setText("");
                            progress.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void ready(){
        if (binding != null && vehiclesViewModel != null){
            binding.setLifecycleOwner(this);
            binding.setVehiclesViewModel(vehiclesViewModel);
        }
    }
} 