package com.brianestrada.moviebrowser.adapters.reviews;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.models.Review;


class ViewHolderReview extends RecyclerView.ViewHolder {
    TextView tvAuthor;
    TextView tvContent;

    ViewHolderReview(View itemView) {
        super(itemView);

        tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);

        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
    }

    void bind(Review review) {

        tvAuthor.setText(review.getAuthor());

        tvContent.setText(review.getContent());
    }

}
