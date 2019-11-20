import java.util.Map;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class Principal {

	public static void main(String[] args) {
		String reportSource = "./plantilla/plantilla.jrxml";
		String reportHTML = "./informes/Informe.html";
		String reportPDF = "./informes/Informe.pdf";
		String reportXML = "./informes/Informe.xml";
        //Los parámetros tienen que crearse y almacenarse en un HashMap, ese Map se usa para crear el JasperPrint añadiendo parámetros 
		//Declaramos tres parámetros y los guardo en param
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("titulo", "PRACTICA JASPER");
		params.put("autor", "ANTHONY MOSCOSOS");
		params.put("fecha", (new java.util.Date()).toString());

		try {
			//compilamos  la plantilla y obtenemos el objeto JasperReport
			JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
			Class.forName("com.mysql.jdbc.Driver");
			String usuario="root";
			String password="1234";
			String bd="Ejercicio2";
			java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+bd+"?userTimezone=true&serverTimezone=UTC",usuario ,password );
			//Connection conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/"+bd+"?userTimezone=true&serverTimezone=UTC",usuario ,password );
			//Rellenamos datos del informe con el método fillReport, generando un fichero .jrprint
			//Se necesitará el nombre del objeto JasperReport, los parámetros del informe y la conexión a bd
			JasperPrint MiInforme = JasperFillManager.fillReport(jasperReport, params, conn);
			//Podremos exportar el fichero JasperPrint, en el MiInforme generado anteriormente al formato que desee
			// Visualizar en pantalla
			JasperViewer.viewReport(MiInforme);
			// Convertir a HTML
			JasperExportManager.exportReportToHtmlFile(MiInforme, reportHTML);
			// Convertir a PDF
			JasperExportManager.exportReportToPdfFile(MiInforme, reportPDF);
			// Convertir a XML, false es para indicar que no hay im�genes
			// (isEmbeddingImages)
			JasperExportManager.exportReportToXmlFile(MiInforme, reportXML, false);
			System.out.println("ARCHIVOS CREADOS");
		} catch (CommunicationsException c) {
			System.out.println(" Error de comunicaci�n con la BD. No est� arrancada.");
		} catch (ClassNotFoundException e) {
			System.out.println(" Error driver. ");
		} catch (SQLException e) {
			System.out.println(" Error al ejecutar sentencia SQL ");
		} catch (JRException ex) {
			System.out.println(" Error Jasper.");
			ex.printStackTrace();
		}
	}

}
