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
chat = model.start_chat(history=[])

response = chat.send_message("In one sentence, explain how a computer works to a young child.")
print(response.text)
print("===========================================================================================")
print(chat.history)
print("===========================================================================================")
response = chat.send_message("Okay, how about a more detailed explanation to a high schooler?", stream=True)

for chunk in response:
  print(chunk.text)
  print("_"*80)
print("===========================================================================================")

for message in chat.history:
    print(f'**{message.role}**: {message.parts[0].text}')