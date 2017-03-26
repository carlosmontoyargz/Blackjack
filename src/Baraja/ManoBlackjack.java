package Baraja;

/**
 *
 * @author carlosmontoya
 */
public class ManoBlackjack extends Mano
{
	private int Suma;
	private boolean AsSumando11;
	private Carta PrimeraCarta;
	
	public ManoBlackjack(BarajaInglesa baraja)
	{
		super(baraja);
		this.Suma = 0;
		this.AsSumando11 = false;
		this.PrimeraCarta = null;
	}
	
	@Override
	public Carta tomarCarta()
	{
		Carta cartaTomada = super.tomarCarta();
		
		if (getNumeroCartas() == 1)
			PrimeraCarta = cartaTomada;
		
		if (cartaTomada.getNombre().equals("As"))
		{
			Suma += 11;
			AsSumando11 = true;
			
			if (Suma > 21)
				Suma -= 10;
		}
		else
		{
			Suma += cartaTomada.getValor();
			
			if (Suma > 21 && AsSumando11)
			{
				Suma -= 10;
				AsSumando11 = false;
			}
		}
		
		return cartaTomada;
	}
	
	@Override
	public void vaciarMano()
	{
		super.vaciarMano();
		this.Suma = 0;
		this.PrimeraCarta = null;
		this.AsSumando11 = false;
	}
	
	public int getSuma()
	{
		return this.Suma;
	}
	
	public Carta getPrimeraCarta()
	{
		return this.PrimeraCarta;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "Suma total: " + this.Suma;
	}
}

class TestManoBlackJack
{
	public static void main(String[] args)
	{
		BarajaInglesa baraja = new BarajaInglesa();
		baraja.desordenarCartas();
		
		ManoBlackjack mano = new ManoBlackjack(baraja);
		
		System.out.println("Baraja: \n" + baraja);
		
		mano.tomarCarta();
		mano.tomarCarta();
		mano.tomarCarta();
		mano.tomarCarta();
		
		System.out.println(mano);
	}
}
