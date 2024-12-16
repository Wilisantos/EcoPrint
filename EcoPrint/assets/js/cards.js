document.addEventListener("DOMContentLoaded", () => {
    // Função para carregar a quantidade de tipos de impressoras
    async function carregarQuantidadeTiposImpressora() {
        try {
            const response = await fetch('http://localhost:9000/tipos-impressora/count');
            if (!response.ok) {
                const errorText = await response.text();
                console.error("Erro ao carregar quantidade de tipos de impressoras:", errorText);
                throw new Error("Erro ao carregar quantidade de tipos de impressoras.");
            }

            const count = await response.json(); // A resposta deve ser um número
            // Atualiza o conteúdo do h2 com a quantidade de tipos de impressoras
            document.getElementById("tipo-impressora").textContent = `${count}`;

        } catch (error) {
            console.error("Erro:", error.message);
            document.getElementById("tipo-impressora").textContent = "Erro ao carregar a quantidade.";
        }
    }
    async function carregarQuantidadeImpressoras() {
        try {
            const response = await fetch('http://localhost:9000/impressoras/count');
            if (!response.ok) {
                const errorText = await response.text();
                console.error("Erro ao carregar quantidade de impressoras:", errorText);
                throw new Error("Erro ao carregar quantidade de impressoras.");
            }

            const count = await response.json(); // A resposta deve ser um número
            // Atualiza o conteúdo do h2 com a quantidade de impressoras
            document.getElementById("numero-impressora").textContent = `${count}`;

        } catch (error) {
            console.error("Erro:", error.message);
            document.getElementById("numero-impressora").textContent = "Erro ao carregar a quantidade de impressoras.";
        }
    }

    // Função para carregar a quantidade de impressões
    async function carregarQuantidadeImpressoes() {
        try {
            const response = await fetch('http://localhost:9000/impressoes/count');
            if (!response.ok) {
                const errorText = await response.text();
                console.error("Erro ao carregar quantidade de impressões:", errorText);
                throw new Error("Erro ao carregar quantidade de impressões.");
            }

            const count = await response.json(); // A resposta deve ser um número
            // Atualiza o conteúdo do h2 com a quantidade de impressões
            document.getElementById("numero-impressao").textContent = `${count}`;

        } catch (error) {
            console.error("Erro:", error.message);
            document.getElementById("numero-impressao").textContent = "Erro ao carregar a quantidade de impressões.";
        }
    }

    // Carregar as quantidades ao carregar a página
    carregarQuantidadeImpressoras();
    carregarQuantidadeImpressoes();
    carregarQuantidadeTiposImpressora();
});
