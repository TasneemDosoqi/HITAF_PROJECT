package com.example.hitaf_project;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class type extends AppCompatActivity {
    FirebaseStorage storage;
    StorageReference gsReference;
    EditText TypingText;
    FirebaseAuth auth;
    VideoView Avatar ;
    TextView errror;
    ArrayList<String> VideoArray = new ArrayList<>();
    int x = 0 ;
    String word;
    int indexOfSpace;
    int nextIndexOfSpace;
    String sentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        auth = FirebaseAuth.getInstance();
        Avatar = findViewById(R.id.video_view_type);
        errror = findViewById(R.id.errrrror);
        TypingText = (EditText) findViewById(R.id.Tayping);
        errror.setText(" ");
        VideoArray.clear();

    }

    //Function to clean the text from punctuation
    public static String parseText(String text) {
        String temp = "";
        Pattern pattern = Pattern.compile("\\p{Punct}");
        try {
            if (text != null && !text.isEmpty()) {
                Matcher matcher = pattern.matcher(text);
                if (matcher.find()) {
                    temp = temp + text.substring(0, matcher.start()) + "";

                    temp = temp + parseText(text.substring(matcher.end()));
                } else {
                    temp = temp + text;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        temp = temp.replaceAll("\\p{Punct}", " ");
        if (temp.isEmpty()) {
            return text;
        }
        return temp;
    }

    //Function to translate text into video by retrieve the videos from the database
    public void translate_word(View view) {
        errror.setText(" ");
        if (TypingText.getText().toString().equals("")){
            errror.setText("لا يوجد نص لترجمته، فضلاً اكتب ماتريد ترجمته أولاً ");
        }
        String sentence = TypingText.getText().toString();
        String word = "";


        try {
            sentence = parseText(sentence);
            System.out.println(sentence);
            sentence = normalization(sentence);
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringTokenizer t = new StringTokenizer(sentence);

        VideoArray.clear();

        // Tokenization process and eliminate the space
        while (t.hasMoreTokens()) {

            word = t.nextToken();
            VideoArray.add(word);
            System.out.println("up x");
            System.out.println(word);

        }

        trn(VideoArray);

    }

    //Function to split the word that is not in the database to litters
    public void toLetters (String word){

        ArrayList<String> VideoArray = new ArrayList<>();
        Character L;
        for (int i =0 ; i < word.length() ; i++){
            L= word.charAt(i);
            VideoArray.add(L.toString());
            System.out.println(L);

        }

        trn(VideoArray);

        errror.setText("عذراً هِتاف لازالت تتعلم الأسماء ، ولكن تستطيع ترجمة أسم واحد فقط حالياً");
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.nothing;
        Uri uri = Uri.parse(videoPath);
        Avatar.setVideoURI(uri);
        Avatar.start();
        return;

    }

    //Function To retrieve the videos based on the words
    private void trn(final ArrayList<String> VideoArray) {
        if(VideoArray.size() > 0){
            storage = FirebaseStorage.getInstance();
            gsReference = storage.getReferenceFromUrl("gs://hitaf-application-project.appspot.com/videos/" + VideoArray.get(0) + ".mp4");
            gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Avatar.setVideoURI(uri);
                    Avatar.start();
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    //In case the word is not in the database
                    toLetters(VideoArray.get(0));

                }
            });
            Avatar.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    VideoArray.remove(0);
                    trn(VideoArray);
                }
            });
        }
    }

    //Function to NLP the text (normalization)
    private String normalization(String input) {

        //Remove tashkeel
        input=input.replaceAll("\u064B", "");//ARABIC FATHATAN
        input=input.replaceAll("\u064C", "");//ARABIC DAMMATAN
        input=input.replaceAll("\u064D", "");//ARABIC KASRATAN
        input=input.replaceAll("\u064E", "");//ARABIC FATHA
        input=input.replaceAll("\u064F", "");//ARABIC DAMMA
        input=input.replaceAll("\u0650", "");//ARABIC KASRA
        input=input.replaceAll("\u0651", "");//ARABIC SHADDA
        input=input.replaceAll("\u0652", "");//ARABIC SUKUN
        input=input.replaceAll("\u0653", "");//ARABIC MADDAH ABOVE
        input=input.replaceAll("\u0654", "");//ARABIC HAMZA ABOVE
        input=input.replaceAll("\u0655", "");//ARABIC HAMZA BELOW
        input=input.replaceAll("\u0656", "");//ARABIC SUBSCRIPT ALEF
        input=input.replaceAll("\u0657", "");//ARABIC INVERTED DAMMA
        input=input.replaceAll("\u0658", "");//ARABIC MARK NOON GHUNNA
        input=input.replaceAll("\u0659", "");//ARABIC ZWARAKAY
        input=input.replaceAll("\u065A", "");//ARABIC VOWEL SIGN SMALL V ABOVE
        input=input.replaceAll("\u065B", "");//ARABIC VOWEL SIGN INVERTED SMALL V ABOVE
        input=input.replaceAll("\u065C", "");//ARABIC VOWEL SIGN DOT BELOW
        input=input.replaceAll("\u065D", "");//ARABIC REVERSED DAMMA
        input=input.replaceAll("\u065E", "");//ARABIC FATHA WITH TWO DOTS
        input=input.replaceAll("\u065F", "");//ARABIC WAVY HAMZA BELOW
        input=input.replaceAll("\u0670", "");//ARABIC LETTER SUPERSCRIPT ALEF

        //REMOVE IF FIRST 2 LETTER IS ال
        input=input.replaceAll("ال", "");

        //Replace Waw Hamza Above by Waw
        input=input.replaceAll("\u0624", "\u0648");

        //Replace named number with digit number
        input=input.replaceAll("صفر", "٠");
        input=input.replaceAll("واحد", "١");
        input=input.replaceAll("اثنين", "٢");
        input=input.replaceAll("ثلاثة", "٣");
        input=input.replaceAll("اربعة", "٤");
        input=input.replaceAll("خمسة", "٥");
        input=input.replaceAll("ستة", "٦");
        input=input.replaceAll("سبعة", "٧");
        input=input.replaceAll("ثمانية", "٨");
        input=input.replaceAll("تسعة", "٩");


        return input;

    }

    //Function to open homepage
    public void BackButton(View view) {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    //Function to reset the text
    public void ResetText(View v) {
        VideoArray.clear();
        TypingText.setText("");
        errror.setText(" ");

    }

    //Function to repeat the translation again
    public void repeatSign(View view) {
        if (TypingText.getText().toString().equals("")){
            errror.setText("لا يوجد نص لترجمته، فضلاً اكتب ماتريد ترجمته أولاً ");
        }
        else{
            translate_word(view);
        }
    }

}
