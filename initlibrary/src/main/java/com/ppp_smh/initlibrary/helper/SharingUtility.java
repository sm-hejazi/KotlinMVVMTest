package com.ppp_smh.initlibrary.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.Browser;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.widget.ImageView;

import com.ppp_smh.initlibrary.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



class SharingUtility {
    private final Context context;
    private final String watermark;

    public SharingUtility(Context context) {
        this.context = context;
        watermark = "\n" + context.getString(R.string.share_watermark);
    }

    public void createShareImageIntent(ImageView imageView, String caption) {
        String type = "image/*";
        caption += "\n" + watermark;

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setPackage("org.telegram.messenger");

        // Set the MIME type
        share.setType(type);

        // Create the URI from the ImageView
        Uri bmpUri = getLocalBitmapUri(imageView);


        // Add the URI and the caption to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, bmpUri);
        share.putExtra(Intent.EXTRA_TEXT, caption);

        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        share.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Broadcast the Intent.
        context.startActivity(Intent.createChooser(share, null));
    }

    private void createShareImageIntent(String mediaPath, String caption) {
        String type = "image/*";
        caption += "\n" + watermark;

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);
        Log.i("RecipeActivity","createShareImageIntent mediaPath: " + mediaPath);
        // Create the URI from the media
        File media = new File(mediaPath);
        Uri uri  = Uri.fromFile(media);

        // Add the URI and the caption to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT, caption);

        // Broadcast the Intent.
        context.startActivity(Intent.createChooser(share, null));
    }

    public void createShareImageIntent(Bitmap bitmap, String caption) {
        saveBitmapInCache(bitmap);
        String type = "image/*";
        caption += "\n" + watermark;

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);
        // Set the MIME type
        share.setType(type);
        // Create the URI from the media
        File imagePath = new File(context.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri bmpUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileprovider", newFile);
//        String pathofBmp = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,context.getString(R.string.report_transaction), caption);
//        Uri bmpUri = Uri.parse(pathofBmp);

        // Add the URI and the caption to the Intent.
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.putExtra(Intent.EXTRA_STREAM, bmpUri);
        share.putExtra(Intent.EXTRA_TEXT, caption);

        // Broadcast the Intent.
        context.startActivity(Intent.createChooser(share, null));
    }

    public void createShareImageIntent(ArrayList<String> mediaPaths, String caption) {
        if (mediaPaths.size() == 1) {
            createShareImageIntent(mediaPaths.get(0), caption);
            return;
        }

        String type = "image/*";
        caption += "\n" + watermark;

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND_MULTIPLE);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        ArrayList<Uri> uris = new ArrayList<>();
        for (String mediaPath : mediaPaths) {
            File media = new File(mediaPath);
            Uri uri = Uri.fromFile(media);
            uris.add(uri);
        }

        // Add the URI and the caption to the Intent.
        share.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        share.putExtra(Intent.EXTRA_TEXT, caption);

        // Broadcast the Intent.
        context.startActivity(Intent.createChooser(share, null));
    }

    public void createShareTextIntent(String text) {
        String type = "text/plain";
        text += "\n" + watermark;

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        share.putExtra(Intent.EXTRA_TEXT, text);

        // Broadcast the Intent.
        context.startActivity(Intent.createChooser(share, null));
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    private Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmpUri;
    }

    private void saveBitmapInCache(Bitmap bitmap) {
        try {
            File cachePath = new File(context.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotToURL(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
        context.startActivity(intent);
    }

    /*public void shareImage(Context context) {

        Uri contentUri = FileProvider.getUriForFile(context, "org.behinepardaz.hekmatmbank.fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            context.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }*/
}
