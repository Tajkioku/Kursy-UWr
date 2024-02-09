import urllib.request
import threading

# W ponizszym rozwiazanku dodalem jedynie wykonywanie w odrebnych watkach.
# Nie poprawilem problemow programu z listy6.

# Bierze adres url -> zwraca zawartosc strony internetowej
def get_page_content(url):
    try:
        with urllib.request.urlopen(url) as response:
            content = response.read().decode('utf-8')
        return content
    except Exception as e:
        print(f"Blad pobierania zawartosci z {url}: {e}")
        return None


# Slownik: klucze to slowa, wartosci to listy strona na ktorcyh wystepuje slowo
def process_page(page_id, page_url, index):
    page_content = get_page_content(page_url)
    if page_content:
        words = page_content.split()
        for word in words:
            word = word.strip('.,!?"\'').lower()
            if word not in index:
                index[word] = [page_id]
            else:
                index[word].append(page_id)

# Tworze indeksy slow wystepujacych na stronach wykorzystujac process_page w osobnych watkach
def create_index(page_urls):
    index = {}

    threads = []
    for page_id, page_url in enumerate(page_urls):
        thread = threading.Thread(target=process_page, args=(page_id, page_url, index))
        threads.append(thread)
        thread.start()

    for thread in threads:
        thread.join()

    return index

def most_popular_website(index, word):
    if word in index:
        appearances = index[word]
        page_counts = {page_id: appearances.count(page_id) for page_id in set(appearances)}
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
