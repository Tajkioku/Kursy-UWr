import random

def uprosc_zdanie(tekst, word_len, word_cnt):
    words = tekst.split()
    
    # Usuwam za dlugie slowa
    words = [x for x in words if len(x) <= word_len]
    
    # Usuwam losowe wyrazy
    if len(words) > word_cnt:
        slowa = random.sample(words, word_cnt)
    
    res = ' '.join(slowa)
    return res

# Testy
tekst = "Podział peryklinalny inicjałów wrzecionowatych \
   kambium charakteryzuje się ścianą podziałową inicjowaną \
   w płaszczyźnie maksymalnej."
uproszczone = uprosc_zdanie(tekst, 10, 5)
print(uproszczone)

print()

with open('romeo-i-julia.txt', 'r', encoding='utf-8') as file:
    tekst = file.read()
uproszczone = uprosc_zdanie(tekst, 12, 15)
print(uproszczone)
