package com.kkensu.www.imagepager.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by johyunchol on 2018. 4. 13..
 */

public class ShareUtil {
    public static void shareImage(Context context, String subject,
                                  File imageFile, String titleString) {
        shareImageAndTextForResult(context, subject, null, imageFile,
                titleString, -1);
    }

    public static void shareText(Context context, String subject,
                                 String contents, String titleString) {
        shareImageAndTextForResult(context, subject, contents, null,
                titleString, -1);
    }

    public static void shareImageForResult(Context context, String subject,
                                           File imageFile, String titleString, int request_code) {
        shareImageAndTextForResult(context, subject, null, imageFile,
                titleString, request_code);
    }

    public static void shareTextForResult(Context context, String subject,
                                          String contents, String titleString, int request_code) {
        shareImageAndTextForResult(context, subject, contents, null,
                titleString, request_code);
    }

    public static void shareImageAndText(Context context, String subject,
                                         String contents, File imageFile, String titleString) {
        shareImageAndTextForResult(context, subject, contents, imageFile,
                titleString, -1);
    }

    public static void shareImageAndTextForResult(Context context,
                                                  String subject, String contents, File imageFile,
                                                  String titleString, int request_code) {
        Intent share = new Intent(Intent.ACTION_SEND);
        int sdk = android.os.Build.VERSION.SDK_INT;
        // Deprecated (android SDK version 11)
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        }
        // Recommended codes
        else {
            share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        }

        if (subject != null) {
            share.putExtra(Intent.EXTRA_SUBJECT, subject);
        }

        if (imageFile != null) {
            Uri imageUri = Uri.fromFile(imageFile);
            if (imageUri != null) {
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
            }
        } else if (contents != null) {
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, contents);
        }

        if (request_code != -1) {
            ((Activity) context).startActivityForResult(
                    Intent.createChooser(share, titleString), request_code);
        } else {
            context.startActivity(Intent.createChooser(share, titleString));
        }
    }
}
