package Baraja;

/**
 *
 * @author carlosmontoya
 */
public class BarajaInglesa extends Baraja
{
	public BarajaInglesa()
	{
		super(52, BarajaInglesa::generarCartas);
	}
	
	private static void generarCartas(Carta[] carta)
	{
		int valor;
		String palo;

		for (int i = 0; i < 52; i++)
		{
			// Define el palo de la carta
			if (i >= 39)	  palo = "Diamantes";
			else if (i >= 26) palo = "Tréboles";
			else if (i >= 13) palo = "Corazones";
			else			  palo = "Picas";

			// Define el nombre de la carta
			if (i % 13 == 0)	   carta[i] = new Carta(palo, "As", 1);
			else if (i % 13 == 10) carta[i] = new Carta(palo, "J", 10);
			else if (i % 13 == 11) carta[i] = new Carta(palo, "Q", 10);
			else if (i % 13 == 12) carta[i] = new Carta(palo, "K", 10);
			else
			{
				valor = (i % 13) + 1;
				carta[i] = new Carta(palo, String.valueOf(valor), valor);
			}
		}
	}
}
