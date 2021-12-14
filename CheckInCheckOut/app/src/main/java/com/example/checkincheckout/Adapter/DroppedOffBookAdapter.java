package com.example.checkincheckout.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.checkincheckout.CheckInActivity;
import com.example.checkincheckout.Interface.IBookClickListener;
import com.example.checkincheckout.Model.CheckedOutBook;
import com.example.checkincheckout.R;
import com.example.checkincheckout.SearchActivity;

import java.util.List;

public class DroppedOffBookAdapter extends RecyclerView.Adapter<DroppedOffBookAdapter.MyViewHolder> {

    SearchActivity mSearchActivity;

    Context context;
    List<CheckedOutBook> bookList;

    public DroppedOffBookAdapter(Context context, List<CheckedOutBook> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checked_out_book_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.title.setText(bookList.get(i).getTitle());
        holder.author.setText(bookList.get(i).getAuthor());
        holder.genre.setText(bookList.get(i).getGenre());
        holder.book_type.setText(bookList.get(i).getBook_type());
        holder.stock.setText(String.format("Stock: %s", bookList.get(i).getStock()));
        holder.isbn.setText(String.format("ISBN: %s", bookList.get(i).getIsbn()));
        Glide.with(holder.front_cover.getContext()).load(bookList.get(i).getFront_cover()).into(holder.front_cover);
        holder.email.setText(String.format("Email attached to book: %s", bookList.get(i).getEmail()));
        /*holder.front_cover.setText(bookList.get(i).getFront_cover());*/

        if(i % 2 == 0)
            holder.root_view.setCardBackgroundColor(Color.parseColor("#E1E1E1"));

        holder.setBookClickListener(new IBookClickListener() {

            @Override
            public void onBookClick(View view, int position) {
                //bookList.get(position).getTitle()
                //Toast.makeText(context, ""+bookList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, CheckInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("ID", bookList.get(position).getId());
                i.putExtra("Title", bookList.get(position).getTitle());
                i.putExtra("Author", bookList.get(position).getAuthor());
                i.putExtra("Genre", bookList.get(position).getGenre());
                i.putExtra("Book Type", bookList.get(position).getBook_type());
                i.putExtra("Stock", bookList.get(position).getStock());
                i.putExtra("ISBN", bookList.get(position).getIsbn());
                i.putExtra("Front Cover", bookList.get(position).getFront_cover());
                //i.putExtra("Username", mSearchActivity.getUsername());
                i.putExtra("Email attached to book", bookList.get(position).getEmail());
                i.putExtra("History Id", bookList.get(position).getHistory_id());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView root_view;
        TextView title, author, genre, book_type, stock, isbn, email;
        ImageView front_cover;

        IBookClickListener bookClickListener;

        public void setBookClickListener(IBookClickListener bookClickListener) {
            this.bookClickListener = bookClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            root_view = (CardView) itemView.findViewById(R.id.root_view);

            title = (TextView) itemView.findViewById(R.id.text_title);
            author = (TextView) itemView.findViewById(R.id.text_author);
            genre = (TextView) itemView.findViewById(R.id.text_genre);
            book_type = (TextView) itemView.findViewById(R.id.text_book_type);
            stock = (TextView) itemView.findViewById(R.id.text_stock);
            isbn = (TextView) itemView.findViewById(R.id.text_isbn);
            front_cover = (ImageView) itemView.findViewById(R.id.cover_image);
            email = (TextView) itemView.findViewById(R.id.text_email);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            bookClickListener.onBookClick(view, getAdapterPosition());
        }
    }
}
