package id.agristreet.agristreetapp.data.remote;

import android.content.Context;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rx.Emitter;
import rx.Observable;

public class ImageUploader {
    private static final String CLOUDINARY_PATH = "http://res.cloudinary.com/dde2jdlxd/image/upload";
    private static ImageUploader instance;

    private Cloudinary cloudinary;

    public static ImageUploader getInstance(Context context) {
        if (instance == null) {
            instance = new ImageUploader(context);
        }
        return instance;
    }

    private ImageUploader(Context context) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dde2jdlxd");
        config.put("api_key", "332678248912728");
        config.put("api_secret", "l1f0Hjo-p9X3IotUDRV9nrtyRxU");
        cloudinary = new Cloudinary(config);
    }

    public Observable<String> upload(File imageFile) {
        return Observable.create(subscriber -> {
            try {
                Map data = cloudinary.uploader().upload(imageFile, ObjectUtils.emptyMap());
                String url = data.get("url").toString();
                url = url.substring(url.lastIndexOf('/'));
                url = CLOUDINARY_PATH + url;
                subscriber.onNext(url);
                subscriber.onCompleted();
            } catch (IOException e) {
                subscriber.onError(e);
                subscriber.onCompleted();
            }
        }, Emitter.BackpressureMode.BUFFER);
    }
}
