package com.rye.catcher.base.photoview;

import android.widget.ImageView;

/**
 * BaseCallback when the user tapped outside of the photo
 */
public interface OnOutsidePhotoTapListener {

    /**
     * The outside of the photo has been tapped
     */
    void onOutsidePhotoTap(ImageView imageView);
}
