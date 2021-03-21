package com.habp.fhouse.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.UserFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.ArticleSnap;
import com.habp.fhouse.data.model.User;
import com.habp.fhouse.ui.articledetail.ArticleDetailActivity;
import com.habp.fhouse.ui.search.SearchActivity;
import com.habp.fhouse.util.ListHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {

    private ListView lvHomePage;
    private Button btnSearch;
    private EditText edtSearch;
    private TextView txtFullName;
    private TextView txtNoResult;
    private SwipeRefreshLayout swipeArticle;
    private HomeAdapter adapter;
    private HomePresenter homePresenter;
    private ArticleSnap articleSnap;
    private List<Article> listArticleInHomePage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initData(view);

        showArticleData();

        configListViewScrollPagination();

        return view;
    }

    private void initData(View view) {
        lvHomePage = view.findViewById(R.id.lvHomePage);
        swipeArticle = view.findViewById(R.id.swipeArticle);
        txtFullName = view.findViewById(R.id.txtFullName);
        txtNoResult = view.findViewById(R.id.txtNoResult);
        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);

        listArticleInHomePage = new ArrayList<>();
        adapter = new HomeAdapter();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable searchKeyWord = edtSearch.getText();
                homePresenter.searchArticle(searchKeyWord);
            }
        });

    }

    private void showArticleData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        ArticleFirestoreRepository articleFirestoreRepository =
                new ArticleFirestoreRepository(firebaseFirestore, firebaseAuth);
        UserFirestoreRepository userFirestoreRepository =
                new UserFirestoreRepository(firebaseFirestore, firebaseAuth);

        homePresenter = new HomePresenter(articleFirestoreRepository, userFirestoreRepository, this);

        homePresenter.loadUserInfo();
        homePresenter.loadArticle(null, articleSnap -> {
            this.articleSnap = articleSnap;
            showArticle(false, articleSnap.getArticleList());
            adapter.setListArticle(listArticleInHomePage);
            lvHomePage.setAdapter(adapter);
        });
    }

    private void configListViewScrollPagination() {
        lvHomePage.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int lastScrollItemPosition = lvHomePage.getLastVisiblePosition();
                int totalItem = adapter.getCount() - 1;
                if(lastScrollItemPosition >= totalItem && scrollState == SCROLL_STATE_IDLE) {
                    Toast.makeText(getContext(), "Loading more boarding", Toast.LENGTH_SHORT).show();
                    homePresenter.loadArticle(articleSnap.getLastSnap(), articleSnap -> {
                        HomeFragment.this.articleSnap = articleSnap;
                        showArticle(true, articleSnap.getArticleList());
                    });
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        swipeArticle.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "Please wait", Toast.LENGTH_SHORT).show();
                homePresenter.loadArticle(null, articleSnap -> {
                    HomeFragment.this.articleSnap = articleSnap;
                    showArticle(false, articleSnap.getArticleList());
                    swipeArticle.setRefreshing(false);
                });
            }
        });

        lvHomePage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = listArticleInHomePage.get(position);
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                intent.putExtra("article", article);
                intent.putExtra("articlePosition", position);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        homePresenter.loadUserInfo();
        this.showArticle(false, listArticleInHomePage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1) {
            if(resultCode == 2) {
                int articlePosition = data.getIntExtra("articlePosition", 0);
                Article modifiedArtical = (Article) data.getSerializableExtra("modifiedArticle");
                Article article = listArticleInHomePage.get(articlePosition);
                article.setWishListId(modifiedArtical.getWishListId());
            }
        }
    }

    @Override
    public void showArticle(boolean isLoadMore, List<Article> articles) {
        if(articles != null) {
            if(isLoadMore) {
                listArticleInHomePage = ListHelper.addCollection(listArticleInHomePage, articles);
            } else {
                listArticleInHomePage = articles;
                adapter.setListArticle(listArticleInHomePage);
            }
        }
        if(listArticleInHomePage.size() == 0) {
            txtNoResult.setVisibility(View.VISIBLE);
            lvHomePage.setVisibility(View.INVISIBLE);
            txtNoResult.setText("No result found");
        } else {
            txtNoResult.setVisibility(View.INVISIBLE);
            lvHomePage.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showUserInfo(User user) {
        if(user != null) {
            txtFullName.setText("Hi " + user.getFullName());
        }
    }

    @Override
    public void redirectToSearch(String keyword) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("keyword", keyword);
        startActivity(intent);
    }
}
