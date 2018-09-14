package com.kkensu.www.imagepager.util;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.kkensu.www.imagepager.util.Util.getEncMD5;

/**
 * Created by johyunchol on 2018. 4. 9..
 */

public class DownloadUtil {
    public static long downloadData(Context context, String url) {
        long downloadReference;

        Uri uri = Uri.parse(url);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        String filename = null;
        try {
            filename = getEncMD5(url);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        filename += ".jpg";

        //Setting title of request
        request.setTitle(filename);

        //Setting description of request
        request.setDescription("Downloading...");

        request.setVisibleInDownloadsUi(true);
        request.allowScanningByMediaScanner();
        request.setMimeType(getMimeType(uri.getPath()));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM, filename);

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);

        return downloadReference;

    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }
}
