package Baraja;

/**
 *
 * @author carlosmontoya
 */
public class Carta
{
	private final String Nombre;
	private final String Palo;
	private final int Valor;
	
	/**
	 * Construye una carta con los parámetros especificados.
	 * @param nombre  El número en la carta
	 * @param palo  El palo de la carta
	 * @param valor  El valor de la carta
	 */
	public Carta(String palo, String nombre, int valor)
	{
		this.Nombre = nombre;
		this.Palo = palo;
		this.Valor = valor;
	}

	public String getNombre()
	{
		return this.Nombre;
	}

	public String getPalo()
	{
		return this.Palo;
	}

	public int getValor()
	{
		return this.Valor;
	}

	@Override
	public String toString()
	{
		return this.Nombre + " " + this.Palo;
	}
}
