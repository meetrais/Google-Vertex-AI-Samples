import PIL.Image
import google.generativeai as genai
import apikeys

GOOGLE_API_KEY= apikeys.GOOGLE_API_KEY

genai.configure(api_key=GOOGLE_API_KEY)
img = PIL.Image.open('images/image.jpg')
model = genai.GenerativeModel('gemini-pro-vision')

response = model.generate_content(img)

print(response.text)
print("===========================================================================================")
response = model.generate_content(["Write a short, engaging blog post based on this picture. It should include a description of the meal in the photo and talk about my journey meal prepping.", img], stream=True)
response.resolve()

print(response.text)