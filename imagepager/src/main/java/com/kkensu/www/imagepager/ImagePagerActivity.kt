package com.kkensu.www.imagepager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.PopupMenu
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.kkensu.www.imagepager.adapter.ImageListAdapter
import com.kkensu.www.imagepager.adapter.ImagePageAdapter
import com.kkensu.www.imagepager.interfaces.OnSelectedImageCallback
import com.kkensu.www.imagepager.model.ImageData
import com.kkensu.www.imagepager.util.DownloadUtil
import kotlinx.android.synthetic.main.activity_viewpager.*
import java.util.*


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

    private var position = 0
    private var closeType: CloseType? = null
    private var toggleButtons: Array<ToggleButton?>? = null

    private var title: String? = null
    private var isShowPosition = false
    private var isShowBottomView = false

    private var imageListAdapter: ImageListAdapter? = null

    private var imageList: MutableList<ImageData>? = null
    private var thumbnailList: MutableList<ImageData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)

        initData()
        initResources()
    }

    private fun initData() {
        imageList = intent.getSerializableExtra(ARG_IMAGE_LIST) as ArrayList<ImageData>
        try {
            thumbnailList = intent.getSerializableExtra(ARG_THUMBNAIL_LIST) as ArrayList<ImageData>

            if (thumbnailList?.size == 0) {
                thumbnailList = imageList
            }
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
        initTitle()
        initPageNumber()
        initViewPager()
        initBottomView()
    }

    private fun initTitle() {
        txtTitle.text = title
    }

    private fun initPageNumber() {
        txtPosition.visibility = if (isShowPosition) View.VISIBLE else View.GONE
        updatePageNumber(position)
    }

    private fun initBottomView() {
        if (isShowBottomView) {
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            imageListAdapter = ImageListAdapter(this)
            imageListAdapter?.setItem(thumbnailList)
            imageListAdapter?.setOnSelectedItemCallback {
                viewPager?.currentItem = it.idx!! - 1
            }
            recyclerView.adapter = imageListAdapter
            recyclerView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
        }
    }

    private fun initViewPager() {
        viewPager?.adapter =
                ImagePageAdapter(
                        this,
                        imageList!!,
                        OnSelectedImageCallback {
                            showHideMenuLayout()
                        },
                        View.OnLongClickListener {
                            popupMenu()
                            return@OnLongClickListener false
                        }
                )
        viewPager?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager?.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (recyclerView.layoutManager != null) {
                    Handler().postDelayed({
                        recyclerView.layoutManager!!.smoothScrollToPosition(recyclerView, RecyclerView.State(), (position * 2))
                    }, 200)
                }

                updatePageNumber(position)

                allUnSelect()
                thumbnailList?.get(position)?.isSelected = true
                imageListAdapter?.setItem(thumbnailList)
            }
        })
        viewPager?.currentItem = position
    }

    private fun allUnSelect() {
        for (image in this.thumbnailList!!) {
            image.isSelected = false
        }
    }

    fun showHideMenuLayout() {
        if (layout_top.visibility == View.GONE) {
            val showAnim: Animation = AlphaAnimation(0f, 1f)
            showAnim.duration = 500
            layout_top.visibility = View.VISIBLE
            layout_top.animation = showAnim

            if (isShowBottomView) {
                recyclerView.visibility = View.VISIBLE
                recyclerView.animation = showAnim
            }
        } else {
            val hideAnim: Animation = AlphaAnimation(1f, 0f)
            hideAnim.duration = 500
            layout_top.visibility = View.GONE
            layout_top.animation = hideAnim

            if (isShowBottomView) {
                recyclerView.visibility = View.GONE
                recyclerView.animation = hideAnim
            }
        }
    }

    private fun popupMenu() {
        val popupMenu = PopupMenu(this, btnMore)
        menuInflater.inflate(R.menu.imageview_more_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.btn_image_save -> {
                    DownloadUtil.downloadData(this, imageList?.get(viewPager.currentItem)?.image as String?)
                }
            }
            false
        }
        popupMenu.show()
    }

    private fun updatePageNumber(curNo: Int) {
        txtPosition.text = String.format("%d / %d", (curNo + 1), imageList?.size)
    }

    private fun initClose() {
        when (closeType) {
            CloseType.TYPE_BACK -> btnClose.setImageResource(R.drawable.ic_back_black)
            CloseType.TYPE_CLOSE -> btnClose.setImageResource(R.drawable.ic_nav_close_black)
        }

        btnClose.setOnClickListener { finish() }
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