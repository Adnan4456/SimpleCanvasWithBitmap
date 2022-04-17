package com.example.simplecanvas

import android.graphics.Canvas
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Bitmap
import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {

    private lateinit var mCanvas: Canvas
    private var mPaint: Paint = Paint()

    private var mPaintText = Paint(Paint.UNDERLINE_TEXT_FLAG)
    private lateinit var mBitmap: Bitmap

    private lateinit var mImageView: ImageView

    private val mRect: Rect = Rect()
    private val mBounds: Rect = Rect()

    val OFFSET = 120
    var  mOffset = OFFSET

    var  MULTIPLIER = 100

    private var  mColorBackground:Int =0
    var mColorRectangle = 0
    var mColorAccent = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mColorBackground  = ResourcesCompat.getColor(resources , R.color.colorBackground , null)
        mColorRectangle = ResourcesCompat.getColor(resources , R.color.colorRectangle , null)
        mColorAccent = ResourcesCompat.getColor(resources , R.color.colorAccent , null)

        mPaint.setColor(mColorBackground)
        mPaintText.setColor(ResourcesCompat.getColor(resources,R.color.design_default_color_primary_dark ,null))

        mPaintText.setTextSize(70F)

        mImageView =  findViewById(R.id.myimageview)

        // You cannot create the Canvas in onCreate(),
        // because the views have not been
        // laid out, so their final size is not available.
        // When you create a custom view in a later lesson,
        // you will learn other ways of initializing your drawing surface.

    }

    fun drawSomething(view: View){

        val vWidth = view.width
        val vHeight = view.height
        val halfWidth = vWidth / 2
        val halfHeight = vHeight / 2

// Only do this first time view is clicked after it has been created.
        if (mOffset == OFFSET){

            // Only true once, so don't need separate flag.
            // Each pixel takes 4 bytes, with alpha channel.
            // Use RGB_565 if you don't need alpha and a huge color palette.

            mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888)

//            Associate the bitmap with the ImageView.
            mImageView.setImageBitmap(mBitmap)

//            Create a Canvas and associate it with mBitmap, so that drawing on the canvas draws on the bitmap.
            mCanvas =  Canvas(mBitmap)

//            Fill the entire canvas with the background color.
            mCanvas.drawColor(mColorBackground)

//            Draw the "Keep tapping" text onto the canvas. You need to supply a string,
        //            x and y positions, and a Paint object for styling.

            mCanvas.drawText("Keep tapping", 100F, 100F, mPaintText)
//            Increase the offset.
            mOffset += OFFSET

        }else
            if (mOffset < halfWidth && mOffset < halfHeight){

                // Change the color by subtracting an integer.
                mPaint.setColor(mColorRectangle - MULTIPLIER*mOffset)
                mRect.set(
                    mOffset, mOffset, vWidth - mOffset, vHeight - mOffset)

                mCanvas.drawRect(mRect, mPaint)
                // Increase the indent.
                mOffset += OFFSET

            }else{

                mPaint.color = mColorAccent
                mCanvas.drawCircle(
                    halfWidth.toFloat(),
                    halfHeight.toFloat(),
                    (halfWidth / 3).toFloat(),
                    mPaint
                )
                val text = "Done"
                // Get bounding box for text to calculate where to draw it.
                // Get bounding box for text to calculate where to draw it.
                mPaintText.getTextBounds(text, 0, text.length, mBounds)
                // Calculate x and y for text so it's centered.
                // Calculate x and y for text so it's centered.
                val x = halfWidth - mBounds.centerX()
                val y = halfHeight - mBounds.centerY()
                mCanvas.drawText(text, x.toFloat(), y.toFloat(), mPaintText)

            }

        // Invalidate the view, so that it gets redrawn.
        view.invalidate()
    }

}