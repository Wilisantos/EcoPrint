import json
from PIL import Image, ImageCms

def convert_to_cmyk(image):
    """
    Converte uma imagem RGB para CMYK usando perfis de cores.
    """
    # Caminho para os perfis ICC
    srgb_profile_path = ImageCms.createProfile("sRGB")
    cmyk_profile_path = "USWebUncoated.icc"  # Substitua pelo caminho correto

    # Carrega os perfis
    cmyk_profile = ImageCms.getOpenProfile(cmyk_profile_path)
    
    # Cria a transformação de cores
    transform = ImageCms.buildTransform(srgb_profile_path, cmyk_profile, "RGB", "CMYK")
    return ImageCms.applyTransform(image, transform)

def get_cmyk_totals(image_path):
    """
    Calcula os totais normalizados de CMYK para uma imagem.
    """
    # Carrega a imagem
    image = Image.open(image_path)
    
    # Converte a imagem para CMYK com precisão
    if image.mode != "CMYK":
        image = convert_to_cmyk(image)
    
    # Obtem os pixels da imagem como uma lista de tuplas (C, M, Y, K)
    pixels = list(image.getdata())

    # Debug: Exibe os primeiros 10 pixels para análise
    print(pixels)
    
    # Inicializa os somatórios de cada canal
    c_total, m_total, y_total, k_total = 0, 0, 0, 0
    
    # Soma os valores de cada canal
    for c, m, y, k in pixels:
        c_total += c
        m_total += m
        y_total += y
        k_total += k
    
    # Conta o número total de pixels
    total_pixels = len(pixels)
    if total_pixels == 0:
        raise ValueError("A imagem não contém pixels válidos.")
    
    # Exibe os totais para depuração
    print(f"Soma total dos canais: C={c_total}, M={m_total}, Y={y_total}, K={k_total}")
    
    # Retorna os totais normalizados por pixel
    return {
        "Ciano (C)": c_total / total_pixels,
        "Magenta (M)": m_total / total_pixels,
        "Amarelo (Y)": y_total / total_pixels,
        "Preto (K)": k_total / total_pixels,
    }

# Caminho da imagem
image_path = r"john.jpeg"

try:
    # Calcula os totais de CMYK
    cmyk_totals = get_cmyk_totals(image_path)

    # Salva o resultado em um arquivo JSON
    output_file = "cmyk_totals.json"
    with open(output_file, "w") as json_file:
        json.dump(cmyk_totals, json_file, indent=4)

    print(f"Totais de CMYK salvos em {output_file}.")
except Exception as e:
    print(f"Erro: {e}")
