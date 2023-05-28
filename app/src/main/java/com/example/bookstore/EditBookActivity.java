package com.example.bookstore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.domain.model.BookItem;
import com.example.bookstore.utils.ImageUtil;

public class EditBookActivity extends AppCompatActivity {

    public static final String EXTRAS_ADDED_BOOK = "com.example.bookstore.EXTRAS.added_book";

    private TextView mTitleTextInput;
    private TextView mAuthorTextInput;

    private TextView mDesriptionTextInput;
    private TextView mImageUriInput;
    private ImageView mBookCoverPreviewImage;
    private ActivityResultLauncher<PickVisualMediaRequest> mPickMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        mTitleTextInput = findViewById(R.id.titleInput);
        mAuthorTextInput = findViewById(R.id.authorInput);
        mDesriptionTextInput = findViewById(R.id.descriptionInput);
        mImageUriInput = findViewById(R.id.imageUriInput);

        mBookCoverPreviewImage = findViewById(R.id.bookCoverPreviewImage);

        mPickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                mImageUriInput.setText(uri.toString());
                mBookCoverPreviewImage.setImageURI(uri);
            }
        });

        View applyBtn = findViewById(R.id.applyBtn);
        applyBtn.setOnClickListener(this::onApplyButtonClick);
    }

    public void onSelectButtonClick(View v) {
        mPickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    public void onApplyButtonClick(View v) {
        String title = mTitleTextInput.getText().toString();
        String author = mAuthorTextInput.getText().toString();

        if (title.isEmpty()) {
            Toast.makeText(this,"Cannot save a book record. Title is empty!", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (author.isEmpty()) {
            Toast.makeText(this,"Cannot save a book record. Author is empty!", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        String description = mDesriptionTextInput.getText().toString();

        BookItem b = new BookItem();
        b.setName(title);
        b.setAuthor(author);
        b.setDescription(description);


        String imageUrl = mImageUriInput.getText().toString();
        if (!imageUrl.isEmpty()) {
            b.setImageUrl(imageUrl);
            String imageData = ImageUtil.getBase64EncodedThumbnail(imageUrl, getContentResolver());
            if (imageData != null) {
                b.setImageData(imageData);
            }
        }

        Intent output = new Intent();
        output.putExtra(EXTRAS_ADDED_BOOK, b);
        setResult(RESULT_OK, output);
        finish();
    }
}