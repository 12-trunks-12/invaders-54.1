package naves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Enemigo1Nave extends AbstractNave{
	//-------------------------------------------------------
	//-------- Constructor
	//-------------------------------------------------------
	public Enemigo1Nave() {
		super(new Texture(Gdx.files.internal("images/enemigo1Sprites.png")), 0.08f, 0.08f);
			
		this.nombreNave = "Default";
		this.vida = 500;
		this.fuerza = 50;
		this.resistencia = 5;
		this.rapidez = 75;
			
		this.disparoImagen = new Texture(Gdx.files.internal("images/disparoIMG.png"));
	}
}
