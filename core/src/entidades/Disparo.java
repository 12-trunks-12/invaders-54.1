package entidades;

import com.badlogic.gdx.graphics.g2d.Sprite;

import naves.AbstractNave;


public class Disparo extends Sprite{
	
	private int fuerza;
	private float velocidad;
	private String direccion;
	
	//-------------------------------------------------------
	//-------- Constructor
	//-------------------------------------------------------
	public Disparo(AbstractNave naveEmisor, float x, float y, String direccion){
		super(naveEmisor.getDisparoImagen());
		
		fuerza = naveEmisor.getFuerza();
		velocidad = naveEmisor.getRapidez();
		this.direccion = direccion;
		setX(x);
		setY(y);
	}
	
	public int getFuerza(){
		return fuerza;
	}
	
	//-------------------------------------------------------
	//-------- Metodos
	//-------------------------------------------------------
	public void actualizarPosicion(float delta){
		switch(direccion){
			case "arriba": translateY(velocidad * delta);
			case "abajo": translateY(-velocidad * delta);
		}
	}
}

