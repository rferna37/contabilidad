$(() => { 
  $('input[type="file"]')
    .on('change', (e) => { 
	  let fichero =  e.target.files[0];
	  $('#info').text(fichero.name);
	  })
});


