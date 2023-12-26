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
"""
By default, the model returns a response after completing the entire generation process. 
You can also stream the response as it is being generated, and the model will return chunks of the response as soon as they are generated.
"""

response_stream = model.generate_content(question, stream=True)

for chunk in response_stream:
  print(chunk.text)
  print("_"*80)
