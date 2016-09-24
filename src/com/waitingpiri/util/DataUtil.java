package com.waitingpiri.util;

import java.util.ArrayList;
import java.util.List;

import com.waitingpiri.domain.Cargo;
import com.waitingpiri.domain.Colectivo;
import com.waitingpiri.domain.Funcionario;
import com.waitingpiri.domain.Sugerencia;
import com.waitingpiri.domain.Usuario;

public class DataUtil {
	/**
	 * @return los datos de cargos..
	 */
	public static List<Cargo> getCargosData() {
		List<Cargo> out = new ArrayList<Cargo>();
		String[] descripcion = new String[] { "GERENTE", "AUXILIAR ADMINISTRATIVO", "CHOFER"};
		for (int i = 0; i < descripcion.length; i++) {
			Cargo car = new Cargo(i + 1, descripcion[i]);
			out.add(car);
		}
		return out;
	}

	/**
	 * @return datos de funcionarios..
	 */
	public static List<Funcionario> getFuncionariosData() {
		List<Funcionario> out = new ArrayList<Funcionario>();
		List<Cargo> cargos = DataUtil.getCargosData();

		String[] nombres = new String[] { "Juan", "Luis", "Lida", "Geronimo",
				"Hipolito", "Violeta", "Damian", "Fabian", "Kike",
				"Dario" };

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
	
		Cargo[] cargos_ = new Cargo[] { cargos.get(0), cargos.get(1), cargos.get(2), cargos.get(0), cargos.get(1),
				cargos.get(2), cargos.get(0), cargos.get(1), cargos.get(2), cargos.get(0) };

		for (int i = 0; i < 10; i++) {
			Funcionario func = new Funcionario(i + 1, nombres[i], apellidos[i],
					cedulas[i], direcciones[i], telefonos[i], cargos_[i]);
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

	/**
	 * @return los datos de colectivos..
	 */
	public static List<Colectivo> getColectivosData() {
		List<Colectivo> out = new ArrayList<Colectivo>();
		String[] nroColec = new String[] { "001", "025", "032", "040", "090",
				"067", "070", "125", "090", "110" };
		String[] nroChasis = new String[] { "D2262662", "142GE4AA",
				"79652F4S2", "9654F124", "19844RF6515", "96F4856E",
				"98AS4DF651", "98DSF974", "646ADS654", "DSGDF988651" };
		String[] nroChapa = new String[] { "AKA 047", "BOB 963", "LIZ 968",
				"GAB 425", "ANA 965", "JES 789", "GHJ 456", "ASD 456",
				"VBN 654", "SDF 987" };
		String[] imei=new String[]{"12365478941236587","12589633255","2326595230","789456123","1548310311","7894002232",
				"4512369874","78456311222","554557412","458255221"};
		
		for (int i = 0; i < 10; i++) {
			Colectivo col = new Colectivo(i + 1, nroColec[i], nroChasis[i],
					nroChapa[i],imei[i]);
			out.add(col);
		}
		return out;
	}

	/**
	 * @return los datos de sugerencias..
	 */
	public static List<Sugerencia> getSugerenciasData() {
		List<Sugerencia> out = new ArrayList<Sugerencia>();
		String[] fechas = new String[] { "01-09-2016", "02-09-2016", "03-09-2016" };
		String[] descripciones = new String[] { "Sugerencia 1", "Sugerencia 2", "Sugerencia 3" };
		
		for (int i = 0; i <= 2; i++) {
			Sugerencia sug = new Sugerencia(i + 1, fechas[i], descripciones[i]);
			out.add(sug);
		}
		return out;
	}
}
