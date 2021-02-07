<?php

namespace App\Http\Controllers;

use App\Models\UserModel ; 
use App\Models\TaskModel ; 
use App\Models\CategoryModel ; 
use Illuminate\Http\Request  ; 
use Laravel\Lumen\Routing\Controller as BaseController;
use DB; 
class TaskController extends Controller
{
    public function GetAllTaskById($user_id)
    {
            $userTaskList = TaskModel::query()
                            ->where( "user_id", $user_id)
                            ->with('category')
                            ->get() ;
                            
                            return response()-> json(
                                [
                                    'msg'=> 'Successfull' , 
                                    'error'=> false ,
                                    'data'=> $userTaskList
                                ],200
                            );
    }


    public function GetAllTaskByDate($user_id , $date)
    {
            $userTaskList = TaskModel::query()
                            ->where( "user_id", $user_id)
                            ->where('date' , $date)
                            ->with('category')
                            ->get() ;
                            
                            return response()-> json(
                                [
                                    'msg'=> 'Successfull' , 
                                    'error'=> false ,
                                    'data'=> $userTaskList
                                ],200
                            );
    }



    /*
    crearte a  task form data 
    title , start , end , dated , is_whole_day , desc , user_id 

    */

    public function insertTask(Request $req ){ // mapp all the form data 
        
        $newTask = new  TaskModel() ; 

        //assaign value to $ new task 
        $newTask->task_title = $req->title ; 
        $newTask->description = $req ->desc ; 
        $newTask->start_at= $req->start ; 
        $newTask->date = $req->date ; 
        $newTask->end_at = $req->end ; 
        $newTask->dated = $req->dated ; 
        $newTask->is_whole_day = $req->is_whole_day ; 
        $newTask->is_completed = false; 
        $newTask->user_id = $req->user_id ; 
        $newTask->cat_id = $req->cat_id ; 
        // save it 
        $newTask->save() ; 

        return response()-> 
        json([
            'msg'=> 'Task Saved !!!' , 
            'error'=> false  ,
            'data'=> [$newTask]   
        ], 200) ; 
         
     
    }

    public function updateTask(Request $req)
    {
         $task_id  = $req->task_id; 
          //assaign value to $ new task 
        //   $newTask = new  TaskModel() ; 
        //   $newTask->task_id  = $req->task_id; 
        //   $newTask->task_title = $req->title ; 
        //   $newTask->description = $req ->desc ; 
        //   $newTask->start_at = $req->start ; 
        //   $newTask->end_at = $req->end ; 
        //   $newTask->dated = $req->dated ; 
        //   $newTask->is_whole_day = $req->is_whole_day ; 
        //   $newTask->is_completed = $req->is_completed;
        //   $newTask->user_id = $req->user_id; 


     TaskModel::query()
        ->where("task_id" , $task_id)
          ->update([
            'task_id' => $req->task_id, 
            'start_at' => $req->start ,
            'end_at' =>  $req->end,
            'dated' =>  $req->dated,
            'date' => $req->date ,
            'task_title' => $req->title , 
            'is_whole_day' => $req->is_whole_day,
            'is_completed' =>$req->is_completed,
            'description' => $req->desc
         ]) ;

          return response()-> 
          json([
              'msg'=> 'Task Updated !!!' , 
              'error'=> false  ,
              'data'=> []   
          ], 200) ; 
           
  
    }

    // delete task via path 
    
     public function deleteTask($task_id)
    {
        TaskModel::query()
        ->where("task_id" , $task_id)
        ->delete() ; 
        
        return response()-> 
        json([
            'msg'=> 'Task Deleted !!!' , 
            'error'=> false  ,
            'data'=> []   
        ], 200) ; 


    }

     public function categoryListCount()
    {
     
          $countList = CategoryModel::get();

            return response()-> 
            json([
                'msg'=> 'found' , 
                'error'=> false  ,
                'data'=> $countList  
            ], 200) ; 
    
    }



}