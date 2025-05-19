# Client do uzycia funkcji z API

import requests
import json

base_url = 'http://127.0.0.1:5000'

def add_movie(title, year_of_creation, people):
    # Dane do wyslania JSON
    movie_data = {
        'title': title,
        'year_of_creation': year_of_creation,
        'people': people
    }

    # Zapytanie o dodanie filmu
    response = requests.post(f'{base_url}/movies', json=movie_data)

    if response.status_code == 201:
        print(f'Movie added: {title}')
    else:
        print(f'Failed to add the movie. Response code: {response.status_code}')

def get_movie(movie_id):
   # Zapytanie o pobranie filmu
    response = requests.get(f'{base_url}/movies/{movie_id}')

    if response.status_code == 200:
        movie_info = response.json()
        print(f'Movie information:')
        print(f'Title: {movie_info["title"]}')
        print(f'Year of creation: {movie_info["year_of_creation"]}')
        print(f'People associated with the movie:')
        for person in movie_info["people"]:
            print(f'Name: {person["name"]}, Role: {person["role"]}')
    else:
        print(f'Failed to retrieve movie information. Response code: {response.status_code}')

# Funkcje update i search z main.py - nie zaimplementowane

if __name__ == "__main__":
    add_movie("Inception", "2010-07-16", [
        {"name": "Christopher Nolan", "role": "Director"},
        {"name": "Leonardo DiCaprio", "role": "Actor"}
    ])

    get_movie(1)
