package com.spinnertech.noteapp.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spinnertech.noteapp.R
import com.spinnertech.noteapp.databinding.FragmentAddTaskBinding
import com.spinnertech.noteapp.models.CategoryItem
import com.spinnertech.noteapp.models.TaskItem
import com.spinnertech.noteapp.utils.SharedPrefManager
import com.spinnertech.noteapp.utils.Status
import com.spinnertech.noteapp.utils.Utils
import com.spinnertech.noteapp.viewmodels.AuthViewModel
import com.spinnertech.noteapp.viewmodels.CreateTaskViewModel
import retrofit2.http.Field
import java.util.*
import kotlin.math.log


class AddTaskFragment : Fragment() , DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private  lateinit var  createTaskViewModel: CreateTaskViewModel
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0
    var  start   :String = " "
    var  cat_id   :Int = 0
    lateinit var  binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater , container , false)
        setUpViewModel()
        setUpObservers()

//        @Field("title") title: String,
//        @Field("desc") desc: String,
//        @Field("start") start: String,
//        @Field("end") pass: String,
//        @Field("dated") dated: Int,
//        @Field("date") date: String,
//        @Field("is_whole_day") is_whole_day: Int,
//        @Field("user_id") user_id: Int,
//        @Field("cat_id") cat_id: Int,
        createTaskViewModel.getCat()
        binding.addTask.setOnClickListener {

             val id : Int? =  Utils.getUserData()?.user_id

            val taskItem : TaskItem = TaskItem(
                cat_id ,
                CategoryItem(0 ,"0", "0"),
                "",
                Utils.gatTodayDate(),
                0,
                binding.descEt.text.toString(),
                0 ,
                "0" ,
                0 ,
                0,
                start ,
                0 ,
                binding.title.text.toString()             ,
                "",
                id!!

            )
            Log.d("TAG", "onCreateView: "+ taskItem)
            createTaskViewModel.createTask(taskItem)

        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.catSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Get the value selected by the user
                // e.g. to store it as a field or immediately call a method
                val cat_item = parent.selectedItem as CategoryItem
                cat_id = cat_item.cat_id

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.date.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(requireActivity(), this@AddTaskFragment, year, month,day)
            datePickerDialog.show()
        }


        return binding.root

    }

    private fun setUpViewModel(){
        createTaskViewModel = ViewModelProvider(
            this).get(
            CreateTaskViewModel::class.java
        )
    }

    private fun setUpObservers() {
        createTaskViewModel.createTaskResp.observe(requireActivity() , {
                it->
            it?.let {
                    resource ->
                when (resource.status){
                    Status.SUCCESS -> {
                        // store the data
                      findNavController().navigate(R.id.dashboardFragment)

                    }
                    Status.ERROR -> {
                        Toast.makeText(context , "Error : ${resource.message}" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        createTaskViewModel.categoryList.observe(
            requireActivity(),{
                it?.let {
                        resource ->
                    when (resource.status){
                        Status.SUCCESS -> {
                            // store the data
                            val apater  = ArrayAdapter(
                                requireContext() ,
                                android.R.layout.simple_spinner_dropdown_item,
                                resource.data?.data as MutableList<CategoryItem>
                            )
                            binding.catSpinner.adapter  = apater
                        }
                        Status.ERROR -> {
                            Toast.makeText(context , "Error : ${resource.message}" , Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = day
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(context, this@AddTaskFragment, hour, minute,
            DateFormat.is24HourFormat(context))
        timePickerDialog.show()


    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
       // binding.date.text = "Year: " + myYear + "\n" + "Month: " + myMonth + "\n" + "Day: " + myDay + "\n" + "Hour: " + myHour + "\n" + "Minute: " + myMinute

        var date  = "$myDay/$myMonth/$myYear , $myHour-$myMinute"
        start = "$myHour-$myMinute , $myDay/$myMonth/$myYear"
        binding.date.text = date
    }

}
