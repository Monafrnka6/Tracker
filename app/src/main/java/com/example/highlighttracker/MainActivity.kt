package com.example.highlighttracker

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import android.widget.ScrollView

class MainActivity : AppCompatActivity() {
    private lateinit var scrollView: ScrollView
    private lateinit var textView: TrackingTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scrollView = findViewById(R.id.scrollView)
        textView = findViewById(R.id.documentTextView)

        // Sample long document text. Replace this with actual content or load from file
        val document = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse
            lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum
            ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi.
            Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam
            nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in,
            pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor.
            Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu
            enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in
            faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris
            sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.
            
            Ut velit mauris, egestas sed, gravida nec, ornare ut, mi. Aenean ut orci vel massa
            suscipit pulvinar. Nulla sollicitudin. Fusce varius, ligula non tempus aliquam,
            nunc turpis ullamcorper nibh, in tempus sapien eros vitae ligula. Pellentesque rhoncus
            nunc et augue. Integer id felis. Curabitur aliquet pellentesque diam. Integer quis
            metus vitae elit lobortis egestas. Lorem ipsum dolor sit amet, consectetuer adipiscing
            elit. Morbi vel erat non mauris convallis vehicula. Nulla et sapien. Integer tortor
            tellus, aliquam faucibus, convallis id, congue eu, quam. Mauris ullamcorper felis
            vitae erat. Proin feugiat, augue non elementum posuere, metus purus iaculis lectus,
            et tristique ligula justo vitae magna.
        """.trimIndent()

        textView.text = document
        // Allow the TextView to be selectable so users can select text
        textView.setTextIsSelectable(true)

        // Listen for selection changes to highlight and scroll
        textView.onSelectionChange = { start, end ->
            highlightRange(start, end)
            scrollToHighlight(start)
        }
    }

    /**
     * Highlights the selected text by applying a BackgroundColorSpan. Clears any existing
     * highlight spans before applying a new one.
     */
    private fun highlightRange(start: Int, end: Int) {
        val textContent = textView.text
        if (textContent !is CharSequence) return
        val spannable = SpannableString(textContent)
        // Remove existing highlight spans
        val existingSpans = spannable.getSpans(0, spannable.length, BackgroundColorSpan::class.java)
        for (span in existingSpans) {
            spannable.removeSpan(span)
        }
        // Apply new highlight if selection is valid
        if (start < end) {
            spannable.setSpan(
                BackgroundColorSpan(Color.YELLOW),
                start,
                end,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        // Update the TextView with the new spannable content
        textView.text = spannable
    }

    /**
     * Scrolls the parent ScrollView to bring the highlighted text into view.
     */
    private fun scrollToHighlight(start: Int) {
        val layout = textView.layout ?: return
        val line = layout.getLineForOffset(start)
        val y = layout.getLineTop(line)
        scrollView.post {
            scrollView.smoothScrollTo(0, y)
        }
    }
}
