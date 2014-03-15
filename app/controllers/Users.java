/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import play.*;
import play.mvc.*;

@Check("admin")
@With(Secure.class)
public class Users extends CRUD{
    
}
