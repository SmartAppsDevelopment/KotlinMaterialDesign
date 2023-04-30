package com.panaceasoft.pskotlinmaterial.activity.application.education.coursecollection

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.`object`.EducationCourseCollection
import com.panaceasoft.pskotlinmaterial.adapter.application.education.coursecollection.AppEducationCourseCollectionList2Adapter
import com.panaceasoft.pskotlinmaterial.repository.education.EducationCourseCollectionRepository
import kotlinx.android.synthetic.main.app_education_course_collection_list_2_activity.*

class AppEducationCourseCollectionList2Activity : AppCompatActivity() {

    // data and adapter
    internal lateinit var educationCourseCollectionRepositoryList: List<EducationCourseCollection>
    internal lateinit var adapter: AppEducationCourseCollectionList2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_education_course_collection_list_2_activity)

        initData()

        initUI()

        initDataBindings()

        initActions()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else {
            Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initData() {
        educationCourseCollectionRepositoryList = EducationCourseCollectionRepository.courseCollection

    }

    private fun initUI() {
        initToolbar()

        // get list adapter
        adapter = AppEducationCourseCollectionList2Adapter(educationCourseCollectionRepositoryList)

        // get recycler view
        val mLayoutManager = LinearLayoutManager(applicationContext)
        videoRecyclerView.layoutManager = mLayoutManager
        videoRecyclerView.itemAnimator = DefaultItemAnimator()


    }

    private fun initDataBindings() {
        // bind adapter to recycler
        videoRecyclerView.adapter = adapter

    }

    private fun initActions() {
        adapter.setOnItemClickListener(object : AppEducationCourseCollectionList2Adapter.OnItemClickListener {
            override fun onItemClick(view: View, obj: EducationCourseCollection, position: Int) {
                Toast.makeText(applicationContext, "Clicked " + obj.name, Toast.LENGTH_SHORT).show()
            }
        })
    }


    //region Init Toolbar
    private fun initToolbar() {

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP)
        }

        toolbar.title = "Course Collection 2"

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

    }
}


