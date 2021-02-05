<?php
namespace App\Models ; 

use Illuminate\Database\Eloquent\Model;

class UserModel extends Model
{
 
    protected $primary_key = 'user_id' ; 
    protected $table = "user"; 
    
    protected $fillable =[
            'user_id', 
            'user_name' , 
            'password_hash',
            'isActive',
            'updated_at',
            'created_at',
            'email'
    ];
}
