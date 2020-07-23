package com.kkensu.www.imagepager

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.widget.LinearLayout
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.kkensu.www.imagepager.adapter.ImageListAdapter
import com.kkensu.www.imagepager.adapter.ImagePageAdapter
import com.kkensu.www.imagepager.event.PageEvent
import com.kkensu.www.imagepager.model.ImageData
import kotlinx.android.synthetic.main.activity_viewpager.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.math.roundToInt


class ImagePagerActivity : AppCompatActivity() {
    companion object {
        const val ARG_IMAGE_LIST = "ARG_IMAGE_LIST"
        const val ARG_THUMBNAIL_LIST = "ARG_THUMBNAIL_LIST"
        const val ARG_CLOSE_TYPE = "ARG_CLOSE_TYPE"
        const val ARG_TITLE = "ARG_TITLE"
        const val ARG_POSITION = "ARG_POSITION"
        const val ARG_IS_SHOW_MORE = "ARG_IS_SHOW_MORE"
        const val ARG_IS_SHOW_POSITION = "ARG_IS_SHOW_POSITION"
        const val ARG_IS_SHOW_BOTTOM_VIEW = "ARG_IS_SHOW_BOTTOM_VIEW"
    }

    private var activity: AppCompatActivity? = null

    private var position = 0
    private var closeType: CloseType? = null
    private var toggleButtons: Array<ToggleButton?>? = null

    private var title: String? = null
    private var isShowPosition = false
    private var isShowBottomView = false

    private var imageListAdapter: ImageListAdapter? = null

    private var imageList: MutableList<ImageData>? = null
    private var thumbnailList: MutableList<ImageData>? = null
    private var viewPager: ViewPager2? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        EventBus.getDefault().register(this)
        setContentView(R.layout.activity_viewpager)

        initData()
        initResources()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    private fun initData() {
        imageList = intent.getSerializableExtra(ARG_IMAGE_LIST) as ArrayList<ImageData>
        try {
            thumbnailList = intent.getSerializableExtra(ARG_THUMBNAIL_LIST) as ArrayList<ImageData>
        } catch (e: Exception) {
            if (thumbnailList == null || thumbnailList?.size == 0) {
                thumbnailList = imageList
            }
        }

        position = intent.getIntExtra(ARG_POSITION, 0)
        title = intent.getStringExtra(ARG_TITLE)
        isShowPosition = intent.getBooleanExtra(ARG_IS_SHOW_POSITION, false)
        isShowBottomView = intent.getBooleanExtra(ARG_IS_SHOW_BOTTOM_VIEW, false)
        closeType = CloseType.fromValue(intent.getIntExtra(ARG_CLOSE_TYPE, 0))
    }

    private fun initResources() {
        initClose()

        btnClose.setOnClickListener {
            finish()
        }

        setToggleButton(imageList!!.size)

        txtTitle.text = title
        setTextPageNo(1)
        txtPosition.visibility = if (isShowPosition) View.VISIBLE else View.GONE

        viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager?.adapter = ImagePageAdapter(imageList!!)
        viewPager?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager?.currentItem = position
        viewPager?.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (recyclerView.layoutManager != null) {
                    Handler().postDelayed({
                        recyclerView.layoutManager!!.smoothScrollToPosition(recyclerView, RecyclerView.State(), (position * 2))
                    }, 200)
                }

                setTextPageNo(position + 1)

                allUnSelect()
                imageList?.get(position)?.isSelected = true
                imageListAdapter?.setItem(imageList)
            }
        })

        if (isShowBottomView) {
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            imageListAdapter = ImageListAdapter(this)
            imageListAdapter?.setItem(imageList)
            recyclerView.adapter = imageListAdapter
            recyclerView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
        }

        recyclerView.visibility = if (isShowBottomView) View.VISIBLE else View.GONE
    }

    private fun allUnSelect() {
        for (image in this.imageList!!) {
            image.isSelected = false
        }
    }

    private fun setTextPageNo(curNo: Int) {
        txtPosition.text = String.format("%d / %d", curNo, imageList?.size)
    }

    private fun initClose() {
        when (closeType) {
            CloseType.TYPE_BACK -> btnClose.setImageResource(R.drawable.ic_back_black)
            CloseType.TYPE_CLOSE -> btnClose.setImageResource(R.drawable.ic_nav_close_black)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun setViewPager(event: PageEvent) {
        for (i in imageList?.indices!!) {
            val imageData: ImageData = imageList?.get(i)!!
            if (imageData.image?.equals(event.imageData.image)!!) {
                viewPager!!.currentItem = i
                break
            }
        }
    }
}