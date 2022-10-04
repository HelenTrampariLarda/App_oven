package com.example.oven_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTimerSet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTimerSet extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Integer [] wres = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
    private Integer [] lepta = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,
                               32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59};
    private Integer [] deyterolepta ={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,
                                    32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59};

    private View rootview;
    private View rootview_main;

    private int selected_wres;
    private int selected_lepta;
    private int selected_deyterolepta;


    public FragmentTimerSet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTimerSet.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTimerSet newInstance(String param1, String param2) {
        FragmentTimerSet fragment = new FragmentTimerSet();
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
        rootview = inflater.inflate(R.layout.fragment_timer_set, container, false);



        //adapter = new ArrayAdapter<Integer>(getActivity(),R.layout.lista,wres);
        //(this,R.layout.lista,wres);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), R.layout.lista,wres);

        ListView list_view = (ListView) rootview.findViewById(R.id.list_view_wres);

        list_view.setFadingEdgeLength(200);
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selected_wres = wres[position];
            }
        });

        adapter = new ArrayAdapter<Integer>(getActivity(), R.layout.lista,lepta);

        list_view = (ListView) rootview.findViewById(R.id.list_view_lepta);

        list_view.setFadingEdgeLength(200);
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selected_lepta = lepta[position];
            }
        });

        adapter = new ArrayAdapter<Integer>(getActivity(), R.layout.lista,deyterolepta);

        list_view = (ListView) rootview.findViewById(R.id.list_view_deyterolepta);

        list_view.setFadingEdgeLength(200);
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selected_deyterolepta = deyterolepta[position];
            }

        });

        Button start_ = rootview.findViewById(R.id.start_);
        Button close_ = rootview.findViewById(R.id.close_);


        start_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView inf = (TextView) getActivity().findViewById(R.id.hidden_info);


                String temp = Integer.toString(selected_wres)+","+Integer.toString(selected_lepta)+","+Integer.toString(selected_deyterolepta);
                Intent intent = new Intent(getActivity(),MainActivity.class);
                String addition;
                TextView onoma = rootview.findViewById(R.id.textView2);
                if(onoma.getText().toString().equals("Ρυθμίστε το χρονόμετρό σας για τις εστίες")){
                    addition = "#e";
                }
                else{
                    addition = "#f";
                }
                inf.setText(temp+addition);
                //FragmentContainerView ch = (FragmentContainerView) rootview.findViewById(R.id.fragmentXronometro);
                getActivity().findViewById(R.id.fragmentXronometro).setVisibility(View.INVISIBLE);
                getActivity().findViewById(R.id.hidden_button).performClick();
            }

        });

        close_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().findViewById(R.id.fragmentXronometro).setVisibility(View.INVISIBLE);
            }
        });





        return rootview;
    }
}