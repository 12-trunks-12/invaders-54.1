package naves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class DefaultNave extends AbstractNave {
	
	//-------------------------------------------------------
	//-------- Constructor
	//-------------------------------------------------------
	public DefaultNave() {
		super(new Texture(Gdx.files.internal("images/defaultNaveSprites.png")), 0.08f, 0.125f);
		
		this.nombreNave = "Default";
		this.vida = 500;
		this.fuerza = 60;
		this.resistencia = 20;
		this.rapidez = 150;
		
		this.disparoImagen = new Texture(Gdx.files.internal("images/disparoIMG.png"));
	}
	
}
