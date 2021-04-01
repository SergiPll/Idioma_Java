package Prueb;

import java.util.Scanner;

public class Matrices {

	public static void main(String[] args) {

		//En los bucles, "I" son filas y "J" columnas.
		
		Scanner sc = new Scanner(System.in);
		int escaner = 0;
		char jugador = 'O';
		char[][] m = new char[6][7];
		boolean ganar = false;
		int turnos = 1;
		
		//Iniciar los valores de la matriz
		for (int i = 0; i < m.length; i++){
			for (int j = 0; j < m[i].length; j++){
				m[i][j] = 0;
			}
		}
		
		
		System.out.println("- - - - - - - - - - Bienvenido al 4 en raya - - - - - - - - - -");
		System.out.println("Jugador 1 --> O \nJugador 2 --> X");
		System.out.println("- - - - - - - - - - - ");
		imprimir(m);

		while (!ganar && turnos <= 42) {
			ganar = ganador(jugador, m);
			if (ganar) {
				break;
			}
			/* 
			 * - Esta parte de codigo, este bucle de abajo, analizará 
			 *   si la jugada que se quiere realizar, se puede llevar a cabo.
			 * - Pedira una columna, y, si no se puede volverá a repetirse.
			*/ 
			boolean jugadaValida = false;
			while (!jugadaValida) {
				
				if (jugador == 'O') {
					System.out.print("\nJugador 1 ( O ) - Eliga columna 1-7  --> ");
				} else if (jugador == 'X') {
					System.out.print("\nJugador 2 ( X ) - Eliga columna 1-7  --> ");
				}

					escaner = sc.nextInt() - 1;
					if (escaner >= 0 && escaner <= 6) {
						jugadaValida = vacio(escaner, m);
					}

			}//Fin bucle de validar

			
			//Cambiar los 0 por el simbolo del jugador
			for (int i = m.length-1; i >= 0; i--) {
				if (m[i][escaner] == 0) {
					if (jugador == 'O') {
						m[i][escaner] = 'O';
						ganar = ganador(jugador, m);
					} else if (jugador == 'X') {
						m[i][escaner] = 'X';
						ganar = ganador(jugador, m);
					}
					
					
					if (jugador == 'O') {
						jugador = 'X';
					} else {
						jugador = 'O';
					}
					
					turnos++;
					break;
				}
			}
			
			
			imprimir(m);
			if (ganar) {
				if (jugador == 'O') {
					System.out.println("\nJugador 2 GANA");
					break;
				} else if (jugador == 'X') {
					System.out.println("\nJugador 1 GANA");
					break;
				}
			} else if (turnos >= 43){
				System.out.println("Empate!!!!");
				break;
			}
			
			
			
		}//Fin bucle de aplicacion

		sc.close();
	}

	
	public static boolean ganador(int jugador, char[][] m) {

		//Valida de arriba a abajo
		for (int i = 0; i < m.length - 3; i++) {
			for (int j = 0; j < m[i].length; j++) {
				if (m[i][j] == jugador && 
						m[i+1][j] == jugador && 
						m[i+2][j] == jugador && 
						m[i+3][j] == jugador) {
					return true;
				}
			}
		}
		
		//Valida de lado.
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length - 3; j++) {
				if (m[i][j] == jugador && 
						m[i][j+1] == jugador && 
						m[i][j+2] == jugador && 
						m[i][j+3] == jugador) {
					return true;
				}
			}
		}
		
		//Valida diagonal arriba
		for (int i = 3; i < m.length; i++) {
			for (int j = 0; j < m[i].length - 3; j++) {
				if (m[i][j] == jugador && 
						m[i-1][j+1] == jugador && 
						m[i-2][j+2] == jugador && 
						m[i-3][j+3] == jugador) { 
					return true;
				}
			}
		}
		
		//Valida diagonal abajo
		for (int i = 0; i < m.length - 3; i++) {
			for (int j = 0; j < m[i].length - 3; j++) {
				if (m[i][j] == jugador && 
						m[i+1][j+1] == jugador && 
						m[i+2][j+2] == jugador && 
						m[i+3][j+3] == jugador) {
					return true;
				}
			}
		}
		
		
		
		return false;
	}
	
	public static boolean vacio(int escaner, char[][] m) {
		
		//Revisa si la columna es valida
		if (escaner > m[0].length || escaner < 0) {
			return true;
		}
		
		//Revisa si la columna esta llena
		if (m[0][escaner] != ' ') {
			return true;
		}
		
		return false;
	}
	
	public static void imprimir(char[][] m) {
		System.out.println("\n|     |     |     |     |     |     |     |");
		for (int i = 0; i < m.length; i++) {

			for (int j = 0; j < m[0].length; j++) {

				if (m[i][j] == 0) {
					System.out.printf("│%3s  ", " ");
				}
				else {					
					if (m[i][j] == 'X') {
						System.out.printf("│%3s  ", m[i][j]);
					}
					else {
						System.out.printf("│%3s  ", m[i][j]);
					}
				}				
			}

			if (i != m.length-1) {
				System.out.println("│\n├─────┼─────┼─────┼─────┼─────┼─────┼─────┤");	
			}			
		}	

		System.out.println("│\n└─────┴─────┴─────┴─────┴─────┴─────┴─────┘");
		System.out.println("   1     2     3     4     5     6     7   ");
		
		}
	
}