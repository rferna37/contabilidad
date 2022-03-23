package as.fleming.rodrigo.datos;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class Mime {
	private Map<String, String> tiposmime;

	public Map<String, String> getTiposmime() {
		return tiposmime;
	}

	public void setTiposmime(Map<String, String> tiposmime) {
		this.tiposmime = tiposmime;
	}
	
	public String getTipo(String extension) {
		return tiposmime.get(extension);
	}
	
}
