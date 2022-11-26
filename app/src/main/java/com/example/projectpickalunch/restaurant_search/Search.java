package com.example.projectpickalunch.restaurant_search;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.example.projectpickalunch.R;

public class Search extends AppCompatActivity {
    EditText searchEdt;

    String searchText;
    String hits = "hits";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        searchEdt = findViewById(R.id.search_editText);

        Client client = new Client("49JNIHFN0A", "2083bbdd34fe14f3ce84ea94897e0a59");

        Index index = client.initIndex("test");


//        var handler = CompletionHandler{content , error ->
//                println(content)
//
//            var list = ArrayList<Apt>()
//
//            var jsonArray  = content?.getJSONArray("hits")
//            if (jsonArray != null) {
//                for(i in 1 until jsonArray.length()) {
//                    var json = jsonArray?.getJSONObject(i)
//                    var apt = Apt(json.getString("aptName"),json.getString("doroJuso"))
//                    list.add(apt)
//                }
//                adapter.list = list
//                adapter.resetting()
//            }
//
//        }

//        try {
//        CompletionHandler completionHandler = new CompletionHandler() {
//            @Override
//            public void requestCompleted(@Nullable JSONObject jsonObject, @Nullable AlgoliaException e) {
//                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
//
//                    JSONArray jsonArray = jsonObject.getJSONArray(hits);
//
//            }
//        };
//        }catch (JSONException ex){
//            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//        catch (AlgoliaException e){
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//        }

        //index.searchAsync(new Query("t"),completionHandler);


//        searchEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                index.searchAsync(new Query(searchText), completionHandler);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });


        //뒤로가기 버튼
        ImageButton searchReturnButton = findViewById(R.id.search_return_button);
        searchReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

