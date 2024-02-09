# Implementacja zadania z poprzedniej listy

from sqlalchemy import create_engine, Column, Integer, String, Date, ForeignKey
from sqlalchemy.orm import relationship, sessionmaker, declarative_base
from sqlalchemy.orm import validates
import argparse
from datetime import datetime, date
from functools import lru_cache, wraps

engine = create_engine('sqlite:///my_movies.db')
Base = declarative_base()

# Loginy i hasła
USERS = {'user1': 'password1', 'admin': 'password123'}

def is_logged_in(username, password):
    return USERS.get(username) == password

# Movies class
class Movie(Base):
    __tablename__ = 'movies'
    id = Column(Integer, primary_key=True)
    title = Column(String, nullable=False)
    year_of_creation = Column(Date)
    # People jest teraz relacją do tabeli Association
    people = relationship('Association', back_populates='movie')

    @validates('title')
    def validate_title(self, key, title):
        if not title:
            raise ValueError("The title cannot be empty.")
        if len(title) < 2:
            raise ValueError("The title must be at least 2 characters long.")
        return title

    @validates('year_of_creation')
    def validate_year_of_creation(self, key, year_of_creation):
        if year_of_creation > datetime.now().date():
            raise ValueError("The year of creation cannot be in the future.")
        return year_of_creation


# Association class
class Association(Base):
    __tablename__ = 'associations'
    movie_id = Column(Integer, ForeignKey('movies.id'), primary_key=True)
    person_id = Column(Integer, ForeignKey('people.id'), primary_key=True)
    role = Column(String, nullable=False)
    # Dodanie relacji do Movie i Person
    movie = relationship("Movie", back_populates="people")
    person = relationship("Person", back_populates="movies")

    @validates('role')
    def validate_role(self, key, role):
        valid_roles = ["Director", "Actor", "Producer", "Cinematographer"]
        if role not in valid_roles:
            raise ValueError(f"Role must be one of {valid_roles}.")
        return role

# Person class
class Person(Base):
    __tablename__ = 'people'
    id = Column(Integer, primary_key=True)
    name = Column(String, nullable=False)
    # Movies jest teraz relacją do tabeli Association
    movies = relationship('Association', back_populates='person')

    @validates('name')
    def validate_name(self, key, name):
        if not name:
            raise ValueError("The name cannot be empty.")
        if len(name) < 2:
            raise ValueError("The name must be at least 2 characters long.")
        return name

# Utworz session and tables
Base.metadata.create_all(engine)
Session = sessionmaker(bind=engine)
session = Session()

# Funkcja do wypisywania wszystkich filmów i osób z nimi związanych
@lru_cache(maxsize=32) # Cache
def list_movies_and_people():
    movies = session.query(Movie).all()  # Pobranie wszystkich filmów
    for movie in movies:
        print(f"Movie: {movie.title}, Year: {movie.year_of_creation}")
        associations = session.query(Association).filter_by(movie_id=movie.id).all()
        for assoc in associations:
            person = session.query(Person).filter_by(id=assoc.person_id).one()
            print(f"Person: {person.name}, Role: {assoc.role}")

# Funkcja do wypisywania wszystkich osób i filmów, w których uczestniczą
@lru_cache(maxsize=32) # Cache
def list_people_and_movies():
    people = session.query(Person).all()  # Pobranie wszystkich osób
    for person in people:
        print(f"Person: {person.name}")
        associations = session.query(Association).filter_by(person_id=person.id).all()
        for assoc in associations:
            movie = session.query(Movie).filter_by(id=assoc.movie_id).one()
            print(f"Movie: {movie.title}, Year: {movie.year_of_creation}, Role: {assoc.role}")

# Dodanie potrzeby bycia zalogowanym
def add_movie(title, year, people):
    try:
        new_year = datetime.strptime(year, "%Y-%m-%d").date()
        new_movie = Movie(title=title, year_of_creation=new_year)
        session.add(new_movie)
        session.commit()

        for person_name, person_role in people:
            # Czy osoba istnieje 
            person = session.query(Person).filter_by(name=person_name).first()
            if not person:
                person = Person(name=person_name)
                session.add(person)
                session.commit()

            # Relacja film - osoba
            association = Association(movie_id=new_movie.id, person_id=person.id, role=person_role)
            session.add(association)
        session.commit()

    except ValueError as e:
        print(f"Error adding movie: {e}")
        session.rollback()

def add_person(name, movie_titles_and_roles):
    try:
        # Dodawanie nowej osoby
        new_person = Person(name=name)  # Tu wywoła się walidator
        session.add(new_person)
        session.commit()

        for title, role in movie_titles_and_roles:
            # Znalezienie filmu po tytule
            movie = session.query(Movie).filter_by(title=title).first()
            if movie:
                # Utworzenie nowego powiązania, jeśli film istnieje
                association = Association(movie_id=movie.id, person_id=new_person.id, role=role)
                session.add(association)
            else:
                print(f"Movie with title '{title}' does not exist.")
        session.commit()

    except ValueError as e:
        print(f"Error adding person: {e}")
        session.rollback()

def update_movie(original_title, new_title=None, new_year_of_creation=None):
    # Znalezienie filmu po oryginalnym tytule
    movie = session.query(Movie).filter_by(title=original_title).first()
    if movie:
        # Aktualizacja tytułu i/lub roku powstania, jeśli podano nowe wartości
        if new_title:
            movie.title = new_title
        if new_year_of_creation:
            movie.year_of_creation = datetime.strptime(new_year_of_creation, "%Y-%m-%d").date()
        session.commit()
        print(f"Movie '{original_title}' has been updated.")
    else:
        print(f"Movie with title '{original_title}' has not been found.")

def update_person(original_name, new_name=None, new_role=None):
    # Znalezienie osoby po oryginalnym imieniu
    person = session.query(Person).filter_by(name=original_name).first()
    if person:
        # Aktualizacja imienia i/lub roli, jeśli podano nowe wartości
        if new_name:
            person.name = new_name
        if new_role:
            # Aktualizacja roli w powiązaniach
            associations = session.query(Association).filter_by(person_id=person.id).all()
            for assoc in associations:
                assoc.role = new_role
        session.commit()
        print(f"Person '{original_name}' has been updated.")
    else:
        print(f"Person named  '{original_name}' not found.")

# Dodanie cache
@lru_cache(maxsize=32)
def get_person_by_name(name):
    return session.query(Person).filter_by(name=name).first()

def search_person(name):
    # Wyszukiwanie osoby po imieniu
    person = get_person_by_name(name)
    if person:
        print(f"Person found: {person.name}")
        associations = session.query(Association).filter_by(person_id=person.id).all()
        for assoc in associations:
            movie = session.query(Movie).filter_by(id=assoc.movie_id).one()
            print(f"  Movie: {movie.title}, Year: {movie.year_of_creation}, Role: {assoc.role}")
    else:
        print(f"Person named '{name}' not found.")
    
@lru_cache(maxsize=32)
def get_movie_by_title(title):
    return session.query(Movie).filter_by(title=title).first()

def search_movie(title):
    # Wyszukiwanie filmow po tytule
    movie = get_movie_by_title(title)
    if movie:
        print(f"Movie found: {movie.title}, Production year: {movie.year_of_creation}")
        associations = session.query(Association).filter_by(movie_id=movie.id).all()
        for assoc in associations:
            person = session.query(Person).filter_by(id=assoc.person_id).one()
            print(f"  Person: {person.name}, Role: {assoc.role}")
    else:
        print(f"Movie with title '{title}' not found.")




def main():
    # Parsery
    parser = argparse.ArgumentParser(description="Movie Database Management")
    
    # Globalne argumenty dla uwierzytelniania (używane dla niektórych komend)
    parser.add_argument('--username', type=str, help='Nazwa użytkownika do logowania')
    parser.add_argument('--password', type=str, help='Hasło do logowania')

    # Listing movies and people
    parser.add_argument('--list-movies-people', action='store_true', help='List all movies and associated people')

    # List people and movies
    parser.add_argument('--list-people-movies', action='store_true', help='List all people and their associated movies')

    # Add movie
    parser.add_argument('--add-movie', action='store_true', help='Add a new movie to the database')
    parser.add_argument('--title', type=str, help='Title of the movie')
    parser.add_argument('--year', type=str, help='Year of creation (format: YYYY-MM-DD)')
    parser.add_argument('--people', nargs='+', help='List of people and their roles in the movie')

    # Add people
    parser.add_argument('--add-person', action='store_true', help='Add a new person')
    parser.add_argument('--name', type=str, help='Name of the person')
    parser.add_argument('--movies-roles', nargs='+', help='List of movies and roles in the format "MovieTitle Role"')

    # Update movie
    parser.add_argument('--update-movie', action='store_true', help='Update an existing movie')
    parser.add_argument('--old-title', type=str, help='Old title of the movie to update')
    parser.add_argument('--new-title', type=str, help='New title of the movie')
    parser.add_argument('--new-year', type=str, help='New year of creation')

    # Update person
    parser.add_argument('--update-person', action='store_true', help='Update an existing person')
    parser.add_argument('--old-name', type=str, help='Old name of the person to update')
    parser.add_argument('--new-name', type=str, help='New name of the person')
    parser.add_argument('--new-role', type=str, help='New role of the person')

    # Search movie
    parser.add_argument('--search-movie', type=str, help='Search for a movie by title')

    # Search people
    parser.add_argument('--search-person', type=str, help='Search for a person by name')

    parser.add_argument('--username', type=str, help='Username for login')
    parser.add_argument('--password', type=str, help='Password for login')


    args = parser.parse_args()

    if args.list_movies_people:
        list_movies_and_people()

    if args.list_people_movies:
        list_people_and_movies()

    if args.add_movie:
        if args.title and args.year and args.people:
            if args.username and args.password:
                # Convert people to tuples
                people_tuples = []
                for person_role in args.people:
                    parts = person_role.rsplit(' ', 1)
                    if len(parts) == 2:
                        people_tuples.append(tuple(parts))
                    else:
                        print(f"Error parsing person and role from '{person_role}'")
                        return
                if is_logged_in(args.username, args.password):
                    add_movie(args.title, args.year, people_tuples)
                else:
                    print("Authentication failed. You need to log in first.")
            else:
                print("Error: Username and password are required to add a movie.")
        else:
            print("Error: Title, year, and people are required to add a movie.")

    if args.add_person:
        if args.name and args.movies_roles:
            if args.username and args.password:
                movie_titles_and_roles = []
                for movie_role in args.movies_roles:
                    try:
                        title, role = movie_role.rsplit(' ', 1)
                        movie_titles_and_roles.append((title, role))
                    except ValueError:
                        print(f"Error parsing movie title and role from '{movie_role}'")
                        return
                add_person(args.username, args.password, args.name, movie_titles_and_roles)
            else:
                print("Error: Username and password are required to add a person.")
        else:
            print("Error: Name and at least one movie title and role are required to add a person.")

    if args.update_movie and args.old_title and args.new_title and args.new_year:
        if args.username and args.password:
            update_movie(args.username, args.password, args.old_title, args.new_title, args.new_year)
        else:
            print("Error: Username and password are required to update a movie.")

    if args.update_person and args.old_name and args.new_name and args.new_role:
        if args.username and args.password:
            update_person(args.username, args.password, args.old_name, args.new_name, args.new_role)
        else:
            print("Error: Username and password are required to update a person.")

    if args.search_movie:
        search_movie(args.search_movie)

    if args.search_person:
        search_person(args.search_person)

    # Przykladowe uzycie
    # is_logged_in(
    # add_movie("user1", "password1", "Incepcja", "2010-07-16", [("Christopher Nolan", "Director"), ("Leonardo DiCaprio", "Actor")])
    # add_movie("The Dark Knight", "2008-07-18", [("Christopher Nolan", "Director"), ("Christian Bale", "Actor")])
    
    # print("Lista filmów i osób:")
    # list_movies_and_people()

    # # add_person("Jan Nowak", [("Inception", "Director"), ("Interstellar", "Cinematographer")])

    # print("\nLista osób i filmów:")
    # list_people_and_movies()

    # add_movie("Przykładowy Film", "2020-01-01", [("Jan Kowalski", "Actor")])
    # update_movie("Przykładowy Film", new_title="Zaktualizowany Film", new_year_of_creation="2021-01-01")
    # update_person("Jan Kowalski", new_name="Jan Nowak", new_role="Director")

    # search_movie("Inception")
    # search_person("Leonardo DiCaprio")


if __name__ == "__main__":
    main()