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
public class PatientNotAssociatedException extends Exception {

    public PatientNotAssociatedException() {
    }

    public PatientNotAssociatedException(String msg) {
        super(msg);
    }
    
}