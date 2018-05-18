package com.example.uis.facebook_emotions.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.uis.facebook_emotions.Model.TweetToAnalyze;
import com.example.uis.facebook_emotions.R;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;

import java.util.LinkedList;

public class DetectEmotionService extends AsyncTask<LinkedList<TweetToAnalyze>, Void, LinkedList<Face[]>> {

    private FaceServiceRestClient faceServiceRestClient;
    private LinkedList<Face[]> results;

    public DetectEmotionService(Context context){
        this.faceServiceRestClient = new FaceServiceRestClient(context.getString(R.string.microsoft_face_url), context.getString(R.string.microsoft_face_api_key));
        results = new LinkedList<>();
    }
    @Override
    protected LinkedList<Face[]> doInBackground(LinkedList<TweetToAnalyze>... tweets) {

        FaceServiceClient.FaceAttributeType[] emotions = new FaceServiceClient.FaceAttributeType[] {
                FaceServiceClient.FaceAttributeType.Emotion,
        };

        LinkedList<TweetToAnalyze> tweetToAnalyzes = tweets[0];
        for(TweetToAnalyze tweet: tweetToAnalyzes){
            try {
                if(tweet.getMediaEntity() != null){
                    results.add(this.faceServiceRestClient.detect(tweet.getMediaEntity(), false, false, emotions));
                }
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
                return null;
            }
        }
        return results;
    }

    protected void onPostExecute(LinkedList<Face[]> results) {
        Log.d("FaceRegonition", results.toString());
    }
}

