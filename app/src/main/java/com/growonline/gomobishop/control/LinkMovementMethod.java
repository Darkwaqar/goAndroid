package com.growonline.gomobishop.control;

/**
 * Created by asifrizvi on 10/12/2019.
 */

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.TextView;

import com.growonline.gomobishop.BrowserActivity;

public class LinkMovementMethod extends android.text.method.LinkMovementMethod {

    // The context we pass to the method
    private static Context movementContext;
    // A new LinkMovementMethod
    private static LinkMovementMethod linkMovementMethod = new LinkMovementMethod();

    public static MovementMethod getInstance(Context c) {
        // Set the context
        movementContext = c;
        // Return this movement method
        return linkMovementMethod;
    }

    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        // Get the event action
        int action = event.getAction();

        // If action has finished
        if (action == MotionEvent.ACTION_UP) {
            // Locate the area that was pressed
            int x = (int) event.getX();
            int y = (int) event.getY();
            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();
            x += widget.getScrollX();
            y += widget.getScrollY();

            // Locate the URL text
            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            // Find the URL that was pressed
            URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
            // If we've found a URL
            if (link.length != 0) {
                // Find the URL
                String url = link[0].getURL();
                // If it's a valid URL
                if (url.contains("https") | url.contains("tel") | url.contains("mailto") | url.contains("http") | url.contains("https") | url.contains("www")) {
                    // Open it in an instance of InlineBrowser
                    movementContext.startActivity(new Intent(movementContext, BrowserActivity.class).putExtra("url", url));
                }
                // If we're here, something's wrong
                return true;
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }
}
