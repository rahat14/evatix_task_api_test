<?php
namespace App\Models ; 

use Illuminate\Database\Eloquent\Model;

class TaskModel extends Model
{
 
    public $timestamps = false ; 
    protected $primary_key = 'task_id' ; 
    protected $table = "task"; 
    
    protected $fillable =[
            'task_id', 
            'user_id' , 
            'start_at',
            'end_at',
            'dated',
            'is_whole_day',
            'is_completed',
            'description'

    ];
}
