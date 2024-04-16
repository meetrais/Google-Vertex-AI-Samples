import pathlib
import textwrap
import google.generativeai as genai
import apikeys

GOOGLE_API_KEY= apikeys.GOOGLE_API_KEY

genai.configure(api_key=GOOGLE_API_KEY)

for m in genai.list_models():
  if 'generateContent' in m.supported_generation_methods:
    print(m.name)

question="What is the meaning of life?"
model = genai.GenerativeModel('gemini-pro')
response = model.generate_content(question)
print(response.text)
#print(response.prompt_feedback)
#print(response.candidates)
