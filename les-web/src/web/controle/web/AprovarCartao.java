package web.controle.web;

import java.util.Random;

import entities.venda.StatusCompra;

public class AprovarCartao {
	
	public static StatusCompra validarCompra() {
		Random r = new Random();
		Double d = r.nextDouble() * 100;
		if(d > 20)
			return StatusCompra.Aprovado;
		else
			return StatusCompra.Reprovado;
	}
}
