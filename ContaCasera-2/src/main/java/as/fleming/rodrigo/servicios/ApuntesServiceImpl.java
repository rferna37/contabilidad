package as.fleming.rodrigo.servicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import as.fleming.rodrigo.datos.Mime;
import as.fleming.rodrigo.datos.RepositorioConcepto;
import as.fleming.rodrigo.datos.RepositorioConta;
import as.fleming.rodrigo.datos.RepositorioCuenta;
import as.fleming.rodrigo.entidades.Apunte;
import as.fleming.rodrigo.entidades.Concepto;
import as.fleming.rodrigo.entidades.Cuenta;
import as.fleming.rodrigo.excepciones.GrabarFicheroException;

@Service
public class ApuntesServiceImpl implements DAOService {

	@Autowired
	private RepositorioConta repositorio;
	
	@Autowired
	private RepositorioConcepto repoConcepto;
	
	@Autowired
	private RepositorioCuenta repoCuenta;
	
	@Autowired
	private Mime tiposDocumento;
	
	@Value("${app.upload.dir:${user.home}}")
    public String uploadDir;
	
	@Override
	public Apunte grabar(Apunte apunte) throws GrabarFicheroException {
		Apunte a = repositorio.save(apunte);
		if (apunte.getDocumento() != null) {
			grabarDocumento(apunte.getDocumento(), "doc" + a.getCodigo());
		}
		
		return a;
	}

	@Override
	public void eliminar(String id) {
		repositorio.deleteById(id);
		eliminarDocumento("doc" + id);
	}

	@Override
	public Apunte leerApunte(String id) {
		Apunte a = repositorio.findByCodigo(id);
		if (a.getExtension() != null) {
			//a.setDocumento(leerDocumento(a.getNombreDocumento(), a.getTipoDocumento()));
			a.setDocumento(leerDocumento(a.getNombreDocumento(), a.getExtension()));
		}
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
	
	
    @Override
    public Resource loadDocumento(String filename) {
        Path rootLocation = Paths.get(uploadDir);
    	try {
            // read the file based on the filename
            Path file = rootLocation.resolve(filename);
            // get resource from path
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
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
	
	private void grabarDocumento(MultipartFile fichero, String nombre) throws GrabarFicheroException {
		/* 
		 * Se elimina del directorio de carga de documentos cualquier fichero que exista cuyo nombre 
		 * sin extensión coincida con el valor pasado como argumento.
		 * Esto sirve para el caso en que se asocia a un apunte un nuevo documento de extensión
         * distinta a la del documento que tenía asociado.
		 */
	    eliminarDocumento(nombre);
		String extension = fichero.getOriginalFilename().substring(fichero.getOriginalFilename().lastIndexOf('.'));
		try {
			Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(nombre + extension));
	        Files.copy(fichero.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
            e.printStackTrace();
            throw new GrabarFicheroException("No se puede grabar el fichero de documento. ¡Inténtelo otra vez!");
        }
	}
	
	private MultipartFile leerDocumento(String nombre, String extension) {
		String tipoDocumento = tiposDocumento.getTipo(extension);
		Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(nombre));
		File fichero = new File(copyLocation.toString());
		MultipartFile documento = null;
		try {
			documento = new MockMultipartFile(copyLocation.getFileName().toString(), 
					                          copyLocation.getFileName().toString(),
					                          tipoDocumento, new FileInputStream(fichero));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return documento;
	}
 
	/* 
	 * Se busca en el directorio de carga de documentos uno cuyo nombre sin extensión coincida
	 * con el que se pasa como argumento y, si se encuentra, se elimina.
	 */
	private void eliminarDocumento(String nombre) {
		try {
	    	  List<File> files = Files.list(Paths.get(uploadDir)) // Stream de paths de ficheros en el directorio de carga.
	    			  .map(Path::toFile)                          // Se transforman los Path en File
	    			  .filter(this.ficheroDeNombre(nombre)) // Se dejan en el Stream solo los que coinciden con el nombre del fichero sin extensión
	    			  .collect(Collectors.toList()); // Se tranforma el Stream en List
	    	  files.forEach(File::delete); // Se eliminan los ficheros de la lista, si hay alguno.
	        } catch (IOException e) {
			    e.printStackTrace();
			}
	}
	
	/*
	 * Predicado para filtrar objetos File cuyo nombre sin path y sin extensión
	 * coincidan con el argumento que se le pasa.
	 */
	
	private Predicate < File > ficheroDeNombre(String nombre) {
	    return (File f) -> {
	    	String[] partes = f.getName().split("/");
			String nombreFichero = partes[partes.length-1]; 
			String nombreSinExtension = nombreFichero.substring(0, nombreFichero.lastIndexOf('.'));
	        return nombreSinExtension.equals(nombre);
	    };
	  }
}
