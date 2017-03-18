package testeSistemaCobranca;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TesteUnitario {

	private Calculadora calculadora;
	
	@Before
	public void inicializa(){
		this.calculadora = new Calculadora();
	}
	
	@Test
	public void verificarSoma() throws Exception{		
		assertEquals(5, calculadora.somar(2, 3), 0.05);
		assertEquals(10, calculadora.somar(5.2225, 4.7775), 0.05);		
	}
	
	@Test
	public void verificarSubtracao() throws Exception{		
		assertTrue(calculadora.subtrair(7, 3) == 4);
		assertTrue(calculadora.subtrair(5.25, 3.75) == 1.5);		
	}
	
	@Test
	public void verificarMultiplicacao() throws Exception{		
		assertEquals(150, calculadora.multiplicar(15, 10), 0.05);
		assertEquals(8.4375, calculadora.multiplicar(2.25, 3.75), 0.05);		
	}
	
	@Test
	public void verificarDivisao() {
		assertFalse(calculadora.dividir(6, 2) == 2);
		assertEquals(3.115, calculadora.dividir(15.575, 5),0.001);		
	}	
}
