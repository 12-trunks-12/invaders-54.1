package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import naves.*;


public abstract class AbstractEntidad {
	
	protected String estadoEntidad;  // Quieto, golpeado, moviendose, moviendose-golpeado, muriendo, muerto
	
	protected int vida;
	protected int vidas;
	
	protected AbstractNave naveActual;
	protected Sprite spriteActual;
	
	protected Array<Disparo> disparosArray = new Array<Disparo>();
	
	protected float tiempoRecarga;  // Tiempo que necesita de recarga
	protected float contadorDisparar;  // Tiempo que necesita esperar para volver a disparar
	protected float tiempoQuieto;
	protected float tiempoMovimiento;
	protected float tiempoGolpeado;
	protected float tiempoMuriendo;
	protected float tiempoAnimacionMovimiento;
	protected float tiempoAnimacionMuerte;
	
	protected Sound disparoSonido;
	protected Music explosionSonido;  // Es Music para comprobar si esta sonando
	
	//-------------------------------------------------------
	// ------- Constructor
	//-------------------------------------------------------
	public AbstractEntidad(String tipoNave, int vidas){
		estadoEntidad = "quieto";
		
		switch(tipoNave){
		case "Default": this.naveActual = new DefaultNave(); break;
		case "BigBoy": this.naveActual = new BigBoyNave(); break;
		case "Leonov": this.naveActual = new LeonovNave(); break;
		case "Goliath": this.naveActual = new GoliathNave(); break;
		
		case "Enemigo1": this.naveActual = new Enemigo1Nave(); break;
		}

		spriteActual = new Sprite(naveActual.getSprites()[0][0]);
		
		vida = naveActual.getVida();
		this.vidas = vidas;
		
		tiempoRecarga = naveActual.getRapidez()/600;
		contadorDisparar = 0;
		tiempoQuieto = 0;
		tiempoMovimiento = 0;
		tiempoGolpeado = 0;
		tiempoMuriendo = 0;
		tiempoAnimacionMovimiento = 0;
		tiempoAnimacionMuerte = 0;
		
		disparoSonido = Gdx.audio.newSound(Gdx.files.internal("sounds/disparoSound.wav"));
		explosionSonido = Gdx.audio.newMusic(Gdx.files.internal("sounds/explosionSound.wav"));
		explosionSonido.setVolume(0.6f);
		
	}
	
	//-------------------------------------------------------
	// ------- Getters y setters
	//-------------------------------------------------------

	//---- Estado
	public void setEstado(String estado){
		if(!getEstado().equals("muerto")){  // Solo puede cambiar a otro estado cuando está vivo
			estadoEntidad = estado;
		}
	}  
	public String getEstado(){
		return estadoEntidad;}
	
	//---- Vida
	public void setVida(int vida){
		this.vida = vida;
	}
	public int getVida(){
		return vida;
		}
	
	public void setVidas(int vidas){
		this.vidas = vidas;
	}
	public int getVidas(){
		return vidas;
	}
	
	//---- Sprite
	public void setSprite(Sprite sprite){
		spriteActual = new Sprite(sprite);
	}
	public Sprite getSprite(){
		return spriteActual;
	}
	
	//---- Disparos
	public void setContadorDisparar(float tiempo){
		contadorDisparar = tiempo;
	}
	public float getContadorDisparar(){
		return contadorDisparar;
	}
	public Array<Disparo> getDisparosArray(){
		return disparosArray;
	}
	
	//-------------------------------------------------------
	// ------- Metodos
	//-------------------------------------------------------
	
	//---- Disparos
	public boolean canDisparar(float delta){
		if(getContadorDisparar() <= 0){
			return true;
		}
		
		else{
			contadorDisparar -= delta;
			return false;
		}
	}
	
	public abstract void disparar();
	public abstract void actualizarDisparos();
	
	//---- Movimiento
	public abstract void pararse(float delta);
	public abstract void moverse(float delta);
	
	//---- Vivo o muerto
	public boolean isMuerto(){
		if(getEstado().equals("muerto"))
			return true;
		
		return false;
	}
	public void morir(float delta){
		tiempoMuriendo += delta; 
		
		Sprite nuevoSprite = new Sprite(naveActual.animarMuerte(delta));  // Se crea el nuevo sprite con la textura actual de la animación
		nuevoSprite.setPosition(getSprite().getX(), getSprite().getY());   // Ponemos las coordenadas del antiguo sprite al nuevo sprite
		setSprite(nuevoSprite);	
		
		if(naveActual.getAnimacionMuerte().isAnimationFinished(tiempoMuriendo)){
			setEstado("muerto");
		}
	}
	
	public void revivir(){
		estadoEntidad = "quieto";
		tiempoMuriendo = 0;
		setVida(naveActual.getVida());
		spriteActual.setPosition((480-spriteActual.getWidth())/2, 50);
	}
	
	
	//---- Asignar sprite
	public void actualizarSprite(float delta){
		Sprite spriteViejo = getSprite();
		
		//- Quieto
		if(getEstado().equals("quieto")){
			tiempoQuieto += delta;
			setSprite(new Sprite(naveActual.getSprites()[0][0]));	
		}
				
		//- Golpeado
		else if(getEstado().equals("golpeado")){
			tiempoGolpeado += delta;
			if(tiempoGolpeado > 0.1f){
				tiempoGolpeado = 0;
				setEstado("quieto");
			}	
			
			setSprite(new Sprite(naveActual.getSprites()[0][1]));
		}
				
				
		//- Moviendose/moviendose-golpeado
		else if(getEstado().equals("moviendose") || getEstado().equals("moviendose-golpeado")){
			tiempoMovimiento += delta;
			
			if(getEstado().equals("moviendose")){
				if(tiempoMovimiento >= 0.48f){
					tiempoMovimiento = 0;
				}
				
				setSprite(new Sprite(naveActual.animarMovimiento(tiempoMovimiento)));
			}
			
					
			else{
				tiempoGolpeado += delta;
				if(tiempoGolpeado > 0.1f){
					tiempoGolpeado = 0;
					setEstado("moviendose");
				}
				setSprite(new Sprite(naveActual.animarMovimiento(tiempoMovimiento + 0.48f)));
			}
		}
				
		//- Muriendo
		else if(getEstado().equals("muriendo") && vidas < 1){	
			tiempoMuriendo += delta;
			if(naveActual.getAnimacionMuerte().isAnimationFinished(tiempoMuriendo))
				setEstado("muerto");
			if(!explosionSonido.isPlaying())
				explosionSonido.play();
			setSprite(new Sprite(naveActual.animarMuerte(tiempoMuriendo)));
		}
			
		//- Muerto
		else{
			setSprite(new Sprite(naveActual.getSprites()[0][2]));
		}
		
		// Asignamos la posicion en la que estaba
		getSprite().setPosition(spriteViejo.getX(), spriteViejo.getY());
	}
	
	
	//---- Dibujar en pantalla
	public void draw(SpriteBatch batch){
		getSprite().draw(batch);
		for(Sprite disparo : disparosArray){
			disparo.draw(batch);
		}
	}
	
	//---- Dispose
	public void dispose(){
		naveActual.dispose();
	}
}


/*

*/