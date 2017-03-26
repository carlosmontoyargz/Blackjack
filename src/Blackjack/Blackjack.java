package Blackjack;

import Baraja.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 *
 * @author carlosmontoya
 */
public class Blackjack
{

	//Datos de la partida
	private double Saldo;
	private double Apuesta;
	private final double ApuestaMinima = 100;

	//Cartas y manos del juego
	private final BarajaInglesa Baraja = new BarajaInglesa();
	private final ManoBlackjack ManoJugador = new ManoBlackjack(Baraja);
	private final ManoBlackjack ManoCroupier = new ManoBlackjack(Baraja);

	//Objetos para operaciones
	private final DecimalFormat fmt = new DecimalFormat("#########.##");
	private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public Blackjack()
	{
		this.cargarSaldo();
		this.Apuesta = 0;
	}

	private void cargarSaldo()
	{
		try
		{
			BufferedReader b = new BufferedReader(new FileReader("saldo.txt"));
			String cd;
			
			while ((cd = b.readLine()) != null)
				this.Saldo = Double.parseDouble(cd);
			
			if (this.Saldo < this.ApuestaMinima)
				this.Saldo = 10000;
		}
		catch (NumberFormatException | IOException e)
		{
			this.Saldo = 10000;
		}
	}

	private void ingresarApuesta()
	{
		double apuesta = this.ApuestaMinima;
		boolean error;
		
		do
		{
			try
			{
				System.out.print("\nIngrese la apuesta deseada: $");
				apuesta = Double.parseDouble(in.readLine());

				if (apuesta < this.ApuestaMinima || apuesta > this.Saldo)
				{
					System.err.println("La apuesta no es valida.");
					error = true;
				} else
					error = false;

			}
			catch (NumberFormatException e)
			{
				System.err.println("No se ha ingresado un valor numérico.");
				error = true;

			}
			catch (IOException e)
			{
				System.err.println("Error en la lectura de la apuesta.");
				error = true;
			}
		}
		while (error);

		this.Apuesta = Double.parseDouble(fmt.format(apuesta));
		this.Saldo -= this.Apuesta;

		System.out.println("La apuesta ha sido guardada.\n");
	}

	private void repartirCartasIniciales()
	{

		Baraja.desordenarCartas();

		ManoJugador.tomarCarta();
		ManoJugador.tomarCarta();
		ManoCroupier.tomarCarta();
		ManoCroupier.tomarCarta();
	}

	private boolean blackjack()
	{
		boolean isBlackjack = (ManoJugador.getNumeroCartas() == 2 && ManoJugador.getSuma() == 21);

		if (isBlackjack)
			Saldo += Apuesta * 2.5;

		return isBlackjack;
	}

	private boolean doblarApuesta()
	{
		if (this.Saldo >= this.Apuesta)
		{

			Saldo -= Apuesta;
			Apuesta *= 2;

			ManoJugador.tomarCarta();

			return true;
		}
		else
			return false;
	}

	private void preguntarDoblar()
	{

		System.out.println("\n-----------------------------------------------------------");
		System.out.println("\n-----------------------------------------------------------\n");

		System.out.println("Ingresar acción:\n\n\tP) Pedir carta\n\tQ) Quedarse\n\tD) Doblar apuesta\n");
		String accionTrasRepartir;
		try
		{
			accionTrasRepartir = this.in.readLine();
		} catch (IOException ex)
		{
			accionTrasRepartir = "q";
		}

		switch (accionTrasRepartir)
		{
			case "P":
			case "p":
				this.pedirCartaJugador("P");

				if (this.ManoJugador.getSuma() <= 21)
					this.repartirCartasCroupier();

				break;

			case "Q":
			case "q":
				this.repartirCartasCroupier();
				break;

			case "D":
			case "d":
				this.doblarApuesta();
				this.repartirCartasCroupier();
				break;

			default:
				this.repartirCartasCroupier();
		}

		System.out.println();
	}

	private void pedirCartaJugador(String pedirCarta)
	{
		if ("P".equals(pedirCarta) || "p".equals(pedirCarta))
		{

			this.ManoJugador.tomarCarta();

			if (this.ManoJugador.getSuma() < 21 && this.ManoJugador.getNumeroCartas() < 7)
			{
				System.out.println();
				mostrarCartas();

				System.out.println("\n-----------------------------------------------------------");
				System.out.println("\n-----------------------------------------------------------\n");
				System.out.println("Ingresar acción:\n\n\tP) Pedir carta\n\tQ) Quedarse");
				try
				{
					pedirCarta = in.readLine();
				} catch (IOException e)
				{
					pedirCarta = "q";
				}

				pedirCartaJugador(pedirCarta);
			}
		}

		System.out.println();
	}

	/**
	 * Reparte las cartas del croupier. Este método reparte las cartas del
	 * croupier una vez que el jugador ha dejado de pedirlas. El croupier está
	 * obligado a pedir carta siempre que la suma de las que tiene sea menor a
	 * 17.
	 *
	 */
	private void repartirCartasCroupier()
	{
		while (this.ManoCroupier.getSuma() < 17 && this.ManoCroupier.getNumeroCartas() < 7)
			this.ManoCroupier.tomarCarta();
	}

	/**
	 * Muestra en pantalla el saldo disponible y la apuesta minima.
	 *
	 */
	private void mostrarInformacionJuego()
	{

		System.out.println("-----------------------------------------------------------");
		System.out.println("       Tu saldo: $" + fmt.format(this.Saldo) + "    Apuesta mínima: $" + fmt.format(this.ApuestaMinima));
		System.out.println("-----------------------------------------------------------");
	}

	/**
	 * Muestra en pantalla la apuesta hecha y el saldo disponible.
	 *
	 */
	private void mostrarApuesta()
	{

		System.out.println("-----------------------------------------------------------");
		System.out.println("       Tu saldo: $" + fmt.format(this.Saldo) + "    Tu apuesta: $" + fmt.format(this.Apuesta));
		System.out.println("-----------------------------------------------------------");
	}

	/**
	 * Muestra las cartas durante el transcurso del juego.
	 *
	 */
	private void mostrarCartas()
	{
		//Muestra el saldo actual y la apuesta realizada
		mostrarApuesta();
		
		System.out.println("\nMano del jugador:\n" + ManoJugador + "\n");
		System.out.println("Carta del croupier:\n" + ManoCroupier.getPrimeraCarta());
	}

	private void mostrarCartasFinales()
	{
		//Muestra el saldo actual y la apuesta realizada
		mostrarApuesta();

		System.out.println("Cartas del jugador:\n" + ManoJugador + "\n");
		System.out.println("Cartas del croupier:\n" + ManoCroupier + "\n");

		System.out.println("-----------------------------------------------------------\n");
		System.out.println("-----------------------------------------------------------\n");
	}

	private void mostrarBlackjack()
	{
		System.out.println("¡Blackjack! Has ganado $" + fmt.format(this.Apuesta * 2.5));

		this.mostrarNuevoSaldo();
	}

	/**
	 * Muestra el nuevo saldo una vez terminado el juego.
	 *
	 */
	private void mostrarNuevoSaldo()
	{

		System.out.println("\n-----------------------------------------------------------");
		System.out.println("       Tu nuevo saldo: $" + fmt.format(this.Saldo));
		System.out.println("-----------------------------------------------------------\n");
	}

	/**
	 * Evalúa al ganador del juego. Este método evalúa al ganador del juego una
	 * vez que se han terminado de repartir cartas al jugador y al croupier, y
	 * devuelve una cadena con la información de lo que se realizó.
	 *
	 * @return El <code>String</code> con la informacion del ganador.
	 */
	private String evaluarGanador()
	{
		String msg;

		if (this.ManoJugador.getSuma() > 21)
			msg = "Te has pasado de 21. Gana el croupier. No se ha ganado nada.";

		else if (this.ManoJugador.getSuma() == this.ManoCroupier.getSuma())
			if (this.ManoCroupier.getSuma() == 21 && this.ManoCroupier.getNumeroCartas() == 2)
				msg = "El croupier ha hecho blackjack. No se ha ganado nada.";

			else
			{
				msg = "Empate. Se recupera la apuesta inicial de $" + fmt.format(this.Apuesta) + ".";
				this.Saldo += this.Apuesta;
			}
		else if (this.ManoJugador.getSuma() > this.ManoCroupier.getSuma() || this.ManoCroupier.getSuma() > 21)
		{
			if (this.ManoCroupier.getSuma() > 21)
				msg = "El croupier se ha pasado de 21. Gana el jugador. La ganancia es de $" + fmt.format(this.Apuesta * 2) + ".";

			else
				msg = "Gana el jugador. La ganancia es de $" + fmt.format(this.Apuesta * 2) + ".";

			this.Saldo += this.Apuesta * 2;

		} else
			msg = "Gana el croupier. No se ha ganado nada.";

		return msg;
	}

	private String preguntarApostarNuevamente()
	{
		String apostarNuevamente;

		if (this.Saldo >= this.ApuestaMinima)
		{
			System.out.println("¿Desea apostar nuevamente (S/N)?");
			try
			{
				apostarNuevamente = in.readLine();
			} catch (IOException ex)
			{
				apostarNuevamente = "n";
			}
		} else
		{
			System.out.println("No tienes más dinero por apostar. Se ha terminado la partida.");
			apostarNuevamente = "n";
		}
		System.out.println();

		return apostarNuevamente;
	}

	private void reiniciarDatos()
	{

		Apuesta = 0;
		ManoJugador.vaciarMano();
		ManoCroupier.vaciarMano();
	}

	private void guardarSaldo()
	{
		try (PrintWriter objGuardar = new PrintWriter(new FileWriter("saldo.txt")))
		{
			objGuardar.print(this.Saldo);
		}
		catch (IOException e)
		{
			System.out.println("\nError en el guardado de los datos");
		}
	}

	public void jugar()
	{
		String apostarNuevamente = "S";

		this.cargarSaldo();

        //Comienza el juego y se reinicia hasta que el usuario lo decida, o
		//hasta que el saldo sea menor a la apuesta mínima.
		while (("S".equals(apostarNuevamente) || "s".equals(apostarNuevamente)) && this.Saldo >= this.ApuestaMinima)
		{
			this.mostrarInformacionJuego(); //Muestra los datos del jugador
			this.ingresarApuesta();         //Comienza el turno guardando la apuesta
			this.repartirCartasIniciales(); //Desordena las cartas y reparte las primeras del juego
			this.mostrarCartas();           //Muestra la informacion de las primeras cartas repartidas

			if (this.blackjack())  //En el caso de hacer blackjack, se gana la partida y se termina el turno
				this.mostrarBlackjack();

			else
			{ //Si no se hace blackjack, se continua el juego
				this.preguntarDoblar();

				this.mostrarCartasFinales();
				System.out.println(evaluarGanador());

				this.mostrarNuevoSaldo();
			}

			apostarNuevamente = this.preguntarApostarNuevamente();

			this.reiniciarDatos();
			this.guardarSaldo();
		}
	}
}
