
$(() => {
  $apunte = $('#eliminar').attr('data-bs-apunte');
  $url = '/eliminar/' + $apunte;
  $('#ok')
    .on('click', () => { 
	  $('#confirmarModal').modal('hide');
      $.get($url, () => {
	                   //alert( "Apunte borrado" );
	                   $('#alertaModal .modal-body').html('Apunte borrado.');
	                   $('#alertaModal').modal('show');
	                   $('#alertaModal').on('hidden.bs.modal', () =>  location.href="/apuntes");
      })
      .fail( ( )  => {
			$('#alertaModal .modal-body').html('Error en la eliminación.');
	        $('#alertaModal').modal('show');	
       });
    }); 
});
