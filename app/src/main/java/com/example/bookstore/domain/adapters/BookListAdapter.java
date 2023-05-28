package com.example.bookstore.domain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.BookActivity;
import com.example.bookstore.R;
import com.example.bookstore.domain.model.BookItem;
import com.example.bookstore.model.BookViewModel;
import com.example.bookstore.utils.ImageUtil;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {

    private final Context mContext;
    private ViewModelStoreOwner mModelOwner;
    private List<BookItem> mData;
    private LayoutInflater mInflater;

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView bookTitleView;

        public final TextView bookAuthorView;
        public final TextView bookDescriptionView;
        public final ImageView bookPreviewImageView;
        final BookListAdapter mAdapter;
        private final ImageView mAddToReadButton;
        private final ImageView mAddToFavoriteButton;

        public BookViewHolder(@NonNull View itemView, BookListAdapter adapter) {
            super(itemView);

            this.mAdapter = adapter;

            bookTitleView = itemView.findViewById(R.id.bookTitle);
            bookAuthorView = itemView.findViewById(R.id.bookAuthor);
            bookDescriptionView = itemView.findViewById(R.id.bookDescription);
            bookPreviewImageView = itemView.findViewById(R.id.bookPreviewImage);

            mAddToReadButton = itemView.findViewById(R.id.toReadImageView);
            mAddToFavoriteButton = itemView.findViewById(R.id.toFavoriteImageView);

            itemView.setOnClickListener(this);
            mAddToReadButton.setOnClickListener(this);
            mAddToFavoriteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            BookItem bookItem = mAdapter.mData.get(position);
            BookViewModel model = new ViewModelProvider(mModelOwner).get(BookViewModel.class);

            if (v.getId() == R.id.toReadImageView) {
                if (bookItem.isToRead()) {
                    model.removeBookFromReadList(bookItem);
                } else {
                    model.addBookToReadList(bookItem);
                }
            } else if (v.getId() == R.id.toFavoriteImageView) {
                if (bookItem.isFavorite()) {
                    model.removeBookFromFavoriteList(bookItem);
                } else {
                    model.addBookToFavoriteList(bookItem);
                }
            } else {
                Intent intent = new Intent(mContext, BookActivity.class);
                intent.addCategory("Intent.CATEGORY_OPENABLE");
                intent.setType("image/*");
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(BookActivity.EXTRA_PARAM_BOOK_ID, bookItem.getId());
                mContext.startActivity(intent);
            }
        }
    }

    public BookListAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public BookListAdapter(Context context, ViewModelStoreOwner modelOwner) {
        this(context);
        this.mModelOwner = modelOwner;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookItem currentBookItem = mData.get(position);
        holder.bookTitleView.setText(currentBookItem.getName());
        holder.bookAuthorView.setText(currentBookItem.getAuthor());
        holder.bookDescriptionView.setText(currentBookItem.getDescription());

        holder.mAddToReadButton.setImageResource(currentBookItem.isToRead()
                ? R.drawable.ic_to_read : R.drawable.ic_add_to_read);

        holder.mAddToFavoriteButton.setImageResource(currentBookItem.isFavorite()
                ? R.drawable.ic_favorite : R.drawable.ic_make_favorite);

        setImage(holder.bookPreviewImageView, currentBookItem.getImageData());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<BookItem> mData) {
        this.mData = mData;
    }

    private void setImage(ImageView imageView, String imageData) {
        if (imageData == null) {
            return;
        }

        imageView.setImageBitmap(ImageUtil.getImageFromBase64(imageData));
    }

}
