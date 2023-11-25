from typing import List

from fastapi import FastAPI, UploadFile, File, Response
from openai import OpenAI
from pydantic import BaseModel
from starlette.responses import JSONResponse
import base64
import requests
from PIL import Image
import io
from rembg import remove
import numpy as np

# OpenAI API Key
api_key = "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"

client = OpenAI(
    api_key=api_key,
)

# Function to encode the image
def encode_image(image_bytes):
    return base64.b64encode(image_bytes).decode('utf-8')

app = FastAPI()

prompt = """
Fill the following json with what you see in the garment image:
{
  "description": "{description}",
  "category": "{category}",
  "subCategory": "{subCategory}",
  "mainColor": "{mainColor}",
  "secondColor": "{secondColor}",
  "pattern": "{pattern}",
  "brand": "{brand}",
  "fabric": "{fabric}",
  "condition": "{condition}",
  "sleeveLength": "{sleeveLength}",
  "length": "{length}",
  "size": "{sizeEnum}",
  "patternDetail": "{patternDetail}",
  "season": ["{season1}", "{season2}", "..."],
  "styles": ["{style1}", "{style2}", "..."]
}
The possible categories are: [
  "Caps",
  "Tops",
  "Bottom",
  "One-Piece",
  "Accessories",
  "Shoes"
]
The possible subcategories are (the key is the category): 
{
  "CAPS": [
    "Baseball-Caps",
    "Beanie-Wolly",
    "WIDE-Brimmed-Hat",
    "Fedora",
    "Peaky-Blinders",
    "Hats"
  ],
  "TOPS": [
    "T-Shirt",
    "Blouse",
    "Shirt",
    "Top",
    "Polo",
    "Tunic",
    "Sweater",
    "Jacket"
  ],
  "BOTTOM": [
    "Jeans",
    "Leggings",
    "Shorts",
    "Trousers",
    "Tracksuit",
    "Skirt"
  ],
  "ONE_PIECE": [
    "Dress",
    "Suit",
    "Jumpsuit"
  ],
  "SHOES": [
    "Sneakers",
    "Boots",
    "High-Heels",
    "High-Weel",
    "Sandals",
    "Loafers",
    "Wedges"
  ],
  "ACCESSORIES": [
    "Belt",
    "Accessories",
    "Scarves",
    "Jewelry"
  ]
}
The possible seasons are: [
  "Spring",
  "Summer",
  "Fall",
  "Winter",
  "I-Dont-Know",
  "All"
]
The possible styles are: [
  "Orizontal-Stripes",
  "Vertical-Stripes",
  "Oblique-Stripes",
  "Big-Dots",
  "Small-Dots",
  "Hawaiian",
  "Leopard-Print",
  "Zebra-Print",
  "Python-Print"
]
The possible patterns are: [
  "Stripes",
  "Dots",
  "Floral",
  "Geometric",
  "Animal-Print",
  "Solid-Color";
]
The possible pattern details are: [
  "Orizontal-Stripes",
  "Vertical-Stripes",
  "Oblique-Stripes",
  "Big-Dots",
  "Small-Dots",
  "Hawaiian",
  "Leopard-Print",
  "Zebra-Print",
  "Python-Print"
]
The possible fabric are: [
  "Cotton",
  "Polyester",
  "Silk",
  "Wool",
  "Linen",
  "Denim",
  "Velvet",
  "Satin",
  "Chiffon",
  "Leather",
  "Jersey",
  "Spandex",
  "Tweed",
  "Cashmere",
  "Lycra",
  "Microfiber"
]
The possible condition are: [
  "New",
  "Excellent-Condition",
  "Used-Few-Times",
  "Used-Many-Times",
  "Exhausted"
]
The possible size are: [
  "XXS",
  "XS",
  "S",
  "M",
  "L",
  "XL",
  "XXL",
  "XXXL"
]
The possible length are: [
  "Mini",
  "Midi",
  "Long"
]
DO NOT CHANGE THE JSON STRUCTURE
DO NOT ADD ANYTHING ELSE TO THE JSON
RETURN ONLY THE JSON OBJECT
IF UNABLE TO DEFINE A PROPERTY USE "Undefined" AS VALUE
DO NOT WRAP THE RESPONSE WITH ```json...```
"""

class AnalyzeResponseModel(BaseModel):
    description: str
    category: str
    subCategory: str
    mainColor: str
    secondColor: str
    pattern: str
    brand: str
    fabric: str
    condition: str
    sleeveLength: str
    length: str
    sizeEnum: str
    patternDetail: str
    season: List[str]
    styles: List[str]

@app.post("/api/analyze", response_model=AnalyzeResponseModel)
async def analyze(file: UploadFile = File(...)):
    try:
        content = await file.read()
        # Getting the base64 string
        base64_image = encode_image(content)

        headers = {
            "Content-Type": "application/json",
            "Authorization": f"Bearer {api_key}"
        }

        payload = {
            "model": "gpt-4-vision-preview",
            "messages": [
                {
                    "role": "user",
                    "content": [
                        {
                            "type": "text",
                            "text": prompt
                        },
                        {
                            "type": "image_url",
                            "image_url": {
                                "url": f"data:image/jpeg;base64,{base64_image}"
                            }
                        }
                    ]
                }
            ],
            "max_tokens": 300
        }

        response = requests.post("https://api.openai.com/v1/chat/completions", headers=headers, json=payload)
        print(response.json())
        return Response(status_code=200, content=response.json()["choices"][0]["message"]["content"], media_type="application/json")
    except Exception as e:
        return JSONResponse(status_code=500, content={"message": str(e)})


class GenerateResponseModel(BaseModel):
    textureUri: str

@app.post("/api/texture", response_model=GenerateResponseModel)
async def generateTexture(file: UploadFile = File(...)):
    try:
        content = await file.read()
        img = Image.open(io.BytesIO(content))

        # Convert to RGBA
        img.thumbnail((800, 800))
        img_rgba = img.convert('RGBA')

        # Get bytes of RGBA image
        byte_arr = io.BytesIO()
        img_rgba.save(byte_arr, format='PNG')
        byte_array_rgba = byte_arr.getvalue()
        response = client.images.edit(
            model="dall-e-2",
            image=byte_array_rgba,
            mask=getMask(byte_array_rgba),
            prompt="""
            Fill the image using the materials, patterns, styles and colors of the garment in the original image.
            The result should look like a piece of cloth.
            """,
            n=1,
            size="1024x1024"
        )
        return JSONResponse(status_code=200, content={"textureUri": response.data[0].url})
    except Exception as e:
        return JSONResponse(status_code=500, content={"message": str(e)})

def getMask(image_bytes):
    # bg_removed = remove(image)
    # pil_image = Image.open(io.BytesIO(bg_removed)).convert("RGBA")
    # np_image = np.array(pil_image)
    # # Create binary mask. Set all transparent (alpha < 255) pixels to be black and all opaque to be white
    # mask = np.zeros_like(np_image)
    # # Apply condition to change pixels
    # mask[np_image[..., 3] == 255] = [255, 255, 255, 255]  # opaque pixels to white
    # mask[np_image[..., 3] < 255] = [0, 0, 0, 255]  # transparent pixels to black
    # processed_image = Image.fromarray(np.uint8(mask))
    # processed_image.save("mask.png")
    # byte_arr = io.BytesIO()
    # processed_image.save(byte_arr, format='PNG')
    # processed_image_bytes = byte_arr.getvalue()
    # return processed_image_bytes
    image = Image.open(io.BytesIO(image_bytes))

    # Get the size of the original image
    width, height = image.size

    # Create a new image with the same size, fully transparent
    transparent_image = Image.new('RGBA', (width, height), (0, 0, 0, 0))

    # If necessary, convert to bytes
    byte_arr = io.BytesIO()
    transparent_image.save(byte_arr, format='PNG')
    transparent_image_bytes = byte_arr.getvalue()
    return  transparent_image_bytes