package com.bilgetech.widgets.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageHelper {
    private static final String SAVED_IMAGE_PREFIX = "img";

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public static Bitmap rotateAndResizeBitmapIfNeeded(File imageFile, int reqWidth, int reqHeight) throws IOException {
        ExifInterface exifInterface = new ExifInterface(imageFile.getAbsolutePath());
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap bitmap = decodeSampledBitmapFromFile(imageFile, reqWidth, reqHeight);
        Bitmap rotatedBitmap = null;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateBitmap(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateBitmap(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateBitmap(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }

        return rotatedBitmap;
    }

    /**
     * Saves the given Bitmap to cache directory of the given context. Returns the path of saved Bitmap.
     *
     * @param context Context to be used to reach cache directory. See {@link Context#getCacheDir()}
     * @param bitmap  Bitmap to be saved.
     * @return String path of saved Bitmap.
     */
    public static File saveBitmapToCacheDir(Context context, Bitmap bitmap) throws FileOperationException{
        String fileName = SAVED_IMAGE_PREFIX + System.nanoTime();
        return saveBitmapTo(bitmap, context.getCacheDir(), fileName);
    }

    /**
     * Saves the given Bitmap to given directory with given name.
     *
     * @param bitmap    Bitmap to be saved.
     * @param directory Save directory.
     * @param fileName  File name.
     * @return String path of saved Bitmap.
     */
    public static File saveBitmapTo(Bitmap bitmap, File directory, String fileName) throws FileOperationException {
        File file = new File(directory, fileName + ".jpg");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } catch (Exception highlyUnlikelyException) {
            highlyUnlikelyException.printStackTrace();
            throw new FileOperationException("Cannot open file with path: " + file.getAbsolutePath());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException mostlyUnlikelyException) {
                mostlyUnlikelyException.printStackTrace();
                throw new FileOperationException("Cannot close the output stream. File path: " + file.getAbsolutePath());
            }
        }
        return file;
    }

    public static Bitmap decodeSampledBitmapFromFile(File file, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static class FileOperationException extends Exception {
        public FileOperationException(String message) {
            super(message);
        }
    }
}

