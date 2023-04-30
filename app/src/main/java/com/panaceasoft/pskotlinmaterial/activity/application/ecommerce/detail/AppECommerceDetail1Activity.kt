package com.panaceasoft.pskotlinmaterial.activity.application.ecommerce.detail

import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.`object`.ShopItem
import com.panaceasoft.pskotlinmaterial.`object`.UserReview
import com.panaceasoft.pskotlinmaterial.adapter.application.ecommerce.detail.detail1.AppECommerceDetail1PagerAdapter
import com.panaceasoft.pskotlinmaterial.adapter.application.ecommerce.detail.detail1.AppECommerceDetail1ReviewAdapter
import com.panaceasoft.pskotlinmaterial.repository.ecommerce.ShopItemRepository
import com.panaceasoft.pskotlinmaterial.repository.ecommerce.UserReviewRepository
import com.panaceasoft.pskotlinmaterial.utils.Utils
import kotlinx.android.synthetic.main.app_ecommerce_detail_1_activity.*

class AppECommerceDetail1Activity : AppCompatActivity() {

    // data variables
    private lateinit var shopItem: ShopItem
    private lateinit var userReviewList: List<UserReview>
    private var dotsCount: Int = 0

    private var size1Status: Boolean = false
    private var size2Status: Boolean = false
    private var size3Status: Boolean = false
    private var size4Status: Boolean = false
    private var size5Status: Boolean = false

    private var color1Status: Boolean = true
    private var color2Status: Boolean = false
    private var color3Status: Boolean = false
    private var color4Status: Boolean = false
    private var color5Status: Boolean = false
    private var color6Status: Boolean = false
    private var color7Status: Boolean = false

    // ui variables
    private lateinit var appECommerceDetail1PagerAdapter: AppECommerceDetail1PagerAdapter
    private lateinit var adapter: AppECommerceDetail1ReviewAdapter
    private lateinit var dots: Array<ImageView?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_ecommerce_detail_1_activity)

        initData()

        initUI()

        initDataBindings()

        initActions()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == android.R.id.home -> finish()
            item.itemId == R.id.action_share -> Toast.makeText(this, "Clicked Share.", Toast.LENGTH_SHORT).show()
            item.itemId == R.id.action_basket -> Toast.makeText(this, "Clicked Basket.", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share_basket, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initData() {

        // get shopItem detail
        shopItem = ShopItemRepository.womenShopItem

        appECommerceDetail1PagerAdapter = AppECommerceDetail1PagerAdapter(this, shopItem)

        // get place list
        userReviewList = UserReviewRepository.userReviewList

    }

    private fun initUI() {

        initToolbar()

        val toolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this,android.R.color.transparent))

        setDefaultCircleImage(color1BgImageView, R.color.md_white_1000)
        setDefaultCircleImage(color2BgImageView, R.color.md_grey_400)
        setDefaultCircleImage(color3BgImageView, R.color.md_yellow_400)
        setDefaultCircleImage(color4BgImageView, R.color.md_green_500)
        setDefaultCircleImage(color5BgImageView, R.color.md_green_900)
        setDefaultCircleImage(color6BgImageView, R.color.md_blue_500)
        setDefaultCircleImage(color7BgImageView, R.color.md_black_1000)

        setDefaultCircleImage(size1BgImageView, R.color.md_grey_400)
        setDefaultCircleImage(size2BgImageView, R.color.md_grey_400)
        setDefaultCircleImage(size3BgImageView, R.color.md_grey_400)
        setDefaultCircleImage(size4BgImageView, R.color.md_grey_400)
        setDefaultCircleImage(size5BgImageView, R.color.md_grey_400)

        // Set Color Default
        color1ImageView.setImageResource(R.drawable.baseline_select_with_check_transparent_24)
        color1Status = true


        // Set Size Default
        setSelectUnSelectSizeFilter(size3BgImageView, R.color.colorPrimary, size3TextView, R.color.md_white_1000)
        size3Status = true


        // get list adapter
        adapter = AppECommerceDetail1ReviewAdapter(userReviewList)

        // get recycler view
//        recyclerView = findViewById(R.id.reviewRecyclerView)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        reviewRecyclerView.layoutManager = mLayoutManager
        reviewRecyclerView.itemAnimator = DefaultItemAnimator()
        reviewRecyclerView.isNestedScrollingEnabled = false
    }

    private fun setDefaultCircleImage(imageView: ImageView?, color: Int) {
        Utils.setCircleImageToImageView(applicationContext, imageView!!, R.drawable.white_background, color, 10, R.color.colorLine)
    }

    private fun setSelectUnSelectSizeFilter(imageView: ImageView, bgColor: Int, textView: TextView, color: Int) {
        imageView.setColorFilter(ContextCompat.getColor(this,bgColor), PorterDuff.Mode.SRC_IN)
        textView.setTextColor(ContextCompat.getColor(this,color))
    }

    private fun initDataBindings() {

        imageViewPager.adapter = appECommerceDetail1PagerAdapter
        setupSliderPagination()

        addressTextView.text = shopItem.shop.shopAddress
        phoneTextView.text = shopItem.shop.shopPhone
        websiteTextView.text = shopItem.shop.shopWebsite
        emailTextView.text = shopItem.shop.shopEmail

        descTextView.text = shopItem.description
        nameTextView.text = shopItem.name
        reviewCountTextView.text = shopItem.ratingCount
        itemRatingBar.rating = java.lang.Float.parseFloat(shopItem.totalRating)

        val priceStr = "$ " + shopItem.price
        val originalPriceStr = "$ " + shopItem.originalPrice
        priceTextView.text = priceStr
        originalPriceTextView.text = originalPriceStr
        originalPriceTextView.paintFlags = originalPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        // bind adapter to recycler
        reviewRecyclerView.adapter = adapter
    }

    private fun initActions() {
        imageViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {


                    setupSliderPagination()


                for (i in 0 until dotsCount) {
                    dots[i]?.setImageDrawable(ContextCompat.getDrawable(this@AppECommerceDetail1Activity,R.drawable.nonselecteditem_dot))
                }

                dots[position]?.setImageDrawable(ContextCompat.getDrawable(this@AppECommerceDetail1Activity,R.drawable.selecteditem_dot))
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


        //region Size
        size1TextView.setOnClickListener {
            size1Status = if (size1Status) {
                setSelectUnSelectSizeFilter(size1BgImageView, R.color.md_grey_400, size1TextView, R.color.md_grey_800)
                false
            } else {
                setSelectUnSelectSizeFilter(size1BgImageView, R.color.colorPrimary, size1TextView, R.color.md_white_1000)
                true
            }

        }

        size2TextView.setOnClickListener {
            size2Status = if (size2Status) {
                setSelectUnSelectSizeFilter(size2BgImageView, R.color.md_grey_400, size2TextView, R.color.md_grey_800)
                false
            } else {
                setSelectUnSelectSizeFilter(size2BgImageView, R.color.colorPrimary, size2TextView, R.color.md_white_1000)
                true
            }

        }

        size3TextView.setOnClickListener {
            size3Status = if (size3Status) {
                setSelectUnSelectSizeFilter(size3BgImageView, R.color.md_grey_400, size3TextView, R.color.md_grey_800)
                false
            } else {
                setSelectUnSelectSizeFilter(size3BgImageView, R.color.colorPrimary, size3TextView, R.color.md_white_1000)
                true
            }

        }

        size4TextView.setOnClickListener {
            size4Status = if (size4Status) {
                setSelectUnSelectSizeFilter(size4BgImageView, R.color.md_grey_400, size4TextView, R.color.md_grey_800)
                false
            } else {
                setSelectUnSelectSizeFilter(size4BgImageView, R.color.colorPrimary, size4TextView, R.color.md_white_1000)
                true
            }

        }

        size5TextView.setOnClickListener {
            size5Status = if (size5Status) {
                setSelectUnSelectSizeFilter(size5BgImageView, R.color.md_grey_400, size5TextView, R.color.md_grey_800)
                false
            } else {
                setSelectUnSelectSizeFilter(size5BgImageView, R.color.colorPrimary, size5TextView, R.color.md_white_1000)
                true
            }

        }

        //endregion

        //region Color

        color1ImageView.setOnClickListener {
            color1Status = if (color1Status) {
                color1ImageView.setImageDrawable(null)
                false
            } else {
                color1ImageView.setImageResource(R.drawable.baseline_select_with_check_transparent_24)
                true
            }

        }

        color2ImageView.setOnClickListener {
            color2Status = if (color2Status) {
                color2ImageView.setImageDrawable(null)
                false
            } else {
                color2ImageView.setImageResource(R.drawable.baseline_select_with_check_transparent_24)
                true
            }

        }

        color3ImageView.setOnClickListener {
            color3Status = if (color3Status) {
                color3ImageView.setImageDrawable(null)
                false
            } else {
                color3ImageView.setImageResource(R.drawable.baseline_select_with_check_transparent_24)
                true
            }

        }

        color4ImageView.setOnClickListener {
            color4Status = if (color4Status) {
                color4ImageView.setImageDrawable(null)
                false
            } else {
                color4ImageView.setImageResource(R.drawable.baseline_select_with_check_transparent_24)
                true
            }

        }

        color5ImageView.setOnClickListener {
            color5Status = if (color5Status) {
                color5ImageView.setImageDrawable(null)
                false
            } else {
                color5ImageView.setImageResource(R.drawable.baseline_select_with_check_transparent_24)
                true
            }

        }

        color6ImageView.setOnClickListener {
            color6Status = if (color6Status) {
                color6ImageView.setImageDrawable(null)
                false
            } else {
                color6ImageView.setImageResource(R.drawable.baseline_select_with_check_transparent_24)
                true
            }

        }

        color7ImageView.setOnClickListener {
            color7Status = if (color7Status) {
                color7ImageView.setImageDrawable(null)
                false
            } else {
                color7ImageView.setImageResource(R.drawable.baseline_select_with_check_transparent_24)
                true
            }

        }

        //endregion

        adapter.setOnItemClickListener(object : AppECommerceDetail1ReviewAdapter.OnItemClickListener{
            override fun onItemClick(view: View, obj: UserReview, position: Int) {
                Toast.makeText(this@AppECommerceDetail1Activity, "Selected : " + obj.userName, Toast.LENGTH_LONG).show()
            }
        })

        appECommerceDetail1PagerAdapter.setOnItemClickListener(object : AppECommerceDetail1PagerAdapter.OnItemClickListener{
            override fun onItemClick(view: View, obj: ShopItem, position: Int) {
                Toast.makeText(this@AppECommerceDetail1Activity, "Selected : " + obj.imageList[position].imageName!!, Toast.LENGTH_SHORT).show()
            }
        })

        viewAllCommentTextView.setOnClickListener { Toast.makeText(this, "Clicked View All Reviews.", Toast.LENGTH_SHORT).show() }
        addToBasketButton.setOnClickListener { Toast.makeText(this, "Clicked add to basket..", Toast.LENGTH_SHORT).show() }
        addToFavouriteButton.setOnClickListener { Toast.makeText(this, "Clicked add to favourite.", Toast.LENGTH_SHORT).show() }

        phoneTextView.setOnClickListener { Toast.makeText(this, "Clicked phone.", Toast.LENGTH_SHORT).show() }
        emailTextView.setOnClickListener { Toast.makeText(this, "Clicked email.", Toast.LENGTH_SHORT).show() }
        websiteTextView.setOnClickListener { Toast.makeText(this, "Clicked website.", Toast.LENGTH_SHORT).show() }
    }

    fun setupSliderPagination() {

        dotsCount = appECommerceDetail1PagerAdapter.count

        if (dotsCount > 0 ) {

            dots = arrayOfNulls(dotsCount)


                if (viewPagerCountDots.childCount > 0) {
                    viewPagerCountDots.removeAllViewsInLayout()

            }

            for (i in 0 until dotsCount) {
                dots[i] = ImageView(this)
                dots[i]?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.nonselecteditem_dot))

                val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )

                params.setMargins(4, 0, 4, 0)

                viewPagerCountDots.addView(dots[i], params)
            }

            dots[0]?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.selecteditem_dot))

        }

    }

    private fun initToolbar() {

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP)
        }

        toolbar.title = "Detail 1"

        try {
           toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.md_white_1000))
        } catch (e: Exception) {
            Log.e("TEAMPS", "Can't set color.")
        }

        try {
            setSupportActionBar(toolbar)
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set support action bar.")
        }

        try {
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set display home as up enabled.")
        }

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)

        if (Utils.isRTL) {
            collapsingToolbarLayout.collapsedTitleGravity = Gravity.END
        } else {
            collapsingToolbarLayout.collapsedTitleGravity = Gravity.START
        }
    }
}