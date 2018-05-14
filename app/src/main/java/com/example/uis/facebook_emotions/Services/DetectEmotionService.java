package com.example.uis.facebook_emotions.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.uis.facebook_emotions.R;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;

public class DetectEmotionService extends AsyncTask<String, String, Face[]> {

    private FaceServiceRestClient faceServiceRestClient;

    public void DetectEmotionService(Context context){
        this.faceServiceRestClient = new FaceServiceRestClient(context.getString(R.string.microsoft_face_url), context.getString(R.string.microsoft_face_api_key));
    }
    @Override
    protected Face[] doInBackground(String... strings) {
        try {
            return this.faceServiceRestClient.detect(
                    strings[0], false, false,
                    new FaceServiceClient.FaceAttributeType[] {
                            FaceServiceClient.FaceAttributeType.Emotion,
                    });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            return null;
        }
    }

    protected void onPostExecute(Face[] face) {
        Log.d("FaceRegonition", face.toString());
    }
}

