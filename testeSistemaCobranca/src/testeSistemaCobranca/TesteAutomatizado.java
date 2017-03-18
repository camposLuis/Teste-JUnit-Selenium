package testeSistemaCobranca;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TesteAutomatizado {

	private FirefoxDriver driver;
	private File src;	
	
	@Before
	public void inicializa(){
		
		System.setProperty("webdriver.gecko.driver", "D:\\IGTI\\Projeto\\geckodriver.exe");
		this.driver = new FirefoxDriver();
		driver.get("http://localhost:8080/cobranca/titulos/novo");
		this.src = new File("D:\\Eclipse\\testeSistemaCobranca\\util\\PlanilhaTesteAutomatizado.xlsx");
	}
	
	@Test
	public void verificaCadastroDeTitulo() throws InterruptedException, IOException{		
				
		FileInputStream fis = new FileInputStream(src);		
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ts = wb.getSheetAt(0);
		
		DecimalFormat formato = new DecimalFormat("#.00");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");		

		int linha = Integer.parseInt(String.valueOf(ts.getRow(0).getCell(1)
				.getNumericCellValue()).replace(".0",""));
		int inicio = Integer.parseInt(String.valueOf(ts.getRow(0).getCell(3)
				.getNumericCellValue()).replace(".0",""));
		
		for(int i = inicio; i <= linha; i++){				
			
			WebElement txtDescricao = driver.findElement(By.name("descricao"));
			WebElement txtDataVencimento = driver.findElement(By.name("dataVencimento"));
			WebElement txtValor = driver.findElement(By.name("valor"));
			Select comboStatus = new Select(driver.findElement(By.name("status")));
			
			txtDescricao.sendKeys(ts.getRow(i).getCell(0).getStringCellValue());					
			txtDataVencimento.sendKeys(dateFormat.format(ts.getRow(i).getCell(1).getDateCellValue()));			
			txtValor.sendKeys(formato
					.format(ts.getRow(i).getCell(2).getNumericCellValue()).toString());
			comboStatus.selectByVisibleText(ts.getRow(i).getCell(3).getStringCellValue());		
			txtDescricao.submit();
			
			Thread.sleep(800);			
			driver.get("http://localhost:8080/cobranca");
			
			assertTrue(driver.getPageSource().contains(ts.getRow(i).getCell(0).getStringCellValue())
					&& driver.getPageSource().contains(dateFormat.format(ts.getRow(i).getCell(1)
							.getDateCellValue()))
					&& driver.getPageSource().contains(formato
							.format(ts.getRow(i).getCell(2).getNumericCellValue()).toString())
					&& driver.getPageSource().contains(ts.getRow(i).getCell(3).getStringCellValue()));
			
			driver.get("http://localhost:8080/cobranca/titulos/novo");						
		}
		wb.close();
	}
	
	@After	
	public void finaliza(){
		driver.quit();
	}	
}




