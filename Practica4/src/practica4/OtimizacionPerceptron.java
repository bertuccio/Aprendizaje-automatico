package practica4;
import java.util.ArrayList;
import optimizador.data.Datos;
import optimizador.factory.Factory;
import optimizador.factory.FactoryPerceptron;
import optimizador.genetico.Entorno;
import optimizador.genetico.Evaluador;
import optimizador.genetico.FitnessFunction;
import optimizador.genetico.Individuo;
import optimizador.genetico.mutacion.Mutacion;
import optimizador.genetico.mutacion.MutacionGen;
import optimizador.genetico.recombinacion.Recombinacion;
import optimizador.genetico.recombinacion.RecombinacionSimple;
import optimizador.genetico.seleccion.Seleccion;
import optimizador.genetico.seleccion.SeleccionRuleta;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

/**
 * Main de Optimizacion de MLP
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class OtimizacionPerceptron {

    public static void main(String[] args) throws Exception {
        
        ArrayList<Mutacion> mutaciones = new ArrayList<>();
        ArrayList<Individuo> bestOnes = new ArrayList<>();
        mutaciones.add(new MutacionGen());
        ArrayList<Seleccion> selecciones = new ArrayList<>();
        selecciones.add(new SeleccionRuleta());
        ArrayList<Recombinacion> recombinaciones = new ArrayList<>();
        recombinaciones.add(new RecombinacionSimple());
        
        Evaluador fitness = new FitnessFunction();
        
        Factory factoryPerceptron = new FactoryPerceptron();
        Individuo bestOne = null;
        
        int nFolds = 10;
        int epochs = 10;
        int population = 10;
        
        String inputFile = "input";
           
            
        for (int i = 0; i < args.length; i++) {

            if (args[i].compareTo("-input") == 0) {

                inputFile = args[i + 1];
                i++;
            }
            if (args[i].compareTo("-nFolds") == 0) {

                nFolds = Integer.parseInt(args[i + 1]);
                i++;
            }
            if (args[i].compareTo("-epochs") == 0) {

                epochs = Integer.parseInt(args[i + 1]);
                i++;
            }
            
            if (args[i].compareTo("-population") == 0) {

                population = Integer.parseInt(args[i + 1]);
                i++;
            }
        }
        
        Datos data = new Datos(inputFile);
//        Entorno e1 = new Entorno(10,0.01,0.6,new FitnessFunction(),
//            data.getData(), factoryPerceptron);
//        //Poblacion pob = new Poblacion(3, factoryPerceptron);     
//        e1.setCruces(recombinaciones);
//        e1.setMutaciones(mutaciones);
//        e1.setSelecciones(selecciones);
//        pob.scoring(new FitnessFunction(), data.getData(), 3);
//        pob.sort();
//        
//        
//        System.out.println(pob.getIndividuos()[0].getScore());
//        System.out.println(pob.getIndividuos()[1].getScore());
//        System.out.println(pob.getIndividuos()[2].getScore());
//        double error = 0;
        double best = Integer.MAX_VALUE;
        for (int fold = 0; fold < nFolds; fold++) {
            
            Instances training = data.getData().trainCV(nFolds, fold);
            Evaluation eval = new Evaluation(training);
            
            for(int fold2 = 0; fold2 < (nFolds-1); fold2++){
                
                Entorno entorno = new Entorno(population, 0.01, 0.6, fitness,
                training, factoryPerceptron, nFolds-1);
                entorno.setCruces(recombinaciones);
                entorno.setMutaciones(mutaciones);
                entorno.setSelecciones(selecciones);
                
     
                for(int i=0;i<epochs;i++){
                    entorno.epoch();
                    if(fold == 0 && fold2 == 0){
                        for(Individuo ind : entorno.getPoblacion().getIndividuosPoblacion())
                            System.out.print(" "+ind.getScore()+" HN:"+((MultilayerPerceptron)ind.getClasificador()).getHiddenLayers());
                        System.out.println();
                    }
                }
                bestOnes.add(entorno.getKing());
            }
            
            Instances test = data.getData().testCV(nFolds, fold);
            System.out.println("----BEST ONES----");
            for(Individuo i : bestOnes){
                eval.evaluateModel(i.getClasificador(), test);
                System.out.println(1-eval.errorRate()+" HN:"+((MultilayerPerceptron)i.getClasificador()).getHiddenLayers());
                if(best>eval.errorRate()){
                    best = eval.errorRate();
                    bestOne = i;
                }
                    
            }
            
            bestOnes.clear();
            //error += 
            //error += eval.errorRate();
        }
        System.out.println("--------BEST ONE------");
        System.out.println("<"+(1-best)+">"+" HN:"+((MultilayerPerceptron)bestOne.getClasificador()).getHiddenLayers());

    }      
}

