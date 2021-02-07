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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.spinnertech.noteapp.R
import com.spinnertech.noteapp.adapters.taskListAdpater
import com.spinnertech.noteapp.databinding.FragmentAllListBinding
import com.spinnertech.noteapp.models.TaskItem
import com.spinnertech.noteapp.models.UserModel
import com.spinnertech.noteapp.utils.Status
import com.spinnertech.noteapp.utils.SwipeHelper

import com.spinnertech.noteapp.utils.Utils
import com.spinnertech.noteapp.viewmodels.AllListViewModel


class AllListFragment : Fragment(), taskListAdpater.Interaction {

    private lateinit var allListView: AllListViewModel
    private lateinit var binding: FragmentAllListBinding
    private var toast: Toast? = null
    private lateinit var taskListAdapter: taskListAdpater
    private var taskList :MutableList<TaskItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentAllListBinding.inflate(inflater, container, false)
        setUpViewModel()
        setUpObservers()


        taskListAdapter = taskListAdpater(this@AllListFragment)

        binding.allTaskList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskListAdapter

        }




        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.allTaskList) {
            override fun instantiateUnderlayButton(position: Int): List<SwipeHelper.UnderlayButton> {
                var buttons = listOf<SwipeHelper.UnderlayButton>()
                val deleteButton = deleteButton(position)
//                    val markAsUnreadButton = markAsUnreadButton(position)
//                    val archiveButton = archiveButton(position)
                buttons = listOf(deleteButton)
//                when (position) {
//                    1 -> buttons = listOf(deleteButton)
////                        2 -> buttons = listOf(deleteButton, markAsUnreadButton)
////                        3 -> buttons = listOf(deleteButton, markAsUnreadButton, archiveButton)
//                    else -> buttons = listOf(deleteButton)
//                }
                return buttons
            }


        })

        itemTouchHelper.attachToRecyclerView(binding.allTaskList)
//        binding.allTaskList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL )
//        )

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.dashboardFragment)
        }

        // get user data
        val  model : UserModel? = Utils.getUserData();
        if(model!= null){
            allListView.getAllTaskList(model.user_id)
        }


        return binding.root
    }

    private fun toast(text: String) {
        toast?.cancel()
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)


        toast?.show()
    }
    private fun deleteButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    val id  = taskList.get(position).task_id
                   taskList.removeAt(position)
                 //   taskListAdapter.submitList(newList)
                    allListView.deleteTask(id)
                    Log.d("TAG", "onClick: " +position)

                }
            })
    }

    private fun setUpViewModel() {
        allListView = ViewModelProvider(
            this
        ).get(
            AllListViewModel::class.java
        )
    }

    private fun setUpObservers() {
        allListView.all_taskList.observe(requireActivity(), { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        // store the data
                        // fill the adapter
                        setupAdapter(resource.data?.data)
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Error : ${resource.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })

        allListView.deleteResp.observe(requireActivity(), { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        // store the data
                        // fill the adapter
                        Toast.makeText(context, "Msg : ${resource.data?.msg}", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Error : ${resource.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })


    }

    private fun setupAdapter(data: List<TaskItem>?) {

        if (data != null) {
            taskList = data as MutableList<TaskItem>
            taskListAdapter.submitList(data)
        }

    }

    override fun onItemSelected(position: Int, item: TaskItem) {
        /*

         */
    }




}
