package com.habp.fhouse.ui.wishlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.WishListFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.util.ArticleTypeHelper;
import com.habp.fhouse.util.ConvertHelper;

import java.util.List;

public class ArticleAdapter extends BaseAdapter  {
    private List<Article> articles;

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_wishlist, parent, false);
        }
        Article article = articles.get(position);

        Glide.with(convertView).load(article.getPhotoPath())
                .into((ImageView) convertView.findViewById(R.id.imgHouse));

        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtAddress = convertView.findViewById(R.id.textPlace);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);
        TextView txtArticleType = convertView.findViewById(R.id.txtArticleType);
        ImageView imvWishList = convertView.findViewById(R.id.imvWishList);

        imvWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WishListFirestoreRepository wishRep
                        = new WishListFirestoreRepository(FirebaseFirestore.getInstance());
                WishlistPresenter wishlistPresenter =
                        new WishlistPresenter(wishRep, FirebaseAuth.getInstance());

                Article article = articles.get(position);
                wishlistPresenter.removeWishList(article.getArticleId());
                articles.remove(position);
                notifyDataSetChanged();
            }
        });

        String price = ConvertHelper.convertToMoneyFormat(article.getPrice());

        txtName.setText(article.getArticleName());
        txtAddress.setText(article.getHouseAddress());
        txtPrice.setText(price);
        txtArticleType.setText(ArticleTypeHelper.getArticleType(article.getArticleType()));

        return convertView;
    }

}
