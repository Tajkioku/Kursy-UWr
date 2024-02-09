import urllib.request

# Bierze adres url -> zwraca zawartosc strony internetowej
def get_page_content(url):
    try:
        with urllib.request.urlopen(url) as response:
            content = response.read().decode('utf-8')
        return content
    except Exception as e:
        print(f"Blad pobierania zawartosci z {url}: {e}")
        return None
    
# Tworze indeksy slow wystepujacych na stronach
# Slownik: klucze to slowa, wartosci to listy strona na ktorcyh wystepuje slowo
def create_index(page_urls):
    index = {}
    for page_id, page_url in enumerate(page_urls):
        page_content = get_page_content(page_url)
        if page_content:
            words = page_content.split()
            for word in words:
                word = word.strip('.,!?"\'').lower()

                if word not in index:
                    index[word] = [page_id]
                else:
                    index[word].append(page_id)

    return index

# Znajduje najpopularniejsza strone dla slowa (strony numeruje od 0)
def most_popular_website(index, word):
    if word in index:
        appearances = index[word]
        # Licze ilosc wystapien danej strony dla slowa
        # set(appearances), zeby przejsc po liscie stron bez duplikatow
        page_counts = {page_id: appearances.count(page_id) for page_id in set(appearances)}
        # Zwracam numer strony na ktorej slowo wystepuje najwiecej razy
        most_popular_page = max(page_counts, key=page_counts.get)
        return most_popular_page
    else:
        print(f"Slowo '{word}' nie znalezione.")
        return None



# Test
page_urls = [
    "https://ii.uni.wroc.pl/dla-studenta/informacje-praktyczne",
    "https://wolnelektury.pl/katalog/lektura/romeo-i-julia.html"
]

index = create_index(page_urls)

word_to_search = "informatyka"
most_popular_page = most_popular_website(index, word_to_search)
print(f"Slowo '{word_to_search}' najpopularniejsze dla strony {most_popular_page}.")

word_to_search2 = "romeo"
most_popular_page2 = most_popular_website(index, word_to_search2)
print(f"Slowo '{word_to_search2}' najpopularniejsze dla strony {most_popular_page2}.")