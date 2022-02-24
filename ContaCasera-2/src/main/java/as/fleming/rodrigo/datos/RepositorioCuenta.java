package as.fleming.rodrigo.datos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import as.fleming.rodrigo.entidades.Cuenta;

public interface RepositorioCuenta extends CrudRepository<Cuenta, String> {
	List<Cuenta> findAllByOrderByNombreAsc();
}
