package com.examples.finaleproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.examples.finaleproject.R;
import com.tapadoo.alerter.Alerter;

public class MainActivity extends AppCompatActivity {
    public static final int READ_EXT_PER_CODE = 1;
    CardView cv_library,cv_favorites,cv_create_book,cv_create_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAlerter();
        requeatPermissions();

        cv_library=findViewById(R.id.cv_library);
        cv_favorites=findViewById(R.id.cv_favorites);
        cv_create_book=findViewById(R.id.cv_createbook);
        cv_create_category=findViewById(R.id.cv_create_category);


            clickView( cv_library ,Library.class);

            clickView( cv_favorites , Favorites.class);

            clickView( cv_create_book ,CreateBook.class );

            clickView( cv_create_category , CreateCategory.class);


        cv_create_category.setOnClickListener(View->{
            startActivity(new Intent(this,CreateCategory.class));
        });


    }

    public void clickView (View view,Class goTo){
       view. setOnClickListener( view1 -> {
            startActivity(new Intent(this,goTo));
        });
    }

    private void showAlerter() {
        Alerter.create(this)
                .setIcon(R.drawable.alerter_ic_face)
                .setBackgroundColorRes(R.color.color_bg_CB)
                .setTitle(R.string.alertwelcome)
                .setText(R.string.alerttext)
                .enableProgress(true)
                .setProgressColorRes(R.color.color_bg_CC)
                .enableSwipeToDismiss()
                .show();

    }

    private void requeatPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXT_PER_CODE);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                if (requestCode == READ_EXT_PER_CODE) {

                    if (grantResults.length >  0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                        Toast.makeText(this, getString(R.string.havePer), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, getString(R.string.dontper), Toast.LENGTH_SHORT).show();
                        finish();
                    }

        }


    }
}