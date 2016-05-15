package main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.MenuScreen;
import perfil.PerfilJugador;

public class Main extends Game {
	public SpriteBatch batch;
	public static PerfilJugador perfilJugador;

	@Override
	public void create () {
		batch = new SpriteBatch();
		perfilJugador = new PerfilJugador("Anxo");
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}


