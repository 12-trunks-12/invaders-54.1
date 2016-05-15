package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import entidades.AbstractEntidad;
import entidades.Disparo;
import entidades.Enemigo;
import entidades.Jugador;
import perfil.PerfilJugador;
import funciones.GameFunctions;
import main.Main;


public class GameScreenLV1 implements Screen {

	//-------------------------------------------------------
	//-------- Declaracion de variables
	//-------------------------------------------------------
	Main game;
	PerfilJugador perfilJugador;
	
	ShapeRenderer shapeRenderer;
	
	Music musicaFondo;
		
	Array<Array<Integer>> posicionEstrellas;
		
	//---- Jugador
	Jugador jugador = null;
	Array<AbstractEntidad> jugadoresArray;  // Por si en un futuro se cambia el juego para varios jugadores
	Texture imagenVida;
	Texture imagenSinVida;
		

	Sound disparoNaveSound;
	Music explosionNaveSound;
	
	//---- Enemigos
	Array<AbstractEntidad> enemigosArray;

	Sound disparoBotSound;
	Music explosionBotSound;

	
	//-------------------------------------------------------
	//-------- Creacion de naves y enemigos
	//-------------------------------------------------------
	public GameScreenLV1(Main game){
		this.game = game;
		perfilJugador = Main.perfilJugador;
		
		//--------------------------------------
		//---------- Variables varias
		shapeRenderer = new ShapeRenderer();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		//---- Juego	
		imagenVida = new Texture(Gdx.files.internal("images/corazonVivo.png"));
		imagenSinVida = new Texture(Gdx.files.internal("images/corazonDestruido.png"));
		
		//---- Musica
		musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("sounds/musicaFondo.mp3"));
		musicaFondo.play();
		musicaFondo.setVolume(0.3f);
	
		
		//--------------------------------------
		//---------- Jugador
		jugadoresArray = new Array<AbstractEntidad>();
		
		//---- Creacion de la entidad
		jugador = (Jugador) GameFunctions.crearEntidad("jugador", perfilJugador.getNave(), 200, 60);
		jugadoresArray.add(jugador);
		
		
		//--------------------------------------
		//---------- Naves enemigas
		
		//---- Creación automática de los enemigos
		enemigosArray = new Array<AbstractEntidad>();
		for(int i=1 ; i <= 5 ; i++){
			Enemigo enemigo = (Enemigo) GameFunctions.crearEntidad("enemigo", "Enemigo1", i*81.75f-21.75f, 725f); 
			enemigosArray.add(enemigo);
		}
		
			
		//--------------------------------------
		//---------- Posición de las estrellas
		posicionEstrellas = new Array<Array<Integer>>();  // Crea una lista donde cada elemento será una lista que contenga una coordenada (x,y)
		for(int i=0 ; i<401 ; i++){
			Array<Integer> coordenadas = new Array<Integer>();
			coordenadas.add(MathUtils.random(0, 480));  // x
			coordenadas.add(MathUtils.random(0, 800));  // y
			posicionEstrellas.add(coordenadas);
		}
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	
	//-------------------------------------------------------
	//-------- Manejo de eventos y actualizacion de datos (posicion, vida, etc.)
	//-------------------------------------------------------
	public void update (float delta) {

		//--------------------------------------
		//---------- Actualizacion de la posición de las estrellas
		for(Array<Integer> coordenadas : posicionEstrellas){
			int x = coordenadas.get(0);
			int y = (int) (coordenadas.get(1) + 1);
			if(y>800){
				x = MathUtils.random(0, 481);  // Para hacer que no tengan siempre la misma forma
				y = 0;
			}

			coordenadas.clear();
			coordenadas.add(x); 
			coordenadas.add(y);
		}
			
		
		//---- Cambiar de nivel
		if(Gdx.input.isKeyPressed(Keys.F1)) game.setScreen(new GameScreenLV1(game));
		//else if(Gdx.input.isKeyPressed(Keys.F2))game.setScreen(new GameScreenLV2(game));
		
		
		//--------------------------------------
		//---------- Actualizacion del jugador
		
		//---- Eventos
		if(!jugador.getEstado().equals("muriendo") && !jugador.getEstado().equals("muerto")){
			jugador.moverse(delta);
			if(jugador.canDisparar(delta) && Gdx.input.isKeyPressed(Keys.SPACE)){  // Espacio
				jugador.disparar();	
			}
		}
		
		else{
			if(Gdx.input.isKeyPressed(Keys.ENTER))  // La nave se muestra destruída con la opción de poder revivir, si no le quedan vidas pasa al siguiente nvl
				if(jugador.getVidas() > 0)
					jugador.revivir();	 
		}
		
		//---- Actualizar datos
		for(AbstractEntidad jugador : jugadoresArray){
		   //---- Disparos
		   jugador.actualizarDisparos();
		  
		   //---- Colisiones
		   for(Disparo disparo : jugador.getDisparosArray()){
			   AbstractEntidad enemigoGolpeado = GameFunctions.colisionar(disparo, enemigosArray);
			   if(enemigoGolpeado != null && !enemigoGolpeado.equals("muriendo") && !enemigoGolpeado.getEstado().equals("muerto")){
				   enemigoGolpeado.setVida(enemigoGolpeado.getVida() - disparo.getFuerza()); 
				   jugador.getDisparosArray().removeValue(disparo, true);
			   
				   if(enemigoGolpeado.getVida() <= 0){
					   enemigoGolpeado.setEstado("muriendo");
					   enemigoGolpeado.setVidas(enemigoGolpeado.getVidas() - 1);
				   }
				   
	     			else if(enemigoGolpeado.getEstado().equals("moviendose")) 
	     				enemigoGolpeado.setEstado("moviendose-golpeado");
				   
	     			else 
	     				enemigoGolpeado.setEstado("golpeado");
			   }
		   }
		   	
		   //---- Poner el sprite que toca
		   jugador.actualizarSprite(delta);
		   
		   //---- Eliminar jugador
		   if(jugador.isMuerto() && jugador.getVidas() < 1)
				jugadoresArray.removeValue(jugador, true);
		}
	       
	       
	    //--------------------------------------
		//---------- Actualización de los bots
		for(AbstractEntidad enemigo : enemigosArray){
			//---- Movimiento
			if(!enemigo.getEstado().equals("muriendo") && !enemigo.getEstado().equals("muerto")){
				if(enemigo.getEstado().equals("quieto") || enemigo.getEstado().equals("golpeado"))
					enemigo.pararse(delta);
				else
					enemigo.moverse(delta);
			}
			
			//---- Disparos
			enemigo.actualizarDisparos();
			
			//---- Colisiones
			for(Disparo disparo : enemigo.getDisparosArray()){
				   AbstractEntidad jugadorGolpeado = GameFunctions.colisionar(disparo, jugadoresArray);
				   if(jugadorGolpeado != null && !jugadorGolpeado.equals("muriendo") && !jugadorGolpeado.getEstado().equals("muerto")){
					   jugadorGolpeado.setVida(jugadorGolpeado.getVida() - disparo.getFuerza());
					   enemigo.getDisparosArray().removeValue(disparo, true);

					   if(jugadorGolpeado.getVida() <= 0){
						   jugadorGolpeado.setEstado("muriendo");
						   jugadorGolpeado.setVidas(jugadorGolpeado.getVidas() - 1);
					   }
					   
		     			else if(jugadorGolpeado.getEstado().equals("moviendose")) 
		     				jugadorGolpeado.setEstado("moviendose-golpeado");
					   
		     			else 
		     				jugadorGolpeado.setEstado("golpeado");
				   }
			}
			
			//---- Disparar
			if(enemigo.canDisparar(delta) && MathUtils.random(0, 100) == 0){
				enemigo.disparar();
			}
			
			//---- Poner el sprite que toca
			enemigo.actualizarSprite(delta);
			
			//---- Eliminar enemigo
			if(enemigo.isMuerto())
				enemigosArray.removeValue(enemigo, true);
		}
	}
		
	
	//-------------------------------------------------------
	//-------- Dibujo en la pantalla
	//------------------------------------------------------
	@Override
	public void render(float delta) {
		//--------------------------------------
		//---------- Actualizamos datos
		update(delta);
		
		//--------------------------------------
		//---------- Limpiamos la pantalla
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//--------------------------------------
		//---------- Formas
		
		//---- Estrellas
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.WHITE);
		for(Array<Integer> coordenadas : posicionEstrellas){
			shapeRenderer.rect(coordenadas.get(0), coordenadas.get(1), 1, 1);
		}
		
		//---- Barras de vida
		shapeRenderer.setColor(7/255f, 165/255f, 250/255f, 1f);
		int barraVidaRestante = jugador.getVida()/50;
		for(int i=0 ; i<barraVidaRestante ; i+=1){
			shapeRenderer.rect(10+i*8, 20, 3, 30);
		}
        shapeRenderer.end();
        
        
        //--------------------------------------
        //---------- Sprites
        game.batch.begin();
        
        //---- Corazones
        for(int i=1 ; i<4 ; i++){
        	if(i <= jugador.getVidas())
        		game.batch.draw(imagenVida, 350+i*30, 20);
        	else
        		game.batch.draw(imagenSinVida, 350+i*30, 20);
        }
        
        //---- Naves y aliens + disparos
        for(AbstractEntidad jugador : jugadoresArray){
        	jugador.draw(game.batch);

        }
		
		for(AbstractEntidad enemigo : enemigosArray){
			enemigo.draw(game.batch);
		}

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

	
	//-------------------------------------------------------
	//-------- Eliminación de archivos que se almacenan en la gráfica
	//-------------------------------------------------------
	@Override
	public void dispose() {
		game.dispose();
		
		//---- Texturas
		jugador.dispose();
		
		for(AbstractEntidad enemigo : enemigosArray){
			enemigo.dispose();
		}
		
		//---- Batch
		shapeRenderer.dispose();
				
		//---- Música y sonidos
		musicaFondo.dispose();
		disparoNaveSound.dispose();
		disparoBotSound.dispose();
		explosionBotSound.dispose();
		explosionNaveSound.dispose();		
	}

}
