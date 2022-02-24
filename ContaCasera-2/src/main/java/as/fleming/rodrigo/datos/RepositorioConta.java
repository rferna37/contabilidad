package as.fleming.rodrigo.datos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import as.fleming.rodrigo.entidades.Apunte;

public interface RepositorioConta extends CrudRepository<Apunte, String> {
	List<Apunte> findAllByOrderByConceptoAsc();
	List<Apunte> findByFechaBetweenOrderByFecha(
			LocalDate fechaInicial, LocalDate fechaFinal);
	List<Apunte> findByCodOrigenLikeAndCodDestinoLikeAndCodConceptoLikeAndFechaBetweenOrderByFecha(String o, String d, String c, LocalDate inicio, LocalDate fin);
	Apunte findByCodigo(String codigo);

}
