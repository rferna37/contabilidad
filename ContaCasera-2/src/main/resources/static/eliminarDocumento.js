$(() => {
  documento = $('#eliminar').attr('data-bs-documento');
  url = '/eliminarDocu/' + documento; 
  $('#ok')
    .on('click', () => { 
	  $('#confirmarModal').modal('hide');
      $.get(url, () => {
	                   //alert( "Documento borrado" );
	                   $('#alertaModal .modal-body').html('Documento borrado.');
	                   $('#alertaModal').modal('show');
	                   $('#alertaModal').on('hidden.bs.modal', () =>  location.href="/apuntes");
      })
      .fail( ( )  => {
			$('#alertaModal .modal-body').html('Error en la eliminación.');
	        $('#alertaModal').modal('show');	
       });
    }); 
});