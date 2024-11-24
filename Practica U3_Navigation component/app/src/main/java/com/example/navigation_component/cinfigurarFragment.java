package com.example.navigation_component;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class cinfigurarFragment extends Fragment implements GestureOverlayView.OnGesturePerformedListener {
    private GestureLibrary gLibrary;
    private View viewFragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public cinfigurarFragment() {
        // Required empty public constructor
    }

    public static cinfigurarFragment newInstance(String param1, String param2) {
        cinfigurarFragment fragment = new cinfigurarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cinfigurar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewFragment = view;
        gestureSetup(viewFragment);
    }

    private void gestureSetup(View view) {
        gLibrary = GestureLibraries.fromRawResource(getActivity(), R.raw.gestures);
        if (gLibrary.load()) {

        }
        GestureOverlayView gOverlay = view.findViewById(R.id.gestures);
        gOverlay.addOnGesturePerformedListener(this);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gLibrary.recognize(gesture);
        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
            String action = predictions.get(0).name;
            Log.i("Generate", action);

            if (action.equals("configurar")) {
                Toast.makeText(getActivity(), "Configurar gesture", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(viewFragment).navigate(R.id.inicioFragment);
            }

            if (action.equals("salir")) {
                Toast.makeText(getActivity(), "Configure gesture", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "asunto");
                intent.putExtra(Intent.EXTRA_TEXT, "texto del correo");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"21151603@aguascalientes.tecnm.mx"});
                startActivity(intent);
            }

            if (action.equals("Face")) {
                Toast.makeText(getActivity(), "Configure gesture", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://"));
                startActivity(intent);
            }
        }
    }
}