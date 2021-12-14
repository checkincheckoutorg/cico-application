package com.example.checkincheckout.Model;

public class CheckedOutBook {
    private int id, stock, history_id;
    private String isbn, title, author, genre, front_cover, book_type, email;

    public CheckedOutBook() {
    }

    public CheckedOutBook(int id, int stock, int history_id, String isbn, String title, String author, String genre, String front_cover, String book_type, String email) {
        this.id = id;
        this.stock = stock;
        this.history_id = history_id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.front_cover = front_cover;
        this.book_type = book_type;
        this.email = email;
        this.history_id = history_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFront_cover() {
        return front_cover;
    }

    public void setFront_cover(String front_cover) {
        this.front_cover = front_cover;
    }

    public String getBook_type() {
        return book_type;
    }

    public void setBook_type(String book_type) {
        this.book_type = book_type;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

}
