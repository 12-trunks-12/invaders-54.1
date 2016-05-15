package funciones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class MenuFunctions {
	public static BitmapFont crearFont(String fontPath, int tamaño, Color color, Color bordeColor){
			
			FreeTypeFontGenerator generadorFonts = new FreeTypeFontGenerator(Gdx.files.internal("fonts/"+fontPath));
			
			FreeTypeFontParameter parametros = new FreeTypeFontParameter();
			parametros.size = tamaño;
			parametros.color = color;
			parametros.borderColor = bordeColor;
			
			BitmapFont font = generadorFonts.generateFont(parametros);
			
			generadorFonts.dispose();
			
			return font;
			
		}
}
