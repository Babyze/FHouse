package com.habp.fhouse.ui.articledetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.data.datasource.WishListFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.WishList;
import com.habp.fhouse.ui.sign.SignInActivity;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.UUID;

public class ArticleDetailActivity extends AppCompatActivity implements ArticleDetailContract.View {
    private FirebaseAuthRepository firebaseAuthRepository;
    private ArticleDetailPresenter articleDetailPresenter;
    private TextView txtArticleName, txtPrice, txtDescription, txtHouseAddress, txtPhoneNumber;
    private ImageView imageArticle, imageWishlist;
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_detail);
        initData();

        firebaseAuthRepository = new FirebaseAuthRepository(FirebaseAuth.getInstance());
        articleDetailPresenter = new ArticleDetailPresenter(firebaseAuthRepository, this);

        articleDetailPresenter.checkAuthorization(false);
        articleDetailPresenter.loadData(article);

    }

    private void initData() {
        txtArticleName = findViewById(R.id.txtArticleName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtHouseAddress = findViewById(R.id.txtHouseAddress);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        imageArticle = findViewById(R.id.imageArticle);
        imageWishlist = findViewById(R.id.imageWishlist);

        Intent intent = getIntent();
        article = (Article) intent.getSerializableExtra("article");
    }

    public void clickToReturn(View view) {
        Intent intent = getIntent();
        intent.putExtra("modifiedArticle", article);
        setResult(2, intent);
        finish();
    }

    public void clickToCall(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
           Intent callIntent = new Intent(Intent.ACTION_CALL);
           callIntent.setData(Uri.parse("tel:" + article.getPhoneNumber()));
           startActivity(callIntent);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.CALL_PHONE
            }, 1);
        }
    }

    public void clickToShowMap(View view) {
        String map = DatabaseConstraints.GOOGLE_MAP_URL + article.getHouseAddress();
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(Intent.createChooser(mapIntent, "Select an application"));
    }

    public void clickToAddUnWishList(View view) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        WishListFirestoreRepository wishListFirestoreRepository =
                    new WishListFirestoreRepository(firebaseFirestore, firebaseAuth);
        if(article.getWishListId() == null) {
            WishList wishList = new WishList(UUID.randomUUID().toString(), firebaseAuth.getUid(), article.getArticleId());
            wishListFirestoreRepository.createWishList(wishList, isSuccess -> {
                if(isSuccess) {
                    imageWishlist.setImageResource(R.mipmap.ic_wishlist_activate);
                    article.setWishListId(wishList.getWishListId());
                    Toast.makeText(ArticleDetailActivity.this, "Add to wish list success", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(ArticleDetailActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
            });
        } else {
            wishListFirestoreRepository.deleteWishList(article.getWishListId(), isSuccess -> {
                if(isSuccess) {
                    imageWishlist.setImageResource(R.mipmap.ic_wishlist_unactivate);
                    article.setWishListId(null);
                    Toast.makeText(ArticleDetailActivity.this, "Remove wish list success", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(ArticleDetailActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
            });
        }
    }

    @Override
    public void startSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void showData() {
        txtArticleName.setText(article.getArticleName());
        txtPrice.setText(ConvertHelper.convertToMoneyFormat(article.getPrice()));
        txtDescription.setText(article.getArticleDescription());
        txtHouseAddress.setText(article.getHouseAddress());
        txtPhoneNumber.setText(article.getPhoneNumber());
        Glide.with(getApplicationContext()).load(article.getPhotoPath()).into(imageArticle);
    }

    @Override
    public void setActivityWishlist(boolean status) {
        if(status)
            imageWishlist.setImageResource(R.mipmap.ic_wishlist_activate);
        else
            imageWishlist.setImageResource(R.mipmap.ic_wishlist_unactivate);
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        articleDetailPresenter.checkAuthorization(true);
        WishListFirestoreRepository wishListFirestoreRepository =
                new WishListFirestoreRepository(FirebaseFirestore.getInstance());
        wishListFirestoreRepository.getWishList(firebaseAuthRepository.getUserId(), article.getArticleId(), wishList -> {
            article.setWishListId(wishList.getWishListId());
            articleDetailPresenter.loadData(article);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + article.getPhoneNumber()));
                    startActivity(callIntent);
                }
            }
        }
    }
}