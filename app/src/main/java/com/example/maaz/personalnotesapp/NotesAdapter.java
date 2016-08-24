package com.example.maaz.personalnotesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by maaz on 7/8/16.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {

    private LayoutInflater mInflater;
    private List<Note> mNotes = Collections.emptyList();
    private Context mContext;

    public NotesAdapter(Context mContext, List<Note> mNotes) {

        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mNotes = mNotes;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_notes_adapter_layout, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        holder.mId.setText(mNotes.get(position).getId() + "");
        holder.mTitle.setText(mNotes.get(position).getTitle());

        if (mNotes.get(position).getType().equals(AppConstant.LIST)) {
            NoteCustomList noteCustomList = new NoteCustomList(mContext);
            noteCustomList.setUpForHomeAdapter(mNotes.get(position).getDescription());
            holder.mLinearLayout.removeAllViews();
            holder.mLinearLayout.addView(noteCustomList);
            holder.mDescription.setVisibility(View.GONE);
        } else {
            holder.mLinearLayout.setVisibility(View.GONE);
            holder.mDescription.setText(mNotes.get(position).getDescription());
        }
        holder.mDate.setText(mNotes.get(position).getDate() + " " + mNotes.get(position).getTime());

        if (mNotes.get(position).getBitmap() != null) {
            holder.mImage.setImageBitmap(mNotes.get(position).getBitmap());
            holder.mImage.setVisibility(View.VISIBLE);
        } else if (mNotes.get(position).getImagePath() == null || mNotes.get(position).getImagePath().equals(AppConstant.NO_IMAGE)) {
            holder.mImage.setVisibility(View.GONE);
        } else {
            holder.mImage.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public void setData(List<Note> notes) {
        this.mNotes = notes;
    }

    public void delete(int position) {
        mNotes.remove(position);
        notifyItemRemoved(position);
    }

    public void notifyImageObtained() {
        notifyDataSetChanged();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {

        TextView mTitle, mDescription, mDate, mId;
        ImageView mImage;
        LinearLayout mLinearLayout;

        public NoteHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title_note_custom_home);
            mDescription = (TextView) itemView.findViewById(R.id.description_note_custom_home);
            mDate = (TextView) itemView.findViewById(R.id.date_time_note_custom_home);
            mId = (TextView) itemView.findViewById(R.id.id_note_custom_home);
            mImage = (ImageView) itemView.findViewById(R.id.image_note_custom_home);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.home_list);
        }
    }
}
