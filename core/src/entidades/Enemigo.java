package entidades;


public class Enemigo extends AbstractEntidad{

	//-------------------------------------------------------
	//-------- Atributos generales
	//-------------------------------------------------------
	private String direccionMovimiento;
	private int contadorMovimientoVert;
	private float contadorMovimientoHor;
	
	//-------------------------------------------------------
	//-------- Constructor
	//-------------------------------------------------------
	public Enemigo(String tipoNave) {
		super(tipoNave, 1);

		direccionMovimiento = "derecha";
		contadorMovimientoVert = 0;
		contadorMovimientoHor = 0;
	}

	//-------------------------------------------------------
	//-------- Metodos
	//-------------------------------------------------------
		
	//---- Disparos
	@Override
	public void disparar() {
		setContadorDisparar(tiempoRecarga);
		Disparo disparo = new Disparo(naveActual, getSprite().getX()+(getSprite().getWidth())/2-2, getSprite().getY(), "abajo");
		disparosArray.add(disparo);
		disparoSonido.play(0.25f);
	}


	@Override
	public void actualizarDisparos() {
		for(Disparo disparo : disparosArray){
			disparo.translateY(-10);
		}		
	}

	//---- Movimiento
	public void pararse(float delta) {		
		tiempoQuieto += delta;
		if(tiempoQuieto > 5){
			tiempoQuieto = 0;
			setEstado("moviendose");
		}
	}
	
	@Override
	public void moverse(float delta) {
		if(direccionMovimiento.equals("derecha") || direccionMovimiento.equals("izquierda")){	
			
			// Contador del movimiento en horizontal
			contadorMovimientoHor += naveActual.getRapidez() * delta;
			if(contadorMovimientoHor >= 81.75f){
				contadorMovimientoHor = 0;
				setEstado("quieto");
				pararse(delta);
			}
			
			//---- Derecha
			if(direccionMovimiento.equals("derecha")) { 
				getSprite().translateX(naveActual.getRapidez() * delta);
				if(getSprite().getX() >= 480-getSprite().getWidth()) {
					contadorMovimientoHor = 81.75f - contadorMovimientoHor;  // Para que recorra lo mismo una vez vuelva a moverse en horizontal (retorno a la columna)
					direccionMovimiento = "abajo";
				}
			}
			
			//---- Izquierda
			else if(direccionMovimiento.equals("izquierda")) {
				getSprite().translateX(-naveActual.getRapidez() * delta);
				if(getSprite().getX() <= 0) {
					contadorMovimientoHor = 81.75f - contadorMovimientoHor;  // Para que recorra lo mismo una vez vuelva a moverse en horizontal (retorno a la columna)
					direccionMovimiento = "abajo";
				}
			}
		}
		
		//---- Abajo
		else{
			// Contador del movimiento en vertical
			contadorMovimientoVert += 1;
			if(contadorMovimientoVert >= 40){
				contadorMovimientoVert = 0;
				
				if(getSprite().getX() <= 0) 
					direccionMovimiento = "derecha";
					
				else if(getSprite().getX() >= 480-getSprite().getWidth()) 
					direccionMovimiento = "izquierda";
			}
			
			getSprite().translateY(-naveActual.getRapidez() * delta);
		}
	}
}
