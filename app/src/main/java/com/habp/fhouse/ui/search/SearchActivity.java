package com.habp.fhouse.ui.search;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import com.habp.fhouse.ui.articledetail.ArticleDetailActivity;
import com.habp.fhouse.util.ListHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    private EditText edtSearch;
//    private Button btnSort, btnFilter;
    private SwipeRefreshLayout swipeArticle;
    private TextView txtNoResult;
    private ListView lvResult;
    private String keyWord;
    private ArticleSnap articleSnap;

    private List<Article> listResult;

    private ArticleAdapter articleAdapter;
    private SearchPresenter searchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initData();

        searchPresenter.loadArticleResult(null, keyWord);

        checkListDataIsExist();

        configListViewScrollPagination();

        configKeywordType();
    }

    private void initData() {
        edtSearch = findViewById(R.id.edtSearch);
//        btnSort = findViewById(R.id.btnSort);
//        btnFilter = findViewById(R.id.btnFilter);
        swipeArticle = findViewById(R.id.swipeArticle);
        txtNoResult = findViewById(R.id.txtNoResult);
        lvResult = findViewById(R.id.lvResult);

        listResult = new ArrayList<>();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        HouseFirestoreRepository houseFirestoreRepository = new HouseFirestoreRepository(firebaseFirestore);
        ArticleFirestoreRepository articleFirestoreRepository = new ArticleFirestoreRepository(firebaseFirestore, firebaseAuth);

        searchPresenter = new SearchPresenter(this, houseFirestoreRepository, articleFirestoreRepository);
        articleAdapter = new ArticleAdapter();
        articleAdapter.setListArticle(listResult);
        lvResult.setAdapter(articleAdapter);

        Intent intent = getIntent();
        keyWord = intent.getStringExtra("keyword");

        edtSearch.setText(keyWord);
    }

    private void configKeywordType() {
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                    keyWord = edtSearch.getText().toString();
                    searchPresenter.loadArticleResult(null, keyWord);
                    return true;
                }
                return false;
            }
        });
    }

    private void checkListDataIsExist() {
        if(listResult.size() == 0) {
            txtNoResult.setVisibility(View.VISIBLE);
            lvResult.setVisibility(View.INVISIBLE);
            txtNoResult.setText("No result found");
        } else {
            txtNoResult.setVisibility(View.INVISIBLE);
            lvResult.setVisibility(View.VISIBLE);
        }
    }

    private void configListViewScrollPagination() {
        lvResult.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int lastScrollItemPosition = lvResult.getLastVisiblePosition();
                int totalItem = articleAdapter.getCount() - 1;
                if(lastScrollItemPosition >= totalItem && scrollState == SCROLL_STATE_IDLE) {
                    Toast.makeText(SearchActivity.this, "Loading more boarding", Toast.LENGTH_SHORT).show();
                    searchPresenter.loadArticleResult(articleSnap.getLastSnap(), keyWord);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        swipeArticle.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(SearchActivity.this, "Please wait", Toast.LENGTH_SHORT).show();
                searchPresenter.loadArticleResult(null, keyWord);
            }
        });

        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = listResult.get(position);
                Intent intent = new Intent(SearchActivity.this, ArticleDetailActivity.class);
                intent.putExtra("article", article);
                intent.putExtra("articlePosition", position);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void showResult(boolean isLoadMore, ArticleSnap articleSnap) {
        List<Article> articles = articleSnap.getArticleList();
        this.articleSnap = articleSnap;
        if(articles != null) {
            if(isLoadMore) {
                listResult = ListHelper.addCollection(listResult, articles);
                System.out.println(listResult.size() + " Size Ne");
            } else {
                listResult = articles;
                articleAdapter.setListArticle(listResult);
            }
        }
        checkListDataIsExist();
        articleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == 2) {
                int articlePosition = data.getIntExtra("articlePosition", 0);
                Article modifiedArtical = (Article) data.getSerializableExtra("modifiedArticle");
                Article article = listResult.get(articlePosition);
                article.setWishListId(modifiedArtical.getWishListId());
            }
        }
    }

    public void clickToClose(View view) {
        finish();
    }
}