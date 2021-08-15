package com.example.data3.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.data3.LogInActivity_manager;
import com.example.data3.R;
import com.example.data3.navigation;
import com.example.data3.search;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FirebaseDatabase database;
    TextView title;
    DrawerLayout mDrawerLayout;
    String title2,address;
    String intuid="T1Ro2C1bwhW3o9lgVK08Mg3aFig1";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.activity_signup, container, false);

        final Button button= root.findViewById(R.id.button);
        title=(TextView ) root.findViewById(R.id.title);
        final EditText vaccinename=(EditText)root.findViewById(R.id.vaccinename);
        final EditText biseaname = (EditText)root.findViewById(R.id.biseaname);
        final EditText total = (EditText)root.findViewById(R.id.total2);
        String uid= FirebaseAuth.getInstance().getUid();




        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        database = FirebaseDatabase.getInstance();
        final ImageButton btnSearch = (ImageButton) root.findViewById(R.id.search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSearch = new Intent(getActivity(), search.class);
                startActivity(intentSearch);
            }
        });
        final ImageButton nav_btn = (ImageButton) root.findViewById(R.id.nav_btn);
        mDrawerLayout = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        nav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String type="0";
                        String name=title.getText().toString();
                        String vaccine=vaccinename.getText().toString();
                        String bis =biseaname.getText().toString();
                        String tot =total.getText().toString();
                        type="admin";

                        firebase_add(name,vaccine,bis,tot,address);
                        Intent intent = new Intent(getActivity().getApplicationContext(), com.example.data3.ui.home.HomeFragment.class);
                       startActivity(intent);
                    }
                });
        database_run(intuid);
        return root;
    }
    public void firebase_add(String name,String vaccine,String bis,String tot,String addr){
        database = FirebaseDatabase.getInstance();
        DatabaseReference query = database.getReference("BBS");
        String key = query.push().getKey();

        Map<String, Object> map = new HashMap<>();
        map.put("hospital_name", name);
        map.put("vaccine_types", vaccine);
        map.put("disease_name", bis);
        map.put("vaccine_total",tot);
        map.put("hospital_address",addr);

        query.child(key).setValue(map);
    }
    private void database_run(final String uid) {
        database.getReference("info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String tmpuid = messageData.child("uid").getValue().toString();
                    if(tmpuid.equals(uid))
                    {
                        title2  = messageData.child("hospital_name").getValue().toString();
                        address = messageData.child("hospital_address").getValue().toString();
                        title.setText(title2);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
