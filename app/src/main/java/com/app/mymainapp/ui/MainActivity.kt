package com.app.mymainapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.app.mymainapp.baseresult.BaseResult
import com.app.mymainapp.databinding.ActivityMainBinding
import com.app.mymainapp.listeners.OnItemClickListener
import com.app.mymainapp.localdatabaseservice.entities.StudentEntity
import com.app.mymainapp.models.TestApiResponseModel
import com.app.mymainapp.networkconnectivity.NetworkConnectivityManager
import com.app.mymainapp.preferences.PreferenceHandler
import com.app.mymainapp.rxbus.RxBus
import com.app.mymainapp.rxbus.RxEvent
import com.app.mymainapp.ui.adapters.TestAdapter
import com.app.mymainapp.utils.StylishToastyUtils
import com.app.mymainapp.utils.hide
import com.app.mymainapp.utils.show
import com.app.mymainapp.utils.showToast
import com.app.mymainapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClickListener {

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    @Inject
    lateinit var stylishToastyUtils: StylishToastyUtils


    private val binding: ActivityMainBinding by viewBinding()

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var testAdapter: TestAdapter

    private lateinit var personDisposable: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceHandler.userToken = "Hello world"
        stylishToastyUtils.showSuccessMessage(preferenceHandler.userToken)






        testAdapter = TestAdapter(this)
        binding.testRecyclerView.adapter = testAdapter


        mainViewModel.getPosts()


        mainViewModel.res.observe(this, Observer {

            when (it.status) {
                BaseResult.Status.SUCCESS -> {

                    stylishToastyUtils.showSuccessMessage("success")

                    binding.appLoader.hide()
                    binding.testRecyclerView.show()
                    it.data.let { testList ->
                        testList?.let { it1 -> testAdapter.submitList(it1) }
                    }
                }

                BaseResult.Status.ERROR -> {
                    binding.appLoader.hide()
                    binding.testRecyclerView.show()

                    stylishToastyUtils.showErrorMessage("${it.message}")

                    Timber.e("${it.message}")


                }

                BaseResult.Status.LOADING -> {
                    binding.appLoader.show()
                    binding.testRecyclerView.hide()

                    stylishToastyUtils.showInfoMessage("Loading...")

                }
            }

        })


        //For Room Insert Data
        val studentEntity = StudentEntity(1, "Jishnu", "P Dileep", "12th", 20)
        mainViewModel.InsertStudentData(studentEntity)

        mainViewModel.insertStudentData.observe(this, Observer { rowId ->
            Timber.e("Inserted %d", rowId)
        })

        //For Room Fetch Data
        mainViewModel.GetAllStudentsData()
        mainViewModel.GetAllStudentData.observe(this, Observer { studentList ->

            studentList.forEach { item ->
                Timber.tag("Details").e(
                    "%d %s %s %d %s",
                    item.studentId,
                    item.fName,
                    item.lName,
                    item.age,
                    item.standard
                )
            }

        })


        //RxEventBus Observing
        personDisposable = RxBus.listen(RxEvent.EventAddPerson::class.java).subscribe {
            showToast(it.personName)
        }
    }

    @SuppressLint("LogNotTimber")
    override fun onItemClick(key: String, item: Any) {
        val testModel = item as TestApiResponseModel

        when (key) {
            "root" -> {
                RxBus.publish(
                    RxEvent.EventAddPerson(
                        "Root Person" +
                                ""
                    )
                )
            }

            "user" -> {
                Toast.makeText(this, "${item.userId}", Toast.LENGTH_SHORT).show()
            }

            "title" -> {
                Toast.makeText(this, "${item.title}", Toast.LENGTH_SHORT).show()
            }

            "description" -> {
                Toast.makeText(this, "${item.body}", Toast.LENGTH_SHORT).show()
            }

            else -> {
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (!personDisposable.isDisposed) personDisposable.dispose()
    }


}