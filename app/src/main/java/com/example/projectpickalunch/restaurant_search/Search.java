package com.example.projectpickalunch.restaurant_search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.example.projectpickalunch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    EditText searchEdt;

    String searchText;
    String hits = "hits";
    ArrayList<String> list;


    //Fragment

    FragmentManager searchFragmentManager;
    FragmentTransaction searchFragmentTransaction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        searchEdt = findViewById(R.id.search_editText);

        //Fragment
        //메뉴피커 프래그먼트
        searchFragmentManager = getSupportFragmentManager();
        searchFragmentTransaction = searchFragmentManager.beginTransaction();
        searchFragmentTransaction.add(R.id.search_Fragment, new SearchBeforeFragment());
        searchFragmentTransaction.commit();

        //Algolia
        Client client = new Client("49JNIHFN0A", "6ef6ce72016a3ff5695d2a1fcfd80afa");
        Index index = client.getIndex("restorantName");
        CompletionHandler completionHandler = new CompletionHandler() {
            @Override
            public void requestCompleted(@Nullable JSONObject jsonObject, @Nullable AlgoliaException e) {
                    JSONArray jsonArray = new JSONArray();
                    ArrayList<SearchRestorantName> listItem = new ArrayList<SearchRestorantName>();
                    list = new ArrayList<String>();
                try {
                    if(jsonObject != null) {
                        jsonArray = jsonObject.getJSONArray("hits");
                    }
                    if(jsonArray != null){
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject json = jsonArray.getJSONObject(i);
                            String jsonlist = json.getString("name");
                            list.add(jsonlist);
                        }
                    }
                }catch (JSONException ex){
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                switchFragment();
            }
        };


        //index.searchAsync(new Query("t"),completionHandler);


        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                index.searchAsync(new Query(searchEdt.getText().toString())
                        .setAttributesToRetrieve("name")
                        .setHitsPerPage(50), completionHandler);
                List<JSONObject> array = new ArrayList<JSONObject>();
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //뒤로가기 버튼
        ImageButton searchReturnButton = findViewById(R.id.search_return_button);
        searchReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //프래그먼트 변경 함수
    public void switchFragment(){
        Fragment fragment;
        //Bundle로 Fragment에 값 전달을 위한 boolean변수
        if(searchEdt.getText().toString().isEmpty()){
            fragment = new SearchBeforeFragment();
            Log.e("fragTest", "before");
        }
        else{
            Log.e("fragTest", "after");
            fragment = new SearchAfterFragment();
        }

        searchFragmentManager = getSupportFragmentManager();
        searchFragmentTransaction = searchFragmentManager.beginTransaction();

        //Bundle로 값 전달
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("selectedRestorantName", list);
        fragment.setArguments(bundle);
        searchFragmentTransaction.replace(R.id.search_Fragment, fragment);
        searchFragmentTransaction.commit();

    }

}

