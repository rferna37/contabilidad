package as.fleming.rodrigo.datos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import as.fleming.rodrigo.entidades.Concepto;

public interface RepositorioConcepto extends CrudRepository<Concepto, String> {
	List<Concepto> findAllByOrderByCodigoAsc();
}
