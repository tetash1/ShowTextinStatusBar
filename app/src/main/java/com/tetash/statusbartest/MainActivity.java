package com.tetash.statusbartest;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example usage of the StatusBarController class


        StatusBarController.showStatusBarText(getApplicationContext(), "0.00 kb/s");

    }
}

class StatusBarController {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "status_bar_channel";
    private static Drawable createTextIconDrawable(Context context, String text) {


        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(60);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // Split the text into two parts
        String[] parts = text.split(" ");
        String firstPart = parts[0];
        String secondPart = parts[1];

        // Calculate the width and height of the bitmap
        int firstPartWidth = (int) textPaint.measureText(firstPart);
        int secondPartWidth = (int) textPaint.measureText(secondPart);
        int width = Math.max(firstPartWidth, secondPartWidth) + 16; // Add some padding
        int height = (int) (96 * context.getResources().getDisplayMetrics().density); // Adjust size as needed

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        float yPos = (canvas.getHeight() / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f);

        // Draw the first part as bold
        TextPaint boldTextPaint = new TextPaint(textPaint);
        boldTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(firstPart, canvas.getWidth() / 2f, yPos, boldTextPaint);

        // Draw the second part
        canvas.drawText(secondPart, canvas.getWidth() / 2f, yPos + textPaint.getFontSpacing(), textPaint);



        return new BitmapDrawable(context.getResources(), bitmap);
    }
    private static IconCompat convertDrawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return IconCompat.createWithBitmap(((BitmapDrawable) drawable).getBitmap());
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return IconCompat.createWithBitmap(bitmap);
    }
    public static void showStatusBarText(Context context, String text) {

        // Create a notification manager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel (required for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Status Bar Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        // Create a custom notification icon using text
        Drawable iconDrawable = createTextIconDrawable(context, text);

        // Create a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(convertDrawableToBitmap(iconDrawable))
                .setContentTitle("Status Bar Text")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}




//same code for kotlin

/*
package com.tetash.statusbartest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example usage of the StatusBarController class
        StatusBarControllerr.showStatusBarText(applicationContext, "0.00 kb/s")
    }
}

object StatusBarControllerr {
    private const val NOTIFICATION_ID = 1
    private const val CHANNEL_ID = "status_bar_channel"

    private fun createTextIconDrawable(context: Context, text: String): Drawable {
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textPaint.textSize = 48f
        textPaint.color = Color.WHITE
        textPaint.textAlign = Paint.Align.CENTER

        // Split the text into two parts
        val parts = text.split(" ")
        val firstPart = parts[0]
        val secondPart = parts[1]

        // Calculate the width and height of the bitmap
        val firstPartWidth = textPaint.measureText(firstPart).toInt()
        val secondPartWidth = textPaint.measureText(secondPart).toInt()
        val width = maxOf(firstPartWidth, secondPartWidth) + 16 // Add some padding
        val height = (96 * context.resources.displayMetrics.density).toInt() // Adjust size as needed

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val yPos = canvas.height / 2f - (textPaint.descent() + textPaint.ascent()) / 2f

        // Draw the first part as bold
        val boldTextPaint = TextPaint(textPaint)
        boldTextPaint.typeface = Typeface.DEFAULT_BOLD
        canvas.drawText(firstPart, canvas.width / 2f, yPos, boldTextPaint)

        // Draw the second part
        canvas.drawText(secondPart, canvas.width / 2f, yPos + textPaint.fontSpacing, textPaint)

        return BitmapDrawable(context.resources, bitmap)
    }

    private fun convertDrawableToBitmap(drawable: Drawable): IconCompat {
        return if (drawable is BitmapDrawable) {
            IconCompat.createWithBitmap(drawable.bitmap)
        } else {
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)

            IconCompat.createWithBitmap(bitmap)
        }
    }

    fun showStatusBarText(context: Context, text: String) {
        // Create a notification manager
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel (required for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Status Bar Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Create a custom notification icon using text
        val iconDrawable = createTextIconDrawable(context, text)

        // Create a notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(convertDrawableToBitmap(iconDrawable))
            .setContentTitle("Status Bar Text")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}
*/
