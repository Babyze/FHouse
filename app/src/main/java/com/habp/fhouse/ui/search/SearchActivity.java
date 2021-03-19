package com.habp.fhouse.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.ArticleSnap;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    private EditText edtSearch;
    private Button btnSort, btnFilter;
    private SwipeRefreshLayout swipeArticle;
    private TextView txtNoResult;
    private ListView lvResult;
    private String keyWord;

    private SearchPresenter searchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initData();

        configKeywordType();

        Toast.makeText(this, "Search: " + keyWord, Toast.LENGTH_LONG).show();
    }

    private void initData() {
        edtSearch = findViewById(R.id.edtSearch);
        btnSort = findViewById(R.id.btnSort);
        btnFilter = findViewById(R.id.btnFilter);
        swipeArticle = findViewById(R.id.swipeArticle);
        txtNoResult = findViewById(R.id.txtNoResult);
        lvResult = findViewById(R.id.lvResult);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        HouseFirestoreRepository houseFirestoreRepository = new HouseFirestoreRepository(firebaseFirestore);
        ArticleFirestoreRepository articleFirestoreRepository = new ArticleFirestoreRepository(firebaseFirestore, firebaseAuth);
        searchPresenter = new SearchPresenter(this, houseFirestoreRepository, articleFirestoreRepository);

        Intent intent = getIntent();
        keyWord = intent.getStringExtra("keyword");
    }

    private void configKeywordType() {
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                System.out.println("Action: " + actionId);
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                    keyWord = edtSearch.getText().toString();
                    System.out.println("Search: " + keyWord);
//                    searchPresenter.loadArticleResult(keyWord);
                    return true;
                }
                return false;
            }
        });
    }

    private void loadData(String keyWord) {
        Toast.makeText(this, "Key: " + keyWord, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResult(ArticleSnap articleSnap) {

    }
}