package com.kkensu.www.imagepager

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.LinearLayout
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.kkensu.www.imagepager.adapter.ImagePageAdapter
import com.kkensu.www.imagepager.model.ImageData
import kotlinx.android.synthetic.main.activity_viewpager.*
import kotlin.math.roundToInt

class ImagePagerActivity : AppCompatActivity() {
    companion object {
        const val ARG_POSITION = "ARG_POSITION"
        const val ARG_IMAGE_LIST = "ARG_IMAGE_LIST"
        const val ARG_CLOSE_TYPE = "ARG_CLOSE_TYPE"
    }

    var activity: AppCompatActivity? = null

    private var position = 0
    private var closeType: CloseType? = null
    private var toggleButtons: Array<ToggleButton?>? = null

    private var imageList: MutableList<ImageData>? = null
    private var viewPager: ViewPager2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        setContentView(R.layout.activity_viewpager)

        initData()
        initResources()
    }

    private fun initData() {
        imageList = intent.getSerializableExtra(ARG_IMAGE_LIST) as ArrayList<ImageData>
        position = intent.getIntExtra(ARG_POSITION, 0)
        closeType = CloseType.fromValue(intent.getIntExtra(ARG_CLOSE_TYPE, 0))
    }

    private fun initResources() {
        initClose()

        btnClose.setOnClickListener {
            finish()
        }

        setToggleButton(imageList!!.size)

        viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager?.adapter = ImagePageAdapter(imageList!!)
        viewPager?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager?.currentItem = position
        viewPager?.registerOnPageChangeCallback(object : OnPageChangeCallback() {})
    }

    private fun initClose() {
        when (closeType) {
            CloseType.TYPE_BACK -> btnClose.setImageResource(R.drawable.common_btn_left_white)
            CloseType.TYPE_CLOSE -> btnClose.setImageResource(R.drawable.common_btn_close_white)
        }
    }

    // view pager ToggleButton 을 동적으로 생성해준다.
    private fun setToggleButton(viewPagerNum: Int) {
        val viewGroup: LinearLayout = findViewById<View>(R.id.layoutCircle) as LinearLayout

        // LayoutParams 객체 생성 및 값 설정
        val dm: DisplayMetrics = resources.displayMetrics
        val size: Int = (8 * dm.density).roundToInt() // size = 10dp
        val marginSize: Int = (2 * dm.density).roundToInt() // marginSize = 2dp
        val btnLayoutLp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(size, size) // layout_width = "10dp", layout_height = "10dp"
        btnLayoutLp.setMargins(marginSize, marginSize, marginSize, marginSize) // layout_margin = "2dp"
        toggleButtons = arrayOfNulls<ToggleButton>(viewPagerNum)

        for (i in 0 until viewPagerNum) {
            toggleButtons!![i] = ToggleButton(this)
            toggleButtons!![i]?.setBackgroundResource(R.drawable.common_btn_circle)
            toggleButtons!![i]?.isEnabled = false
            // Toggle 버튼 텍스트 삭제
            toggleButtons!![i]?.textOff = ""
            toggleButtons!![i]?.textOn = ""
            toggleButtons!![i]?.text = ""
            viewGroup.addView(toggleButtons!![i], btnLayoutLp)
        }
        if (viewPagerNum > 0) {
            toggleButtons!![0]?.isChecked = true
        }
    }

    enum class CloseType(var value: Int) {
        TYPE_BACK(1),
        TYPE_CLOSE(2),
        ;

        companion object {
            fun fromValue(value: Int): CloseType {
                for (state in values()) if (state.value == value) {
                    return state
                }
                return TYPE_BACK
            }
        }

    }
}