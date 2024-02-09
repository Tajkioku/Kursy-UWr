def kompresja(text):
    compressed = []
    current = text[0]
    count = 1

    for char in tekst[1:]:
        if char == current:
            count += 1
        else:
            compressed.append((count, current))
            current = char
            count = 1

    compressed.append((count, current))
    return compressed

def dekompresja(text):
    decompressed = ""
    for count, char in text:
        decompressed += char * count
    return decompressed

# Przykład użycia
tekst = ""
tekst_skompresowany = kompresja(tekst)
print(tekst_skompresowany)
tekst_zdekompresowany = dekompresja(tekst_skompresowany)
print(tekst_zdekompresowany)

print()

with open('tekst.txt', 'r', encoding='utf-8') as file:
    tekst = file.read()
uproszczone = kompresja(tekst)
print(uproszczone)