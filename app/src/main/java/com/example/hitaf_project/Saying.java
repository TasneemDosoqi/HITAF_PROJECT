package com.example.hitaf_project;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ActivityNotFoundException;
import android.speech.RecognizerIntent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.hitaf_project.type.parseText;

public class Saying extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    String word;
    Button Talking;
    protected static final int RESULT_SPEECH = 1;
    private TextView txtText;
    VideoView Avatar ;
    ArrayList<String> VideoArray = new ArrayList<>();
    FirebaseStorage storage;
    StorageReference gsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saying);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Talking = (Button)findViewById(R.id.start_saying);
        txtText = (TextView) findViewById(R.id.TalkText);

        Avatar = findViewById(R.id.video_view_type);

        //Information in case your device does not Support voice recognition
        Talking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Talking.setBackgroundResource(R.drawable.stop);
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "ar-SA");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    txtText.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "جهازك لا يدعم التسجيل الصوتي",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });

    }

    //Function to write the most accurate word based on scored
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtText.setText(text.get(0));
                }
                break;
            }
        }

        String sentence = txtText.getText().toString();
        StringTokenizer t = new StringTokenizer(sentence);
        String word = "";
        Talking.setBackgroundResource(R.drawable.speak);
        try {
            sentence = parseText(sentence);
            System.out.println(sentence);
            sentence = normalization(sentence);
        } catch (Exception e) {
            e.printStackTrace();
        }

        VideoArray.clear();

        //Tokenization process and eliminate the space
        while (t.hasMoreTokens()) {
            word = t.nextToken();
            VideoArray.add(word);
        }
        //translate text into video by retrieve the videos from the database
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

    //Function to setting homepage
    public void settingPage(View view){
        Intent i=new Intent(this, setting.class);
        startActivity(i);

    }

    //Function to open homepage
    public void BackButton(View view) {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    //Function override for media player class
    @Override
    public void onPrepared(MediaPlayer mp) {

    }
}
