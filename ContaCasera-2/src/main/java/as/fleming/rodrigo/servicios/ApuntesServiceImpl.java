package as.fleming.rodrigo.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import as.fleming.rodrigo.datos.RepositorioConcepto;
import as.fleming.rodrigo.datos.RepositorioConta;
import as.fleming.rodrigo.datos.RepositorioCuenta;
import as.fleming.rodrigo.entidades.Apunte;
import as.fleming.rodrigo.entidades.Concepto;
import as.fleming.rodrigo.entidades.Cuenta;

@Service
public class ApuntesServiceImpl implements DAOService {

	@Autowired
	private RepositorioConta repositorio;
	
	@Autowired
	private RepositorioConcepto repoConcepto;
	
	@Autowired
	private RepositorioCuenta repoCuenta;
	
	@Override
	public Apunte grabar(Apunte apunte) {
		Apunte a = repositorio.save(apunte);
		return a;
	}

	@Override
	public void eliminar(String id) {
		repositorio.deleteById(id);
	}

	@Override
	public Apunte leerApunte(String id) {
		Apunte a = repositorio.findByCodigo(id);
		return a;
	}

	@Override
	public List<Apunte> leerApuntesPorFecha(LocalDate fDesde, LocalDate fHasta) {
		List<Apunte> a = repositorio.findByFechaBetweenOrderByFecha(fDesde, fHasta);
		return a;
	}

	@Override
	public List<Apunte> leerApuntesFiltro(String origen, 
										  String destino, 
										  String concepto, 
										  LocalDate fDesde,
			                              LocalDate fHasta) {
		List<Apunte> a = repositorio.findByCodOrigenLikeAndCodDestinoLikeAndCodConceptoLikeAndFechaBetweenOrderByFecha(origen,
				                                                                                                       destino,
				                                                                                                       concepto,
				                                                                                                       fDesde,
				                                                                                                       fHasta);
		return a;
	}

	
	@Cacheable("conceptos")
	@Override
	public List<Concepto> leerConceptos() {
		List<Concepto> c = repoConcepto.findAllByOrderByCodigoAsc();
		return c;
	}

	
	@CacheEvict(value = "conceptos", allEntries=true)
	@Override
	public Concepto grabarConcepto(Concepto concepto) {
		Concepto c = repoConcepto.save(concepto);
		return c;
	}

	
	@CacheEvict(value = "conceptos", allEntries=true)
	@Override
	public void eliminarConcepto(String codigo) {
		repoConcepto.deleteById(codigo);
		
	}
	
	@Override
	public boolean existeConcepto(String codigo) {
		return repoConcepto.existsById(codigo);
	}

	@Cacheable("cuentas")
	@Override
	public List<Cuenta> leerCuentas() {
		List<Cuenta> c = repoCuenta.findAllByOrderByNombreAsc();
		return c;
	}

	@CacheEvict(value = "cuentas", allEntries=true)
	@Override
	public Cuenta grabarCuenta(Cuenta cuenta) {
		Cuenta c = repoCuenta.save(cuenta);
		return c;
	}

	@CacheEvict(value = "cuentas", allEntries=true)
	@Override
	public void eliminarCuenta(String codigo) {
		repoCuenta.deleteById(codigo);
		
	}
	
	@Override
	public boolean existeCuenta(String cuenta) {
		return repoCuenta.existsById(cuenta);
	}

}
