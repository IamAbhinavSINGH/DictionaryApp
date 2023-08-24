package com.abhinav.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abhinav.dictionaryapp.Adapters.AntonymAdapter;
import com.abhinav.dictionaryapp.Adapters.SynonymAdapter;
import com.abhinav.dictionaryapp.Models.APIResponse;
import com.abhinav.dictionaryapp.Models.Definitions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    TextView word, phonetic, definition, example1;
    RecyclerView rcSynonyms, rcAntonyms;
    ProgressDialog progressDialog;
    SynonymAdapter synonymAdapter;
    AntonymAdapter antonymAdapter;
    ExtendedFloatingActionButton audioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        progressDialog.setTitle("Loading...");
        progressDialog.show();
        RequestManager requestManager = new RequestManager( MainActivity.this);
        requestManager.getWordMeaning(onFetchDataListener, "Hello");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Fetching Data for " + query);
                progressDialog.show();
                RequestManager requestManager = new RequestManager( MainActivity.this);
                requestManager.getWordMeaning(onFetchDataListener, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private final OnFetchDataListener onFetchDataListener = new OnFetchDataListener() {
        @Override
        public void onFetchData(APIResponse apiResponse, String message) {
            progressDialog.dismiss();
            if(apiResponse == null){
                Toast.makeText(MainActivity.this, "No Data found!!!" , Toast.LENGTH_SHORT).show();
                return;
            }
            showData(apiResponse);
        }

        @Override
        public void onError(String message) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(APIResponse apiResponse) {

        word.setText(apiResponse.getWord());
        String audioURL = "";

        for(int i=0; i<apiResponse.getPhonetics().size(); i++){
            if(apiResponse.getPhonetics().get(i).getText() != null && !apiResponse.getPhonetics().get(i).getText().isEmpty()){
                phonetic.setText(apiResponse.getPhonetics().get(i).getText());
            }
            if(apiResponse.getPhonetics().get(i).getAudio() != null && apiResponse.getPhonetics().get(i).getAudio().length() > 0){
                audioURL = apiResponse.getPhonetics().get(i).getAudio();
            }
        }

        String finalAudioURL = audioURL;
        Log.e("AudioURL -> ", finalAudioURL);

        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(finalAudioURL);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Coundn't play audio at the moment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        definition.setText(apiResponse.getMeanings().get(0).getDefinitions().get(0).getDefinition());

        setExample(apiResponse);
        setSynonymsAndAntonyms(apiResponse);
    }

    private void setSynonymsAndAntonyms(APIResponse apiResponse) {

        List<String> synonymsList = new ArrayList<>();
        List<String> antonymsList = new ArrayList<>();

        for(int i=0; i<apiResponse.getMeanings().size(); i++){
            synonymsList.addAll(apiResponse.getMeanings().get(i).getSynonyms());
            antonymsList.addAll(apiResponse.getMeanings().get(i).getAntonyms());
        }

        if(synonymsList.size() == 0)
            synonymsList.add("No Synonyms available!!!");
        if(antonymsList.size() == 0)
            antonymsList.add("No Antonyms available!!!");

        rcSynonyms.setHasFixedSize(true);
        rcSynonyms.setItemViewCacheSize(12);
        rcSynonyms.setLayoutManager(new GridLayoutManager(this, 3));

        synonymAdapter = new SynonymAdapter(this,synonymsList);
        rcSynonyms.setAdapter(synonymAdapter);

        rcAntonyms.setHasFixedSize(true);
        rcAntonyms.setItemViewCacheSize(12);
        rcAntonyms.setLayoutManager(new GridLayoutManager(this,3));
        antonymAdapter = new AntonymAdapter(this,antonymsList);
        rcAntonyms.setAdapter(antonymAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void setExample(APIResponse apiResponse) {
        List<Definitions> definitionsList = new ArrayList<>();

        for(int i=0; i<apiResponse.getMeanings().size(); i++)
            definitionsList.addAll(apiResponse.getMeanings().get(i).getDefinitions());

        if(definitionsList.size() == 0){
            example1.setText("No examples available!!!");
        }
        else{
            int i=0;
            boolean foundExample = false;
            StringBuilder sb = new StringBuilder();

            for(i=0; i<definitionsList.size(); i++){
                if(definitionsList.get(i).getExample() != null && !definitionsList.get(i).getExample().isEmpty()
                        && !foundExample){

                        sb.append(definitionsList.get(i).getExample());
                        i++;
                        foundExample = true;
                        continue;
                }
                if(definitionsList.get(i).getExample() != null && !definitionsList.get(i).getExample().isEmpty()){
                    sb.append("\n");
                    sb.append(definitionsList.get(i).getExample());
                   foundExample = true;
                   break;
                }
            }
            if(!foundExample)
                example1.setText("No examples available!!!");
            else
                example1.setText(sb.toString());
        }
    }

    private void init(){
        searchView = findViewById(R.id.searchView);
        word = findViewById(R.id.word);
        phonetic = findViewById(R.id.phonetic);
        definition = findViewById(R.id.definition);
        example1 = findViewById(R.id.example1);
        rcSynonyms = findViewById(R.id.rcSynonyms);
        rcAntonyms = findViewById(R.id.rcAntonnyms);
        progressDialog = new ProgressDialog(this);
        audioButton = findViewById(R.id.audioButton);
    }
}