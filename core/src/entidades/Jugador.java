package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Jugador extends AbstractEntidad{
	
	//-------------------------------------------------------
	//-------- Constructor
	//-------------------------------------------------------
	public Jugador(String tipoNave) {
		super(tipoNave, 3);
	}

	//-------------------------------------------------------
	//-------- Metodos
	//-------------------------------------------------------
	
	@Override
	public void disparar() {
		disparoSonido.play(0.25f);
		setContadorDisparar(tiempoRecarga);
		
		Disparo disparo1 = new Disparo(naveActual, spriteActual.getX()+6, spriteActual.getY()+30, "arriba");  
		Disparo disparo2 = new Disparo(naveActual, spriteActual.getX()+spriteActual.getWidth()-10, spriteActual.getY()+30, "arriba"); 				
				
		disparosArray.add(disparo1);
		disparosArray.add(disparo2);
	}


	@Override
	public void actualizarDisparos() {
		for(Disparo disparo : disparosArray){
			disparo.translateY(15);
		}
		
	}
	
	@Override 
	public void pararse(float delta){
	}
	
	@Override
	public void moverse(float tiempo) {
		//---- Control de eventos
		if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT) || Gdx.input.isKeyPressed(Keys.DPAD_LEFT)){
			if(!getEstado().equals("moviendose-golpeado"))
				setEstado("moviendose");
		
			if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)){  // Tecla derecha
				getSprite().translateX(naveActual.getRapidez() * tiempo);
				
				if(getSprite().getX() > (480-getSprite().getWidth()))  // Si se sale de la pantalla
					getSprite().setX(480-getSprite().getWidth());
			}
				
			else if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)){  // Tecla izquierda
				getSprite().translateX(-naveActual.getRapidez() * tiempo);
				
				if(getSprite().getX() < 0)  // Si se sale de la pantalla
					getSprite().setX(0);
			}
		}
		
		else if(!Gdx.input.isKeyPressed(Keys.DPAD_RIGHT) && !Gdx.input.isKeyPressed(Keys.DPAD_LEFT)){  // No se mueve
			tiempoMovimiento = 0;
			setEstado("quieto");
		}
	}
}

