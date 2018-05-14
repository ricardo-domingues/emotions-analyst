package com.example.uis.facebook_emotions.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.uis.facebook_emotions.Model.TweetToAnalyze;
import com.example.uis.facebook_emotions.R;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;

public class DetectEmotionService extends AsyncTask<TweetToAnalyze, Void, Face[]> {

    private FaceServiceRestClient faceServiceRestClient;

    public DetectEmotionService(Context context){
        this.faceServiceRestClient = new FaceServiceRestClient(context.getString(R.string.microsoft_face_url), context.getString(R.string.microsoft_face_api_key));
    }
    @Override
    protected Face[] doInBackground(TweetToAnalyze... tweets) {

        FaceServiceClient.FaceAttributeType[] emotions = new FaceServiceClient.FaceAttributeType[] {
                FaceServiceClient.FaceAttributeType.Emotion,
        };

        for(TweetToAnalyze tweet : tweets) {
            try {
                return this.faceServiceRestClient.detect(tweet.getMediaEntities().get(0).url
                        , false, false, emotions);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
                return null;
            }
        }
        return null;
    }

    protected void onPostExecute(Face[] face) {
        Log.d("FaceRegonition", face.toString());
    }
}

