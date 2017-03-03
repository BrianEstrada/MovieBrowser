package com.brianestrada.moviebrowser.adapters.videos;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.models.Video;

import java.util.List;


public class AdapterVideos extends RecyclerView.Adapter<ViewHolderVideo> {
    private List<Video> videos;
    OnClickListener onClickListener;

    public AdapterVideos(List<Video> videos,OnClickListener onClickListener) {
        this.videos = videos;
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onClick(Video video);
    }

    @Override
    public ViewHolderVideo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_video, parent, false);
        return new ViewHolderVideo(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderVideo holder, int position) {
        Video video = videos.get(position);
        holder.bind(video,onClickListener);
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }
}
