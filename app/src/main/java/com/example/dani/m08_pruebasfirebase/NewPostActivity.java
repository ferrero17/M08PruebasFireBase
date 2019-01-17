package com.example.dani.m08_pruebasfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static android.os.Build.VERSION_CODES.O;

public class NewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        final EditText editText = findViewById(R.id.editTextNewPost);

        findViewById(R.id.botonAddPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = editText.getText().toString();


                if (message.isEmpty()){
                    editText.setError("DEBES ESCRIBIR UN POST!");

                }else{

                    v.setEnabled(false);

                    Post post = new Post();
                    post.content = message;
                    post.author = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    post.authorPhoto = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();

                    String postId = FirebaseDatabase.getInstance().getReference()
                            .push()
                            .getKey();

                    FirebaseDatabase.getInstance().getReference()
                            .child("posts")
                            .child("data")
                            .child(postId)
                            .setValue(post);


                    FirebaseDatabase.getInstance().getReference()
                            .child("user-posts")
                            .child(FirebaseAuth.getInstance().getUid())
                            .child(postId)
                            .setValue(true);


                    FirebaseDatabase.getInstance().getReference()
                            .child("all-posts")
                            .child(postId)
                            .setValue(true);

                    finish();

                }
            }
        });

    }
}
