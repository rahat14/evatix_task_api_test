<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It is a breeze. Simply tell Lumen the URIs it should respond to
| and give it the Closure to call when that URI is requested.
|
*/

$router->get('/', function () use ($router) {
    return $router->app->version();
});

$router->get('/test' , 'UserController@testAllUser') ;

$router->get('/tasks/{user_id}' , 'TaskController@GetAllTaskById') ;
 //deleteTask
 $router-> get('/delete_task/{task_id}' ,'TaskController@deleteTask');

 $router-> post('/login' ,'UserController@login') ; 


 $router-> post('/register' ,'UserController@UserRegistration') ;
 
 $router-> post('/reset_pass' ,'UserController@ResetPass') ; 

 $router-> post('/add_task' ,'TaskController@insertTask');


 $router-> post('/register' ,'UserController@UserRegistration') ;
 
 $router-> post('/reset_pass' ,'UserController@ResetPass') ; 
 
  
 $router-> post('/add_task' ,'TaskController@insertTask');

 $router-> post('/update_task' ,'TaskController@updateTask');


