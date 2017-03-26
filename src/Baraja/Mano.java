package Baraja;

import java.util.ArrayList;

/**
 *
 * @author carlosmontoya
 */
public abstract class Mano
{
	private final Baraja Baraja;
	private final ArrayList<Carta> Mano = new ArrayList<>();
	
	public Mano(Baraja baraja)
	{
		this.Baraja = baraja;
		this.Mano.ensureCapacity(5);
	}
	
	public Carta tomarCarta()
	{
		Carta cartaTomada = this.Baraja.sacarCarta();
		Mano.add(cartaTomada);
		
		return cartaTomada;
	}
	
	@Override
	public String toString()
	{
		StringBuilder stb = new StringBuilder();
		
		Mano.stream().forEach((Carta carta) -> stb.append(carta).append("\n"));
		
		return stb.toString();
	}

	public void vaciarMano()
	{
		Mano.clear();
	}

	public int getNumeroCartas()
	{
		return Mano.size();
	}
}
