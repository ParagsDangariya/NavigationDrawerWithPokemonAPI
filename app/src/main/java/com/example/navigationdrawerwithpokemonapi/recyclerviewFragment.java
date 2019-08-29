package com.example.navigationdrawerwithpokemonapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class recyclerviewFragment extends Fragment {

    Recycleadapter adapt;

    RecyclerView mRecyclerView;
    ArrayList<Pokemon> pokes;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View fview = inflater.inflate(R.layout.fragment_recyclerview,container,false);


        mRecyclerView = fview.findViewById(R.id.recyclerviewFragment);

        Getdataservice service = RetrofitInstance.getRetrofitInstance().create(Getdataservice.class);

        Call<Pokemonpojo> call = service.getPokemonsObj();

        call.enqueue(new Callback<Pokemonpojo>() {
            @Override
            public void onResponse(Call<Pokemonpojo> call, Response<Pokemonpojo> response) {
                ArrayList<Pokemon> pokarray = new ArrayList<>();

                Pokemonpojo pokojo = response.body();

                try{
                    pokarray = new ArrayList<>(pokojo.getPokemon());
                    generateData(pokarray);

                }catch(NullPointerException e){
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Pokemonpojo> call, Throwable t) {

                Toast.makeText(getActivity(),"Something went wrong!!!",Toast.LENGTH_SHORT).show();
            }
        });

        return fview;

    }
    public void generateData(List<Pokemon> poklist){


        pokes = (ArrayList<Pokemon>) poklist;

        adapt = new Recycleadapter(pokes,getActivity().getApplicationContext());

        @SuppressLint("WrongConstant") LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapt);

    }
}
