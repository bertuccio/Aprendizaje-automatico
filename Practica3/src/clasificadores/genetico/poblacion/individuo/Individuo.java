/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificadores.genetico.poblacion.individuo;

import clasificadores.genetico.UtilesGenetico;
import java.util.ArrayList;

/**
 *
 * @author temporal
 */
public class Individuo implements Comparable<Individuo> {
    
    private ArrayList<Regla> reglas = new ArrayList<>();
    private int numGensRegla;
    private int mutated;
    private double score;
    
    
    public Individuo(int nClases,int nAtributos,int rangoAtributos,int maxReglasIni){
        this.mutated = 1; 
        this.numGensRegla = nAtributos * rangoAtributos;
        int nReglas = UtilesGenetico.randomNumber(maxReglasIni);
        for(int i = 0; i<nReglas;i++){
            this.reglas.add(new Regla(nClases,nAtributos,rangoAtributos));
        }
        this.score = 0; 
    }
    
    public Individuo(int numGensRegla,ArrayList<Regla> reglas){
        this.mutated = 1; 
        this.numGensRegla = numGensRegla;
        this.reglas = reglas;
        this.score = 0; 
    }
    
    /*Muestra debe de ser solo los datos a machear sin la clase*/
    public int evaluate(double muestra[]){
        int claseActual;
        for (Regla reglaActual : this.reglas){
            claseActual = reglaActual.evaluate(muestra);
            if (claseActual != 0)
                return claseActual;
        }
        return 0;
    }
    
    public ArrayList<Regla> getReglas() {
        return reglas;
    }

    public void setReglas(ArrayList<Regla> reglas) {
        this.reglas = reglas;
    }

    public int getNumGensRegla() {
        return numGensRegla;
    }

    public void setNumGensRegla(int numGensRegla) {
        this.numGensRegla = numGensRegla;
    }

    public int getMutated() {
        return mutated;
    }

    public void setMutated(int mutated) {
        this.mutated = mutated;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(Individuo o) {
        return new Double(this.score).intValue() - new Double(o.score).intValue();
    }

}
