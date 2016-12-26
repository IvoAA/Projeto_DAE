/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Ivo
 */
public class PatientAssociateException extends Exception {

    public PatientAssociateException() {
    }

    public PatientAssociateException(String msg) {
        super(msg);
    }
    
}