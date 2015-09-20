package com.gkudva.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by gkudva on 9/13/15.
 */
public class InstaAdapter extends ArrayAdapter {

    public InstaAdapter(Context context, List<InstaFotos> object) {
        super(context, android.R.layout.simple_list_item_1, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstaFotos foto = (InstaFotos)getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_foto, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView img      = (ImageView) convertView.findViewById(R.id.ivFoto);
        ImageView ppImg    = (ImageView) convertView.findViewById(R.id.imPpic);
        TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);
        TextView tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
        TextView tvUser    = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvusrname = (TextView) convertView.findViewById(R.id.tvusrname);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvCommentsCount = (TextView) convertView.findViewById(R.id.tvCommentCount);
        TextView tvCommentUsrname = (TextView) convertView.findViewById(R.id.tvCommentUsrname);
        TextView tvCommentUsrname1 = (TextView) convertView.findViewById(R.id.tvCommentUsrname1);

        tvCaption.setText(" " + foto.caption);
        tvComment.setText(" " + foto.comment);
        tvComment1.setText(" " + foto.comment1);
        img.setImageResource(0);
        ppImg.setImageResource(0);
        Picasso.with(getContext()).load(foto.imgURL).into(img);

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Picasso.with(getContext())
                .load(foto.profilepicURL)
                .fit()
                .transform(transformation)
                .into(ppImg);

        tvUser.setText(foto.username);
        tvusrname.setText(" " + foto.username);
        tvLikes.setText(" " + Integer.toString( foto.likeCount) + " likes");
        tvCommentsCount.setText(" " + Integer.toString(foto.total_comments) + " comments");
        tvCommentUsrname.setText(" " + foto.comment_usrname);
        tvCommentUsrname1.setText(" " + foto.comment_usrname1);
        tvTime.setText(DateUtils.getRelativeTimeSpanString(Long.parseLong(foto.createdTime) * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));

        return convertView;
    }
}
