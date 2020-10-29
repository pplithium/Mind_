package ck.no.mind.activities.window;

import android.app.Activity;
import android.os.Build;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import ck.no.mind.activities.SecondAssesmentActivity;

public class MindWindowCallback implements Window.Callback {
    SecondAssesmentActivity activity = null;
    Window.Callback oldCallback;
    public MindWindowCallback(SecondAssesmentActivity activity, Window.Callback oldCallback) {
        this.activity = activity;
        this.oldCallback = oldCallback;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return oldCallback.dispatchKeyEvent(keyEvent);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return oldCallback.dispatchKeyShortcutEvent(keyEvent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return oldCallback.dispatchTouchEvent(motionEvent);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        return oldCallback.dispatchTrackballEvent(motionEvent);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return oldCallback.dispatchGenericMotionEvent(motionEvent);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return oldCallback.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Nullable
    @Override
    public View onCreatePanelView(int i) {
        return oldCallback.onCreatePanelView(i);
    }

    @Override
    public boolean onCreatePanelMenu(int i, @NonNull Menu menu) {
        return oldCallback.onCreatePanelMenu(i, menu);
    }

    @Override
    public boolean onPreparePanel(int i, @Nullable View view, @NonNull Menu menu) {
        return oldCallback.onPreparePanel(i, view, menu);
    }

    @Override
    public boolean onMenuOpened(int i, @NonNull Menu menu) {
        return oldCallback.onMenuOpened(i, menu);
    }

    @Override
    public boolean onMenuItemSelected(int i, @NonNull MenuItem menuItem) {
        return oldCallback.onMenuItemSelected(i, menuItem);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        oldCallback.onWindowAttributesChanged(layoutParams);
    }

    @Override
    public void onContentChanged() {
        oldCallback.onContentChanged();
    }

    @Override
    public void onWindowFocusChanged(boolean b) {
        activity.triggerDataRefresh();
        oldCallback.onWindowFocusChanged(b);
    }

    @Override
    public void onAttachedToWindow() {
        oldCallback.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        oldCallback.onDetachedFromWindow();
    }

    @Override
    public void onPanelClosed(int i, @NonNull Menu menu) {
        oldCallback.onPanelClosed(i, menu);
    }

    @Override
    public boolean onSearchRequested() {
        return oldCallback.onSearchRequested();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return oldCallback.onSearchRequested(searchEvent);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return oldCallback.onWindowStartingActionMode(callback);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        return oldCallback.onWindowStartingActionMode(callback, i);
    }

    @Override
    public void onActionModeStarted(ActionMode actionMode) {
        oldCallback.onActionModeStarted(actionMode);
    }

    @Override
    public void onActionModeFinished(ActionMode actionMode) {
        oldCallback.onActionModeFinished(actionMode);
    }
}
