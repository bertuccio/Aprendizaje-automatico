package clasificadores.genetico.recombinacion;

import clasificadores.genetico.poblacion.individuo.Individuo;
import java.util.ArrayList;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public interface Recombinacion {

    /*Funcion que recombina dos individuos,
    Salida: Una lista con los individuos nuevos.
    */
    public ArrayList<Individuo> recombina(Individuo a, Individuo b);
}
