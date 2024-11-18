document.addEventListener("DOMContentLoaded", () => {
    const printerForm = document.getElementById("add-printer-form");
    const printerList = document.getElementById("printer-list");
    const printerDetails = document.getElementById("printer-details");
    const detailsName = document.getElementById("details-name");
    const detailsModel = document.getElementById("details-model");
    const detailsPages = document.getElementById("details-pages");
    const removePrinterButton = document.getElementById("remove-printer");
    const editPrinterButton = document.getElementById("edit-printer");

    let selectedPrinterIndex = null;

    // Carregar impressoras salvas no localStorage
    const savedPrinters = JSON.parse(localStorage.getItem("printers")) || [];
    savedPrinters.forEach((printer, index) => addPrinterToList(printer, index));

    // Evento de submissão do formulário
    printerForm.addEventListener("submit", (event) => {
        event.preventDefault();

        const printerName = document.getElementById("printer-name").value;
        const printerModel = document.getElementById("printer-model").value;

        const printer = { name: printerName, model: printerModel, pages: 0 }; // Inicializa com 0 páginas
        savedPrinters.push(printer);

        // Salvar no localStorage
        localStorage.setItem("printers", JSON.stringify(savedPrinters));

        addPrinterToList(printer, savedPrinters.length - 1);

        printerForm.reset();
    });

    // Função para adicionar impressora na lista
    function addPrinterToList(printer, index) {
        const li = document.createElement("li");
        li.textContent = ${printer.name} - ${printer.model};
        li.addEventListener("click", () => showPrinterDetails(index));
        printerList.appendChild(li);
    }

    // Exibe os detalhes da impressora
    function showPrinterDetails(index) {
        const printer = savedPrinters[index];
        selectedPrinterIndex = index;

        detailsName.textContent = Nome: ${printer.name};
        detailsModel.textContent = Modelo: ${printer.model};
        detailsPages.textContent = Páginas Impressas: ${printer.pages};

        printerDetails.style.display = "block";
    }

    // Remover impressora
    removePrinterButton.addEventListener("click", () => {
        if (selectedPrinterIndex !== null) {
            savedPrinters.splice(selectedPrinterIndex, 1);
            localStorage.setItem("printers", JSON.stringify(savedPrinters));

            // Recarregar a lista de impressoras
            printerList.innerHTML = "";
            savedPrinters.forEach((printer, index) => addPrinterToList(printer, index));

            printerDetails.style.display = "none";
        }
    });

    // Editar impressora
    editPrinterButton.addEventListener("click", () => {
        if (selectedPrinterIndex !== null) {
            const printer = savedPrinters[selectedPrinterIndex];

            const newName = prompt("Novo nome da impressora:", printer.name);
            const newModel = prompt("Novo modelo:", printer.model);
            const newPages = prompt("Novas páginas impressas:", printer.pages);

            if (newName && newModel && newPages) {
                printer.name = newName;
                printer.model = newModel;
                printer.pages = newPages;

                localStorage.setItem("printers", JSON.stringify(savedPrinters));

                // Recarregar a lista de impressoras
                printerList.innerHTML = "";
                savedPrinters.forEach((printer, index) => addPrinterToList(printer, index));

                printerDetails.style.display = "none";
            }
        }
    });
});