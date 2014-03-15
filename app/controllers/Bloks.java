package controllers;

import java.io.File;
import java.util.*;
import play.mvc.*;


import models.*;

import play.data.validation.*;
import play.db.jpa.Blob;

@With(Secure.class)
public class Bloks extends Controller {

    public static void index() {
        
        // hard-> title=("%s",title);      
        //Logger.info(" blogs id 1 has title  "+b.title);
        render();
    }

    public static void add(@Required String title, @Required String content,@Required Date postedAt) {
        
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            render("@Application.form");
        }else{
            //Logger.info(postedAt.toString());
            //save data and take us to list of 
            Blog post=new Blog();
           
            post.title=title;
            post.content=content;
            post.postedAt=postedAt;
           
            post.save();
            flash.success("The blog has being added");
          list();
        
        }
        
    }

   public static void signUp(@Required @Email String email,@Required String password,@Required String fullname){
   
       if (Validation.hasErrors()) {
            params.flash();
            Validation.keep();
            render("@Application.form3");
       }
       
       else {
           User user=new User(email,fullname,password);
           user.create();
        session.put("user", user.email);
        flash.success("Welcome, " + user.fullname);
           user.save();
           
          list();
          
           
       }
   }
    
    public static void editSave(@Valid Blog anotherpost){
          
     if(Validation.hasErrors()){
         if(request.isAjax()) error("Invalid value");
            render("@Application.form2",anotherpost);
        }
     else {

        anotherpost.save();
         list();
     }
  } 
    
    
    public static void list() {
        List<Blog> anotherposts=Blog.find("order by title, content").fetch();
        render(anotherposts);
    }

    public static void view(Long id) {
        Blog post=Blog.findById(id);
       
        render(post);
    }
    
    public static void delete(Long id){
        Blog post=Blog.findById(id);
        post.delete();
        flash.success("The blog has been deleted");
        list();
    }
}