	import java.io.File;
	import java.io.FileNotFoundException;
	import java.util.Arrays;
	import java.util.Scanner;


	public class SudokuResolve {

		static int calls = 0;
		int[][] sudoku = new int[9][9];

		private boolean lleno() {
			for(int fila = 0; fila < 9; fila++)
				for(int columna = 0; columna < 9; columna++)
					if(this.sudoku[fila][columna] == 0)
						return false;

			return true;
		}

		private boolean revisaFila(int valor, int fila) {

			int busque = 0;

			for(int i = 0; i < 9; i++) {
				if(sudoku[fila][i] == valor) {
					busque++;
				}
			}

			if(busque > 0)return false;
			else return true;
		}

		private boolean revisacolumnaumnas(int valor, int columna) {

			int busque = 0;

			for(int i = 0; i < 9; i++) {
				if(sudoku[i][columna] == valor) {
					busque++;
				}
			}

			if(busque > 0) return false;
			else return true;
		}

		private boolean checkGrid(int valor, int fila, int columna) {
			int x = fila - fila%3;
			int y = columna - columna%3;

			for(int i = 0; i < 3 && x + i < 9; i++) {
				for(int j = 0; j < 3 && y+j < 9; j++) {
					if(sudoku[x+i][y+j] == valor)
						return false;
				}
			}

			return true;
		}

		private boolean solucion(int fila, int columna) {


			// imprime el número actual de llamadas a esta función
			//System.out.println(++calls);

			if(lleno()) {
				return true;
			}

			if(sudoku[fila][columna] != 0) {
				if(columna + 1 > 8)
					return solucion(fila+1, 0);
				else
					return solucion(fila, columna+1);
			} else {

				for(int number = 1; number < 10; number++) {

					if(revisaFila(number, fila) && revisacolumnaumnas(number,columna) && checkGrid(number, fila, columna)) {
						sudoku[fila][columna] = number;

						// print grid every time a guess is taken, including final solution
						// for (int[] r : sudoku)
						// {
						//     System.out.println(Arrays.toString(r));
						// }

						if(columna + 1 > 8) {
							if(solucion(fila+1, 0))
								return true;
							else continue;
						} else {
							if (solucion(fila, columna+1))
								return true;
							else continue;
						}
					}
				}

				sudoku[fila][columna] = 0;
				return false;
			}
		}

		private boolean populate_array(int[][] sudoku) {

			try {

				int number = 0;

				File file = new File("./src/Sudokutest.txt");
				Scanner scanner = new Scanner(file);

				for(int fila = 0; fila < 9; fila++) {
					for(int columna = 0; columna < 9; columna++) {

						if(!scanner.hasNextInt())
							return false;

						number = scanner.nextInt();
						sudoku[fila][columna] = number;

					}
				}

			} catch (FileNotFoundException e) {
				System.out.println("no se pudo abrir el archivo" + e);
				return false;
			}

			return true;
		}

		public static void main(String[] args) {

			SudokuResolve ss = new SudokuResolve();

			if(!ss.populate_array(ss.sudoku)) {
				System.exit(0);
			}

			System.out.println("Sudoku:");
			for (int[] r : ss.sudoku)
			{
				System.out.println(Arrays.toString(r));
			}

			if(ss.solucion(0, 0)) {

				System.out.println("\nSOLUCIONADO!!");

				// imprimir solución
				for (int[] r : ss.sudoku)
				{
					System.out.println(Arrays.toString(r));
				}
			} else {
				System.out.println("\nNo hay solucion :(\nSUDOKUS POSIBLES PROFE!!!.");
			}
		}
	}