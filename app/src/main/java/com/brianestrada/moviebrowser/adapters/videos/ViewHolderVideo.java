package com.brianestrada.moviebrowser.adapters.videos;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.models.Video;
import com.brianestrada.moviebrowser.utils.MovieUtils;
import com.bumptech.glide.Glide;

class ViewHolderVideo extends RecyclerView.ViewHolder {
    private TextView tvTitle;
    private TextView tvSource;
    private TextView tvResolution;
    private ImageView ivVideoImage;

    ViewHolderVideo(View itemView) {
        super(itemView);

        tvTitle = (TextView) itemView.findViewById(R.id.tv_video_title);

        tvSource = (TextView) itemView.findViewById(R.id.tv_video_source);

        tvResolution = (TextView) itemView.findViewById(R.id.tv_video_resolution);

        ivVideoImage = (ImageView) itemView.findViewById(R.id.iv_video_image);
    }

    void bind(final Video video, final AdapterVideos.OnClickListener onClickListener) {
        if (video != null) {
            tvTitle.setText(video.getName());
            tvResolution.setText(String.valueOf(video.getSize()));
            tvSource.setText(video.getSite());

            String videoThumbnailURL = MovieUtils.getYoutubeThumbnail(video.getKey());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(video);
                }
            });

            Glide.with(itemView.getContext())
                    .load(videoThumbnailURL)
                    .placeholder(R.drawable.video_image_placeholder)
                    .error(R.drawable.video_image_placeholder)
                    .crossFade()
                    .into(ivVideoImage);
        }
    }

}
