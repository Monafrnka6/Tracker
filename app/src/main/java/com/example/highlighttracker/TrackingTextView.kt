package com.example.highlighttracker

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * A custom TextView that exposes a callback when the user changes the selection.
 * This allows the activity to react to selection changes by highlighting and
 * scrolling to the selected text.
 */
class TrackingTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    /**
     * A listener that is invoked whenever the text selection changes. It
     * provides the new start and end indices of the selection.
     */
    var onSelectionChange: ((start: Int, end: Int) -> Unit)? = null

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if (selStart >= 0 && selEnd >= selStart) {
            onSelectionChange?.invoke(selStart, selEnd)
        }
    }
}
