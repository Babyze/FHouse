package com.habp.fhouse.ui.article.createArticle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habp.fhouse.R;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;
import java.util.List;

public class ArticleAdapter extends BaseAdapter {
    List<Article> listArticle;

    public ArticleAdapter(List<Article> listArticle) {
        this.listArticle = listArticle;
    }

    public List<Article> getListArticle() {
        return listArticle;
    }

    public void setListArticle(List<Article> listArticle) {
        this.listArticle = listArticle;
    }

    @Override
    public int getCount() {
        return listArticle.size();
    }

    @Override
    public Object getItem(int position) {
        return listArticle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.card_article_management,parent, false);
        }
        Article article = listArticle.get(position);
        Glide.with(convertView).load(article.getPhotoPath()).into((ImageView) convertView.findViewById(R.id.imgHouse));
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtAddress = convertView.findViewById(R.id.txtAddress);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);
        TextView txtRequirement = convertView.findViewById(R.id.txtRequirementArticle);
        if (article.getArticleType() == DatabaseConstraints.HOUSE_ARTICLE){
            txtRequirement.setText("House For Rent");
        }else if (article.getArticleType() == DatabaseConstraints.ROOM_ARTICLE){
            txtRequirement.setText("Room For Rent");
        }else if (article.getArticleType() == DatabaseConstraints.BED_ARTICLE){
            txtRequirement.setText("Bed For Rent");
        }

        txtName.setText(article.getArticleName());
        txtAddress.setText(article.getHouseAddress());
        String price = ConvertHelper.convertToMoneyFormat(article.getPrice());
        txtPrice.setText(price + " VND");
        return convertView;
    }

}
