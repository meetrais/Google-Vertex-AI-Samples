import google.generativeai as genai
import apikeys

GOOGLE_API_KEY= apikeys.GOOGLE_API_KEY

genai.configure(api_key=GOOGLE_API_KEY)

result = genai.embed_content(
    model="models/embedding-001",
    content="What is the meaning of life?",
    task_type="retrieval_document",
    title="Embedding of single string")

# 1 input > 1 vector output
print("Embedding of single string")
print(str(result['embedding'])[:50], '... TRIMMED]')
print("===========================================================================================")

result = genai.embed_content(
    model="models/embedding-001",
    content=[
      'What is the meaning of life?',
      'How much wood would a woodchuck chuck?',
      'How does the brain work?'],
    task_type="retrieval_document",
    title="Embedding of list of strings")

print("Embedding of list of strings")
# A list of inputs > A list of vectors output
for v in result['embedding']:
  print(str(v)[:50], '... TRIMMED ...')
