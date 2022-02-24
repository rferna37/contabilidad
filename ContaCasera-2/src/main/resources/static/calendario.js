
$(() => {
  /* Asigna un calendario para seleccionar una fecha
   * a cada campo que tenga la clase 'fecha'.
   * Al entrar en el campo aparece el calendario.
  */
  
  $('.fecha').datepicker({ 
    language: "es",
        todayBtn: "linked",
        clearBtn: true,
        format: "dd/mm/yyyy",
        multidate: false,
        todayHighlight: true,
        autoclose: true
  });  
});