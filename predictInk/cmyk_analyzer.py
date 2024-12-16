from PIL import Image, ImageCms
import os


class CMYKAnalyzer:
    def __init__(self, cmyk_profile_path=r"C:\Users\Joao Paulo\Desktop\v3\EcoPrint\predictInk\ICCmodels\USWebUncoated.icc"):
        """
        Inicializa a classe com o caminho para o perfil ICC CMYK.
        """
        self.cmyk_profile_path = cmyk_profile_path
        self.srgb_profile = ImageCms.createProfile("sRGB")

        if not os.path.exists(self.cmyk_profile_path):
            raise FileNotFoundError(
                f"Perfil ICC não encontrado: {self.cmyk_profile_path}"
            )

        self.cmyk_profile = ImageCms.getOpenProfile(self.cmyk_profile_path)

    def convert_to_cmyk(self, image):
        """
        Converte uma imagem RGB para CMYK usando perfis de cores.
        """
        image = image.convert("RGB")
        if image.mode != "RGB":
            raise ValueError(
                "A imagem precisa estar no modo RGB para ser convertida para CMYK."
            )

        transform = ImageCms.buildTransform(
            self.srgb_profile, self.cmyk_profile, "RGB", "CMYK"
        )
        return ImageCms.applyTransform(image, transform)

    def calculate_cmyk_totals(self, image):
        """
        Calcula os totais normalizados de CMYK para uma imagem.
        """
        if image.mode != "CMYK":
            image = self.convert_to_cmyk(image)
        pixels = list(image.getdata())
        c_total, m_total, y_total, k_total = 0, 0, 0, 0
        for c, m, y, k in pixels:
            c_total += c
            m_total += m
            y_total += y
            k_total += k
        total_pixels = len(pixels)
        if total_pixels == 0:
            raise ValueError("A imagem não contém pixels válidos.")
        return f"{c_total / total_pixels},{m_total / total_pixels},{y_total / total_pixels},{k_total / total_pixels}"
