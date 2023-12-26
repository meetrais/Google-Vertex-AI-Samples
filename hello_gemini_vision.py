import PIL.Image
import google.generativeai as genai
import apikeys

GOOGLE_API_KEY= apikeys.GOOGLE_API_KEY

genai.configure(api_key=GOOGLE_API_KEY)
img = PIL.Image.open('images/image.jpg')
model = genai.GenerativeModel('gemini-pro-vision')

response = model.generate_content(img)

print(response.text)