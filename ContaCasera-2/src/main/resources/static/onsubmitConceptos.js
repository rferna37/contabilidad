let formulario = document.querySelector("form");
  formulario.onsubmit = async (e) => {
    e.preventDefault();
alert("Pasó por aquí");

    let response = await fetch('/grabarConcepto', {
      method: 'POST',
      body: new FormData(manteConceptos)
    });

    let result = await response.json();

    alert(result.message);
  };
