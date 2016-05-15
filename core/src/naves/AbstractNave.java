package naves;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class AbstractNave {
	//-------------------------------------------------------
	//-------- Declaracion de variables
	//-------------------------------------------------------
	protected String nombreNave;
	
	protected int vida;	
	protected int fuerza;
	protected int resistencia;
	protected float rapidez;
	
	protected Texture spritesTextura;
	protected TextureRegion[][] spritesArray;
	
	protected Texture disparoImagen;
	
	protected Animation animacionMovimiento;
	protected Animation animacionMuerte;
	
	
	//-------------------------------------------------------
	//-------- Constructor
	//-------------------------------------------------------
	public AbstractNave(Texture spritesTextura, float duracionFrameMovimiento, float duracionFrameMuerte){
		//---- Textura con todos los sprites
		this.spritesTextura = spritesTextura;
		
		//---- Creacion de animaciones                                      columnas                       filas
		spritesArray = TextureRegion.split(spritesTextura, spritesTextura.getWidth() / 3, spritesTextura.getHeight() / 7);

		TextureRegion[] spritesMovimiento = new TextureRegion[12];
		TextureRegion[] spritesMuerte = new TextureRegion[6];
		
		int indice = 0;
		for(int i=0 ; i<7 ; i++) {  // Fila
			for (int j=0 ; j<3 ; j++){  // Columna
				indice++;
				if(indice > 3 && indice < 16)  // Sprites 4,5,6,7,8,9 de movimiento ; Sprites 10,11,12,13,14,15 de movimento y golpeado
					spritesMovimiento[indice-4] = spritesArray[i][j];
			
				else if(indice > 15 && indice < 22)  // Sprites 16,17,18 inicio de la explosion de la muerte ; Sprites 19,20,21 restos de la explosion
					spritesMuerte[indice-16] = spritesArray[i][j];
			}
		}
		
		animacionMovimiento = new Animation(duracionFrameMovimiento, spritesMovimiento);
		animacionMuerte = new Animation(duracionFrameMuerte, spritesMuerte);

	}
	
	//-------------------------------------------------------
	//-------- Getters
	//-------------------------------------------------------
	
	//---- Nombre
	public String getNombre(){
		return nombreNave;
	}
	
	//---- Vida
	public int getVida(){
		return vida;
	}
	
	//---- Fuerza
	public int getFuerza(){
		return fuerza;
	}
			
	//---- Resistencia
	public int getResistencia(){
		return resistencia;
	}
			
	//---- Score
	public float getRapidez(){
		return rapidez;
	}
	
	//---- Sprites
	public TextureRegion[][] getSprites(){
		return spritesArray;
	}
	
	public Texture getDisparoImagen(){
		return disparoImagen;
	}
	
	//---- Animacion
	public Animation getAnimacionMovimiento(){
		return animacionMovimiento;
	}
	public Animation getAnimacionMuerte(){
		return animacionMuerte;
	}
	
	//-------------------------------------------------------
	//-------- Metodos
	//-------------------------------------------------------
	
	//---- Animacion movimiento
	public TextureRegion animarMovimiento(float tiempo){
		return animacionMovimiento.getKeyFrame(tiempo, false);
	}
	
	//---- Animacion muerto
	public TextureRegion animarMuerte(float tiempo){
		return animacionMuerte.getKeyFrame(tiempo, false);
	}
	
	//---- Dispose
	public void dispose(){
		spritesTextura.dispose();
	}
}

