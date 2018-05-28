package com.example.uis.facebook_emotions.AsyncTasks;

import android.os.AsyncTask;

import com.example.uis.facebook_emotions.Model.AsyncTaskListener;
import com.example.uis.facebook_emotions.Model.TweetToAnalyze;
import com.example.uis.facebook_emotions.Model.User;
import com.example.uis.facebook_emotions.R;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Tone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneCategory;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EmotionAnalysisTask extends AsyncTask<Void, Void, Void> {

    private static final String TONE_MAP_KEY = "TONE";
    private static final String MEDIA_MAP_KEY = "MEDIA";
    private AsyncTaskListener caller;
    private HashMap<String, Integer> counters;
    private int tweetCount;

    public EmotionAnalysisTask(AsyncTaskListener caller) {
       this.caller = caller;

        counters = new HashMap<>();
        counters.put(TONE_MAP_KEY, 0);
        counters.put(MEDIA_MAP_KEY, 0);
        tweetCount = User.INSTANCE.getTweets().size();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        ToneAnalyzer toneAnalyzer = initToneAnalyzer();
        ToneOptions toneAnalyzerOptions = initToneOptions();
        FaceServiceRestClient faceServiceClient = initFaceServiceClient();
        FaceServiceClient.FaceAttributeType[] faceServiceOptions = initFaceServiceOptions();

        for (final TweetToAnalyze tweet : User.INSTANCE.getTweets()) {

            toneAnalyzer.getTone(tweet.getText(), toneAnalyzerOptions)
                    .enqueue(new ServiceCallback<ToneAnalysis>() {
                        @Override
                        public void onResponse(ToneAnalysis response) {
                            // More code here
                            List<ToneScore> scores = new LinkedList<>();

                            for (ToneCategory toneCategory : response.getDocumentTone().getTones()) {
                                scores.addAll(toneCategory.getTones());
                            }

                            tweet.setEmotionsInTweetText(scores);
                            incrementCounter(TONE_MAP_KEY);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            caller.onError(e);
                        }
                    });

            if(tweet.getMediaEntity() != null){
                try {
                    Face[] faces = faceServiceClient.detect(tweet.getMediaEntity(), false, false, faceServiceOptions);

                    // We are considering only one face, but there can be more in each image
                    tweet.setEmotionsInTweetMedia(faces[0].faceAttributes.emotion);
                    incrementCounter(MEDIA_MAP_KEY);

                }catch (Exception e){
                    caller.onError(e);
                }

            }else{
                incrementCounter(MEDIA_MAP_KEY);
            }
        }

        return null;
    }

    private FaceServiceClient.FaceAttributeType[] initFaceServiceOptions() {
        return new FaceServiceClient.FaceAttributeType[] {
                FaceServiceClient.FaceAttributeType.Emotion,
        };
    }

    private void incrementCounter(String key){

        counters.put(key, counters.get(key) + 1);
        if(counters.get(TONE_MAP_KEY) == tweetCount && counters.get(MEDIA_MAP_KEY) == tweetCount){
            User.INSTANCE.calculateEmotionAnalysisResult();
            caller.onSuccess();
        }
    }

    private ToneAnalyzer initToneAnalyzer(){

        ToneAnalyzer toneAnalyzer = new ToneAnalyzer("2017-07-01");
        toneAnalyzer.setUsernameAndPassword(caller.getContext().getString(R.string.tone_analyzer_username),
                caller.getContext().getString(R.string.tone_analyzer_password));
        return toneAnalyzer;
    }

    private ToneOptions initToneOptions()
    {
        return new ToneOptions.Builder()
            .addTone(Tone.EMOTION)
            .addTone(Tone.LANGUAGE)
            .html(false).build();
    }

    private FaceServiceRestClient initFaceServiceClient() {

        return new FaceServiceRestClient(caller.getContext().getString(R.string.microsoft_face_url),
                caller.getContext().getString(R.string.microsoft_face_api_key));
    }

}
