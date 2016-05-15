package funciones;


import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import entidades.AbstractEntidad;
import entidades.Disparo;
import entidades.Enemigo;
import entidades.Jugador;


public class GameFunctions {
	
	//---- Detectar colisiones entre un disparo y sus posibles objetivos
	public static AbstractEntidad colisionar(Disparo disparo, Array<AbstractEntidad> objetivos){
    	Rectangle rectanguloDisparo = new Rectangle(disparo.getX(), disparo.getY(), disparo.getWidth(), disparo.getHeight());

		for(AbstractEntidad objetivo : objetivos){
	    	Rectangle rectanguloObjetivo = new Rectangle(objetivo.getSprite().getX(), objetivo.getSprite().getY(), objetivo.getSprite().getWidth(), objetivo.getSprite().getHeight());
	    	
	    	if(Intersector.overlaps(rectanguloDisparo, rectanguloObjetivo) && !objetivo.getEstado().equals("muriendo") && !objetivo.getEstado().equals("muerto")){
      			return objetivo;
	    	}
		}
		return null;
	}
	
	//---- Crear una entidad especifica
	public static AbstractEntidad crearEntidad(String tipo, String tipoNave, float x, float y){
		AbstractEntidad entidad;
		if(tipo.equals("jugador")){
			entidad = new Jugador(tipoNave);
			entidad.getSprite().setPosition(x, y);
		}
		else{
			entidad = new Enemigo(tipoNave);
			entidad.getSprite().setPosition(x, y);
		}
		return entidad;
	}
}


