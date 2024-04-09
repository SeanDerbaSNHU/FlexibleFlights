package com.example.flexibleflights.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flexibleflights.Item;
import com.example.flexibleflights.MyAdapter;
import com.example.flexibleflights.R;
import com.example.flexibleflights.UserSingleton;
import com.example.flexibleflights.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    List<Item> items = new ArrayList<Item>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        /////
        //RecyclerView
        /////
        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewSaved);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter myAdapter = new MyAdapter(items);
        recyclerView.setAdapter(myAdapter);

        getList(items, myAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        items.clear();
        binding = null;
    }

    private void getList(List<Item> items, MyAdapter adapter){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        UserSingleton user = UserSingleton.getInstance();
        if(user.getEmail() != ""){  //Check for log in
            db.collection("users").document(user.getEmail()).collection("SavedFlights").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            //Log.d(TAG, document.getId() + " => " + document.getData());
                            items.add(makeItem(document));
                        }
                    } else {
                        //Task failed!
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private Item makeItem(QueryDocumentSnapshot doc){

        String total_amount = doc.getString("total_amount");
        String base_currency = doc.getString("base_currency");
        String name = doc.getString("name");
        String total_emissions_kg = doc.getString("total_emissions_kg");
        String aircraft_name = doc.getString("aircraft_name");
        String destination_name = doc.getString("destination_name");
        String origin_name = doc.getString("origin_name");
        String arrivalTime = doc.getString("arrive_time");
        String departTime = doc.getString("depart_time");
        String durationTime = doc.getString("duration");

        Item i = new Item(total_amount, base_currency, name, total_emissions_kg, aircraft_name, destination_name, origin_name);
        i.setArrive_time_nf(arrivalTime);
        i.setDepart_time_nf(departTime);
        i.setDuration_nf(durationTime);
        return i;
    }
}