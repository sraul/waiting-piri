package com.waitingpiri.util;

import java.util.ArrayList;
import java.util.List;

import com.waitingpiri.domain.Funcionario;
import com.waitingpiri.domain.Usuario;

public class DataUtil {

	/**
	 * @return datos de funcionarios..
	 */
	public static List<Funcionario> getFuncionariosData() {
		List<Funcionario> out = new ArrayList<Funcionario>();

		String[] nombres = new String[] { "Juan", "Luis", "Lida", "Geronimo",
				"Hipolito", "Violeta", "Damian", "Fabian", "Kike",
				"Dario Lezcano" };

		String[] apellidos = new String[] { "Perez", "Gimenez", "Herrera",
				"Rojas", "Juarez", "Ruiz", "Espinola", "Caceres", "Hernandez",
				"Lezcano" };

		String[] cedulas = new String[] { "3.500.200", "132.456", "1.369.874",
				"9.513.574", "6.314.785", "9.874.563", "789.562", "856.321",
				"6.321.457", "456.782" };

		String[] direcciones = new String[] { "Direccion1", "Direccion2",
				"Direccion3", "Direccion4", "Direccion5", "Direccion6",
				"Direccion7", "Direccion8", "Direccion9", "Direccion10" };

		String[] telefonos = new String[] { "Telefono1", "Telefono2",
				"Telefono3", "Telefono4", "Telefono5", "Telefono6",
				"Telefono7", "Telefono8", "Telefono9", "Telefono10" };
		Integer[] cargos = new Integer[] { Funcionario.ID_CARGO_GERENTE,
				Funcionario.ID_CARGO_CHOFER, Funcionario.ID_CARGO_AUXILIAR,
				Funcionario.ID_CARGO_CHOFER, Funcionario.ID_CARGO_CHOFER,
				Funcionario.ID_CARGO_CHOFER, Funcionario.ID_CARGO_AUXILIAR,
				Funcionario.ID_CARGO_CHOFER, Funcionario.ID_CARGO_CHOFER,
				Funcionario.ID_CARGO_CHOFER, Funcionario.ID_CARGO_CHOFER };

		for (int i = 0; i < 10; i++) {
			Funcionario func = new Funcionario(i + 1, nombres[i], apellidos[i],
					cedulas[i], direcciones[i], telefonos[i], cargos[i]);
			out.add(func);
		}

		return out;
	}

	/**
	 * @return los datos de usuarios..
	 */
	public static List<Usuario> getUsuariosData() {
		List<Usuario> out = new ArrayList<Usuario>();

		String[] nick = new String[] { "jesrazal", "Lizilla", "Gabybu",
				"SergioAce", "Kokito", "Lito", "noname", "wendy", "loli",
				"dudu" };

		String[] password = new String[] { "13254", "987541", "65495", "46584",
				"65485", "3219", "984325", "98435", "6314", "51889" };

		for (int i = 0; i < 10; i++) {
			Usuario usu = new Usuario(i + 1, nick[i], password[i]);
			out.add(usu);
		}

		return out;
	}
}
