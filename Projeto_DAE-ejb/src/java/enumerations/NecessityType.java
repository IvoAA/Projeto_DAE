/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumerations;

/**
 *
 * @author Figueiredo
 */
public enum NecessityType {
    
    TRONCO("Tronco"), MEMBROS_SUPERIORES("Membros Superiores"), 
    MEBROS_INFERIORES("Membros Inferiores"), PSICOLOGICA("Psicologioca");
    
    
    
    private String necessityType;

    NecessityType(String necessityType) {
        this.necessityType = necessityType;
    }

    public String getNecessityType() {
        return this.necessityType;
    }
    
}
