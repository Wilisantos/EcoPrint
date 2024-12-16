document.addEventListener("DOMContentLoaded", () => {
    // Pegando o parâmetro printerId da URL
    const urlParams = new URLSearchParams(window.location.search);
    const printerId = urlParams.get('printerId');

    // Função para enviar o trabalho de impressão
    document.getElementById("print-form").addEventListener("submit", (event) => {
        event.preventDefault();

        const description = document.getElementById("fileDescription").value;
        const fileAttachment = document.getElementById("fileAttachment").files[0];

        if (description && fileAttachment) {
            const formData = new FormData();
            formData.append("description", description);
            formData.append("file", fileAttachment);
            formData.append("printerId", printerId); // Incluindo o ID da impressora

            // Enviando a impressão
            fetch("http://localhost:9000/impressoras/imprimir", {
                method: "POST",
                body: formData,
            }).then(response => response.json())
              .then(data => {
                  console.log("Impressão realizada", data);
                  alert("Impressão realizada com sucesso!");
                  window.location.href = "index.html"; // Redireciona de volta para a página inicial (ajuste conforme necessário)
              })
              .catch(error => {
                  console.error("Erro na impressão", error);
                  alert("Erro ao enviar a impressão.");
              });
        } else {
            alert("Por favor, preencha todos os campos.");
        }
    });
});
