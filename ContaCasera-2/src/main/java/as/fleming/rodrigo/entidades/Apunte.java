package as.fleming.rodrigo.entidades;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import as.fleming.rodrigo.auxform.Asiento;
import lombok.Data;



@Entity
@Table(name="apuntes")
@Data  //Anotación Lombok que genera automáticamente el constructor, getters, setters, etc.
public class Apunte {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String codigo;
	private LocalDate fecha;
	private BigDecimal importe;
	private String documento;
	
	@Column(name="concepto" )
	private String codConcepto;
	
	@Column(name="origen" )
	private String codOrigen;
	
	@Column(name="destino" )
	private String codDestino;
	
	@ManyToOne()
    @JoinColumn(name = "concepto", insertable=false, updatable=false)
	private Concepto concepto;
	
	@ManyToOne()
    @JoinColumn(name = "origen", insertable=false, updatable=false)
	private Cuenta origen;
	
	@ManyToOne()
    @JoinColumn(name = "destino", insertable=false, updatable=false)
	private Cuenta destino;
	
	public Asiento toAsiento(Asiento asiento) {
		asiento.setCodigo(codigo);
		asiento.setFecha(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		asiento.setConcepto(codConcepto);
		asiento.setOrigen(codOrigen);
		asiento.setDestino(codDestino);
		DecimalFormat df = new DecimalFormat("###,###.00");
        asiento.setImporte(df.format(importe));
		return asiento;
	}
}
