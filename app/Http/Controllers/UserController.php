<?php

namespace App\Http\Controllers;

use App\Models\UserModel ; 
use Illuminate\Http\Request  ; 
use Laravel\Lumen\Routing\Controller as BaseController;

class UserController extends Controller
{
    // this controls all the base operation of USER 
    /*
     login 
     registration  

    */
    public function testAllUser()
    {
        // get all the user 
        // test purpose only 
        
        $list = UserModel::get() ; 
        
       return response()-> json($list , 200) ; 
    }

    public function login(Request  $req){

        $mail = $req->mail ; 
        $passwordHash = $req->passwordHash ; 

        $userList = UserModel::query()
        -> where("email", $mail)
        ->where("password_hash" , $passwordHash)
        ->limit(1)
        ->get()  ; 
        $counter = $userList->count() ; 


        if($counter>0){
            return response()-> json(
                [
                    'msg'=> 'User Found' , 
                    'error'=> false ,
                    'data'=> $userList
                ],200
            );

        }else {
            return response()-> json(
                [
                    'msg'=> 'User Not Found !!!' , 
                    'error'=> true ,
                    'data'=> $userList
                ],200
            );
        }


    }



    /* User Register  

    *   user_name ; 
        mail ; 
        passwordHash ;  actual hash 
        as form-data 
    */


    public function UserRegistration(Request $req){

        $user_name = $req->user_name ; 
        $mail = $req->mail ; 
        $passwordHash = $req->passwordHash ; 
        /*
         1st check the mail exist for not if exist throw error 
        */

        $userList = UserModel::query()
        ->where("email", $mail) 
        ->limit(1)
        ->get(); 
        $counter = $userList->count() ; 


        if($counter>0){
            
            // user already exist 
            return response()-> 
            json([
                'msg'=> 'User all ready Exist !!!' , 
                'error'=> true   ,
                'data'=> []   
            ], 200) ; 


        }else {
                // user not exist  rahat shovo -> rahat shovo
                // proceed 
                $user = new UserModel() ; 
                $user -> user_name = str_replace('%20' , ' ' , $user_name) ; 
                $user-> email = $mail ; 
                $user-> isActive = true ; 
                $user-> password_hash = $passwordHash ; 
                $user -> save() ; 

                return response()-> 
                json([
                    'msg'=> 'User Registerd !!!' , 
                    'error'=> false  ,
                    'data'=> [$user]   
                ], 200) ; 

        }




    }


    public function ResetPass(Request $req)
    {
        $mail = $req->mail ; 
        $newPassHash = $req->new_pass_hash; 
           
        /*
            suppose mail was sent for verificain code
            here we will just reset the password here 

        */

        $user = UserModel::query()
        ->where("email" , $mail)
        ->limit(1) 
        ->get(); 

        $counter = $user->count() ; 

        if($counter>0){

            $passChange = UserModel::query()
            ->where("email" , $mail)
            ->update(['password_hash' => $newPassHash]);


            return response()-> json(
                [
                    'msg'=> 'Password Changed' , 
                    'error'=> false ,
                    'data'=> $user
                ],200
            );

        }else {
            return response()-> json(
                [
                    'msg'=> 'User Not Found !!!' , 
                    'error'=> true ,
                    'data'=> []
                ],200
            );
        }



    }

}
