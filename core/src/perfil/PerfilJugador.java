package perfil;


import naves.AbstractNave;
import naves.DefaultNave;


/*** Perfil del jugador: dinero disponible, nave actual, maxima puntuacion, etc. ***/
public class PerfilJugador {
	
	//-------------------------------------------------------
	//-------- Declaracion de variables
	//-------------------------------------------------------
	String nombre;
	
	private int maxScore;
	private int nivel;
	
	private int dinero;
	private String naveActual;
	
	
	//-------------------------------------------------------
	//-------- Constructor
	//-------------------------------------------------------
	public PerfilJugador(String nombre){
		this.nombre = nombre;
		
		maxScore = 0;
		nivel = 1;

		dinero = 0;
		naveActual = "Default";
	}
	
	
	//-------------------------------------------------------
	//-------- Getters y setters
	//-------------------------------------------------------
	
	//--------------------------------------
	//---------- PARTIDA
	
	//---- Score
	public void setMaxScore(int puntuacion){
		this.maxScore = puntuacion;
	}
	public int getMaxScore(){
		return maxScore;
	}
	
	//--------------------------------------
	//---------- STATS
	
	//---- Nivel
	public int getNivel(){
		return nivel;
	}
	
	//--------------------------------------
	//---------- TIENDA
	
	//---- Dinero
	public void setDinero(int dinero){
		this.dinero = dinero;
	}
	public int getDinero(){
		return dinero;
	}
	
	//---- Nave
	public void setNave(String nave){
		this.naveActual = nave;
	}
	public String getNave(){
		return naveActual;
	}
	
	
	//-------------------------------------------------------
	//-------- Funciones
	//-------------------------------------------------------
	public void subirNivel(){
		this.nivel += 1;	
		setDinero(getDinero() + getNivel()*1000);
	}
	
	@Override
	public String toString(){
		String cadena = this.nombre + " (Nvl." + Integer.toString(getNivel()) + "): /n" + 
	"- Nave: " + naveActual + "/n" +
	"- Dinero: " + Integer.toString(getDinero());
		
		return cadena;
	}

}
