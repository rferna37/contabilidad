package as.fleming.rodrigo.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.core.io.Resource;

import as.fleming.rodrigo.entidades.Apunte;
import as.fleming.rodrigo.entidades.Concepto;
import as.fleming.rodrigo.entidades.Cuenta;
import as.fleming.rodrigo.excepciones.GrabarFicheroException;

public interface DAOService {
	public Apunte grabar(Apunte apunte) throws GrabarFicheroException;
	public void eliminar(String id);
	public Apunte leerApunte(String id);
	public List<Apunte> leerApuntesPorFecha(LocalDate fDesde, LocalDate fHasta);
	public List<Apunte> leerApuntesFiltro(String origen,
			                              String destino,
			                              String concepto,
			                              LocalDate fDesde, 
			                              LocalDate fHasta);
	public Resource loadDocumento(String filename);
	
	public List<Concepto> leerConceptos();
	public Concepto grabarConcepto(Concepto concepto);
	public void eliminarConcepto(String codigo);
	public boolean existeConcepto(String codigo);
	
	public List<Cuenta> leerCuentas();
	public Cuenta grabarCuenta(Cuenta cuenta);
	public void eliminarCuenta(String codigo);
	public boolean existeCuenta(String cuenta);

}
