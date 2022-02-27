
document.body.addEventListener("submit",async function enviar(e) {
	e.preventDefault(); //Evita que se ejecuten las acciones por defecto del submit
	let form = e.target;
    let response = await fetch(form.action, {
      method: form.method,
      body: new FormData(form)
    });
    let result = await response.text();  
    let index = result.indexOf("DOCTYPE");
    if(index > 0) {
	  document.querySelector('html').innerHTML = result;
    } else {
	  document.querySelector('#formulario').innerHTML = result;
	  document.querySelector("#codigo").focus();
    }
	
  });

async function altaForm(url){
	    let response = await fetch(url);
	    //let response = await fetch("/fragmento");
		let result = await response.text();
		let elem = document.querySelector('#formulario');
		elem.innerHTML = result;
		document.querySelector("#codigo").focus();
	  }
 
$(() => {
	
	$('button.eliminar')
	  .on('click', function() { 
		if ($(this).attr('alt') == 'Cancelar') {
			cancelarEdicion($(this));
		} else {
			eliminar($(this));
		}
       });   
	
	  
	$('button.editar')
	  .on('click', function(){ 
		if ($(this).attr('alt') == 'Editar') {
			editar($(this));
		} else {
			grabarEdicion($(this));
		} 
	})
	
		
	function eliminar(boton){
		let objCodigo = $(boton).parent().siblings('td:first'); 
		let titulo = $(document).attr("title");
		/* La url se obtiene a partir del título de la página al que se elimina el último caracteres.
		 * Así, la siguiente línea generará "/eliminarConcepto/codigo del concepto" o "/eliminarCuenta/codigo de cuenta" */
		let url = "/eliminar" + titulo.slice(0,titulo.length-1) + "/" + objCodigo.text(); 
		$('#confirmarModal').modal('show');
	    $('#ok')
          .on('click', () => { 
	        $('#confirmarModal').modal('hide'); 
            $.get(url, () => {
	          $('#alertaModal .modal-body').html('Concepto borrado.');
	          $('#alertaModal').modal('show');
	          $('#alertaModal').on('hidden.bs.modal', () =>  location.href="/" + titulo.toLowerCase()); // Irá a /conceptos o /cuentas.
            })
            .fail( (data)  => {
			  $('#alertaModal .modal-body').html(data.responseText);
	          $('#alertaModal').modal('show');	
            });
          })
	}
	
	function cancelarEdicion(boton){
		let objTr = $(boton).parent().parent(); 
		let tdEditable = $(objTr).children('.editable');
		let btnEditar = $(objTr).children('td').children('button.editar');
		$(objTr)
		  .removeClass('border border-warning border-3');
		$(tdEditable).children('span')
		  .removeAttr('style')
		$(tdEditable).children('input')
		  .attr('type','hidden')
		$(boton)
		  .attr('alt', 'Eliminar')
		  .tooltip("dispose")  // Quita el tooltip al botón antes de cambiar su texto.
          .attr('data-bs-original-title', 'Eliminar') 
          .tooltip(); // Vuelve a poner el tooltip al botón.
                      // Si no se quita y pone el tooltip, queda visible aunque el ratón salga del botón.
        $(boton).children('i')
          .removeClass( "bi-x-lg")
          .addClass( "bi-trash");
        $(btnEditar)
          .attr('alt', 'Editar')
          .attr('data-bs-original-title', 'Editar');
        $(btnEditar).children('i')
          .removeClass( "bi-check-lg")
          .addClass( "bi-pencil-fill");
        $(tdEditable).children('div')
	          .removeClass("d-block")
	          .addClass("d-none");
	}
	
	function editar(boton){
		let objTr = $(boton).parent().parent();
		let tdEditable = $(objTr).children('.editable');
		let btnEliminar = $(objTr).children('td').children('button.eliminar');
		$(objTr)
		  .addClass('border border-warning border-3');
        $(tdEditable).children('span')
          .css('display','none');
        $(tdEditable).children('input')
          .attr('type','text')
          .val($(tdEditable).children('span').text())
          .focus();  
        $(boton)
          .attr('alt', 'Grabar')
          .attr('data-bs-original-title', 'Grabar');
        $(boton).children('i')
          .removeClass( "bi-pencil-fill")
          .addClass( "bi bi-check-lg");
        $(btnEliminar)
          .attr('alt', 'Cancelar')
          .attr('data-bs-original-title', 'Cancelar');
        $(btnEliminar).children('i')
          .removeClass( "bi-trash")
          .addClass( "bi bi-x-lg");
	}
	
	function grabarEdicion(boton){
		let titulo = $(document).attr("title");
		let objTr = $(boton).parent().parent();   
		let url = "/modificar" + titulo.slice(0,titulo.length-1); 
		let tdEditable = $(objTr).children('.editable');                
		let codigo =  $(objTr).children('td:first').text();
		let texto = $(tdEditable).children('input').val();
		texto = texto.trim();
		let mensaje = '';
		if (texto == ''){
			mensaje = (titulo == 'Cuentas') ? 'El nombre no puede estar vacío.' : 'La descripción no puede estar vacía.';
		}
		if (texto.length > 50){
			mensaje = (titulo == 'Cuentas') ? 'El nombre no puede ser más de 50 caracteres.' : 'La descripción no puede ser más de 50 caracteres.';
		}
		if (mensaje != ''){
	        $(tdEditable).children('div')
	          .removeClass("d-none")
	          .addClass("d-block")
	          .text(mensaje);
	        $(tdEditable).children('input')
	          .focus();
		} else {
			$(tdEditable).children('div')
	          .removeClass("d-block")
	          .addClass("d-none");
		    $.get(url,
		          {"codigo":codigo,
		           "descripcion":texto
	              },
	              () => {
		            location.href="/" + titulo.toLowerCase(); // Va a /conceptos o /cuentas.
		         })
                .fail( (data)  => {
			      $('#alertaModal .modal-body').html(data.responseText);
	              $('#alertaModal').modal('show');	
	        });
		}
	}
	// Activa los tooltips.
	$('[data-bs-toggle="tooltip"]').tooltip();
	
})
