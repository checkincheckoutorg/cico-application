package com.example.checkincheckout.Adapter;

import android.graphics.Color;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.checkincheckout.Model.Book;
import com.example.checkincheckout.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.title.setText(bookList.get(i).getTitle());
        holder.author.setText(bookList.get(i).getAuthor());
        holder.genre.setText(bookList.get(i).getGenre());
        holder.book_type.setText(bookList.get(i).getBook_type());
        holder.stock.setText(Integer.toString(bookList.get(i).getStock()));
        Glide.with(holder.front_cover.getContext()).load(bookList.get(i).getFront_cover()).into(holder.front_cover);
        /*holder.front_cover.setText(bookList.get(i).getFront_cover());*/

        if(i % 2 == 0)
            holder.root_view.setCardBackgroundColor(Color.parseColor("#E1E1E1"));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView root_view;
        TextView title, author, genre, book_type, stock;
        ImageView front_cover;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            root_view = (CardView) itemView.findViewById(R.id.root_view);

            title = (TextView) itemView.findViewById(R.id.text_title);
            author = (TextView) itemView.findViewById(R.id.text_author);
            genre = (TextView) itemView.findViewById(R.id.text_genre);
            book_type = (TextView) itemView.findViewById(R.id.text_book_type);
            stock = (TextView) itemView.findViewById(R.id.text_stock);
            front_cover = (ImageView) itemView.findViewById(R.id.cover_image);
        }
    }
}
