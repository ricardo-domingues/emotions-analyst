/*
package com.example.uis.facebook_emotions;

import android.os.AsyncTask;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;

public class DetectEmotion extends AsyncTask<String, String, Face[]> {
    @Override
    protected Face[] doInBackground(String... strings) {
        FaceServiceClient faceClient = new FaceServiceRestClient("https://westeurope.api.cognitive.microsoft.com/face/v1.0", "0b84fc5a9ee34c7ca4dc45f6a079223a");
        try {
            publishProgress("Detecting...");

            // Start detection.
            return faceClient.detect(
                    strings[0],  */
/* Input stream of image to detect *//*

                    false,       */
/* Whether to return face ID *//*

                    false,       */
/* Whether to return face landmarks *//*

                    new FaceServiceClient.FaceAttributeType[] {
                            FaceServiceClient.FaceAttributeType.Emotion,

                    });
        } catch (Exception e) {
            publishProgress(e.getMessage());
            return null;
        }
    }
}

*/
