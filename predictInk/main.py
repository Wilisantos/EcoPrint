import base64
from fastapi import FastAPI, HTTPException
from fastapi.responses import JSONResponse
from PIL import Image
import os
import sys
import requests
from io import BytesIO
from cmyk_analyzer import *

app = FastAPI()
cmyk_analyzer = CMYKAnalyzer()


# Função para carregar a imagem a partir de uma string base64
def load_image_from_base64(image_base64: str) -> Image:
    try:
        # Decodifica a string base64 para bytes
        image_bytes = base64.b64decode(image_base64)
        # Usa BytesIO para tratar os bytes como um arquivo
        image_file = BytesIO(image_bytes)
        # Abre a imagem com Pillow
        image = Image.open(image_file)
        return image
    except Exception as e:
        raise ValueError(f"Erro ao carregar a imagem: {str(e)}")


from fastapi import FastAPI, HTTPException, Form
from fastapi.responses import JSONResponse


@app.post("/CmykPredictor")
async def USWebUncoated(image_base64: str):
    try:
        # Carrega a imagem a partir da string base64
        image_obj = load_image_from_base64(image_base64)

        # Processa a imagem e calcula o CMYK
        cmyk_totals = cmyk_analyzer.calculate_cmyk_totals(image_obj)

        # Retorna os resultados
        return JSONResponse(content=cmyk_totals)

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Erro ao processar a imagem: {e}")


# Executa o servidor FastAPI
if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="127.0.0.1", port=7475)
