package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import funciones.MenuFunctions;
import main.Main;


public class MenuScreen implements Screen {
	Main game;
	
	private Texture imagenFondo;
	
	private BitmapFont fontRotulo;
	private BitmapFont fontOpciones;
	private BitmapFont fontLetritas;
	
	
	public MenuScreen(Main game){
		this.game = game;
		
		imagenFondo = new Texture(Gdx.files.internal("images/menuFondo.jpg"));
		
		fontRotulo = MenuFunctions.crearFont("slant.ttf", 50, Color.CYAN, Color.BLUE);
		fontOpciones = MenuFunctions.crearFont("slice.ttf", 25, Color.GREEN, Color.BLACK);
		fontLetritas = MenuFunctions.crearFont("slice.ttf", 10, Color.GREEN, Color.BLACK);

		Gdx.gl.glClearColor(0, 0, 0, 1);
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	public void update(){
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			game.setScreen(new GameScreenLV1(game));
		}
		
		else if(Gdx.input.isKeyPressed(Input.Keys.O)){
			
		}
		
		else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			dispose();
			System.exit(0);
		}
	}
	
	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		game.batch.draw(imagenFondo, -50, 0);
		
		fontRotulo.draw(game.batch, "INVADERS 54.1", 75, 600);
		fontOpciones.draw(game.batch, "INICIO", 50, 420);
		fontOpciones.draw(game.batch, "OPCIONES", 150, 340);
		fontOpciones.draw(game.batch, "SALIR", 300, 260);
		
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		game.dispose();
		
		imagenFondo.dispose();
		
		fontRotulo.dispose();
		fontOpciones.dispose();
		fontLetritas.dispose();
		
		
	}

}
