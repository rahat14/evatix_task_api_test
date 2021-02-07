package com.spinnertech.noteapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.spinnertech.noteapp.R
import com.spinnertech.noteapp.adapters.categoryListAdpater
import com.spinnertech.noteapp.adapters.taskListAdpater
import com.spinnertech.noteapp.databinding.FragmentDashboardBinding
import com.spinnertech.noteapp.models.CategoryItem
import com.spinnertech.noteapp.models.TaskItem
import com.spinnertech.noteapp.utils.Status
import com.spinnertech.noteapp.utils.Utils
import com.spinnertech.noteapp.viewmodels.DashboardViewModel

class DashboardFragment : Fragment(), taskListAdpater.Interaction, categoryListAdpater.Interaction {

    private lateinit var dasboardViewModel: DashboardViewModel
    private lateinit var taskListAdapter: taskListAdpater
    private lateinit var categoryListAdpater: categoryListAdpater
    private lateinit var binding: FragmentDashboardBinding
    private var TaskList: MutableList<TaskItem> = ArrayList()
    private var cat_counter: MutableList<CategoryItem> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        setUpViewModel()
        setUpObservers()
        taskListAdapter = taskListAdpater(this@DashboardFragment)
        categoryListAdpater = categoryListAdpater(this@DashboardFragment)
        binding.taskList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskListAdapter
        }
        binding.categoryList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoryListAdpater
        }

        binding.add.setOnClickListener{
            findNavController().navigate(R.id.action_dashboardFragment_to_addTaskFragment)
        }

        dasboardViewModel.getProfileData()
        dasboardViewModel.getTodayTask(Utils.gatTodayDate())
        return binding.root
    }

    private fun setUpViewModel() {
        dasboardViewModel = ViewModelProvider(
            this
        ).get(
            DashboardViewModel::class.java
        )
    }

    private fun setUpObservers() {
        dasboardViewModel.today_taskList.observe(requireActivity(), { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.data?.let { it1 ->
                            TaskList = it1 as MutableList<TaskItem>
                            setDataToAdapters(it1)


                        }


                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Error : ${resource.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        )

        dasboardViewModel.categoryList.observe(requireActivity(), {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.data?.let { it1 ->
                            setUpCatGoryAdapter(it1)
                        }

                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Error : ${resource.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        })

        dasboardViewModel.UserData.observe(requireActivity(), {
            it?.let {
                binding.header.text = it.user_name


            }
        })
    }

    private fun setUpCatGoryAdapter(catList : List<CategoryItem>) {

        // we could have ignore all that if i make a response for this op
           val cat = TaskList.groupingBy {
               it.cat_id
           }.eachCount()
        Log.d("TAG", "setUpCatGoryAdapter: " +cat)

        catList.forEachIndexed{ it , item ->
            for((k ,v ) in cat ){
                if(item.cat_id == k){
                    item.counter = v
                }
            }
        }

        // now fillter the list  removing  all zero
        val newLIst = catList.filter{
            it.counter != 0
        }

        categoryListAdpater.submitList(newLIst)
        Log.d("TAG", "setUpCatGoryAdapter: " +newLIst)


    }

    private fun setDataToAdapters(data: List<TaskItem>) {
        dasboardViewModel.getCat()
        binding.todayStat.text = "Today You Have ${data.size.toString()} Tasks"
        // filter the today done task
        val doneTask = data.filter{
            it.is_completed == 0
    }
        binding.leftTodayCounter.text = (data.size - doneTask.size).toString()
        binding.allTaskToday.text = data.size.toString()+""

        taskListAdapter.submitList(data)



    }

    override fun onItemSelected(position: Int, item: TaskItem) {
    }

    override fun onCategoryItemSelected(position: Int, item: CategoryItem) {

        findNavController().navigate(R.id.allListFragment)
    }

    override fun onResume() {
        super.onResume()
    }
}
