package Baraja;

import java.util.Random;
import java.util.function.Consumer;

/**
 *
 * @author carlosmontoya
 */
public abstract class Baraja
{
    private final Carta[] Carta;
	private int Actual;
	private final int NumeroCartas;

	protected Baraja(int numCartas, Consumer<Carta[]> c)
	{
		this.Carta = new Carta[numCartas];
		this.NumeroCartas = numCartas;
		c.accept(Carta);
		this.Actual = 0;
	}
	
	public void desordenarCartas()
	{
		Random rnd = new Random();

		Carta[] cartaBarajada = new Carta[NumeroCartas];

		int indice = 0;
		boolean[] desordenada = new boolean[NumeroCartas];

		for (int i = 0; i < NumeroCartas; i++)
			desordenada[i] = false;

		for (int i = 0; i < NumeroCartas; i++)
		{
			do
				indice = (int) (rnd.nextDouble() * NumeroCartas);
			while (desordenada[indice]);

			cartaBarajada[i] = this.Carta[indice];
			desordenada[indice] = true;
		}

		System.arraycopy(cartaBarajada, 0, this.Carta, 0, NumeroCartas);

		this.Actual = 0;
	}
	
	public Carta sacarCarta()
	{
		if (Actual < NumeroCartas)
			return Carta[Actual++];
		else
			return null;
	}
	
	@Override
	public String toString()
	{
		StringBuilder stb = new StringBuilder();
		
		for (Carta c: Carta)
			stb.append(c).append("\n");
		
		return stb.toString();
	}
}
