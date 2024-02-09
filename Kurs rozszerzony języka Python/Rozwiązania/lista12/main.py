# Implementacja zadania z poprzedniej listy

from sqlalchemy import create_engine, Column, Integer, String, Date, ForeignKey
from sqlalchemy.orm import relationship, sessionmaker, declarative_base  # Updated import here
from sqlalchemy.orm import validates
import argparse
from sqlalchemy import event
from datetime import datetime

engine = create_engine('sqlite:///my_movies.db')
Base = declarative_base()

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
def list_movies_and_people():
    movies = session.query(Movie).all()  # Pobranie wszystkich filmów
    for movie in movies:
        print(f"Movie: {movie.title}, Year: {movie.year_of_creation}")
        associations = session.query(Association).filter_by(movie_id=movie.id).all()
        for assoc in associations:
            person = session.query(Person).filter_by(id=assoc.person_id).one()
            print(f"Person: {person.name}, Role: {assoc.role}")

# Funkcja do wypisywania wszystkich osób i filmów, w których uczestniczą
def list_people_and_movies():
    people = session.query(Person).all()  # Pobranie wszystkich osób
    for person in people:
        print(f"Person: {person.name}")
        associations = session.query(Association).filter_by(person_id=person.id).all()
        for assoc in associations:
            movie = session.query(Movie).filter_by(id=assoc.movie_id).one()
            print(f"Movie: {movie.title}, Year: {movie.year_of_creation}, Role: {assoc.role}")

def add_movie(title, year, people):
    try:
        new_movie = Movie(title=title, year_of_creation=datetime.strptime(year, "%Y-%m-%d").date())
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
        new_person = Person(name=name)  # tU Wywola SIE validator
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

def search_person(name):
    # Wyszukiwanie osoby po imieniu
    person = session.query(Person).filter_by(name=name).first()
    if person:
        print(f"Person found: {person.name}")
        associations = session.query(Association).filter_by(person_id=person.id).all()
        for assoc in associations:
            movie = session.query(Movie).filter_by(id=assoc.movie_id).one()
            print(f"  Movie: {movie.title}, Year: {movie.year_of_creation}, Role: {assoc.role}")
    else:
        print(f"Person named '{name}' not found.")
    
def search_movie(title):
    # Wyszukiwanie filmu po tytule
    movie = session.query(Movie).filter_by(title=title).first()
    if movie:
        print(f"Movie found: {movie.title}, Production year: {movie.year_of_creation}")
        associations = session.query(Association).filter_by(movie_id=movie.id).all()
        for assoc in associations:
            person = session.query(Person).filter_by(id=assoc.person_id).one()
            print(f"  Person: {person.name}, Role: {assoc.role}")
    else:
        print(f"Movie with title '{title}' not found.")


# Parsery - nie zaimplementowane
# parser = argparse.ArgumentParser(description='Interfejs zarządzania filmami i osobami.')
# subparsers = parser.add_subparsers(help='dostępne komendy')

# # Dodawanie filmu
# add_movie_parser = subparsers.add_parser('dodaj_film', help='Dodaj nowy film')
# add_movie_parser.add_argument('--title', type=str, required=True, help='Tytuł filmu')
# add_movie_parser.add_argument('--year', type=lambda d: datetime.strptime(d, '%Y-%m-%d'), required=True, help='Data powstania filmu')
# add_movie_parser.add_argument('--people', nargs='+', help='Lista osób i ról')
# add_movie_parser.set_defaults(func=add_movie)

# # Wyświetlanie filmów
# list_movies_parser = subparsers.add_parser('wypisz_filmy', help='Wyświetl listę filmów')
# list_movies_parser.set_defaults(func=list_movies_and_people)

# # Dodawanie osoby
# add_person_parser = subparsers.add_parser('dodaj_osobe', help='Dodaj nową osobę')
# add_person_parser.add_argument('--name', type=str, required=True, help='Imię osoby')
# add_person_parser.set_defaults(func=add_person)

# list_people_parser = subparsers.add_parser('wypisz_osoby', help='Wyświetl listę osób')
# list_people_parser.set_defaults(func=list_people_and_movies)


# Przykladowe uzycie
def main():
    add_movie("Incepcja", "2010-07-16", [("Christopher Nolan", "Director"), ("Leonardo DiCaprio", "Actor")])
    add_movie("The Dark Knight", "2008-07-18", [("Christopher Nolan", "Director"), ("Christian Bale", "Actor")])
    
    print("Lista filmów i osób:")
    list_movies_and_people()

    add_person("Jan Nowak", [("Inception", "Director"), ("Interstellar", "Cinematographer")])

    print("\nLista osób i filmów:")
    list_people_and_movies()

    add_movie("Przykładowy Film", "2020-01-01", [("Jan Kowalski", "Actor")])
    update_movie("Przykładowy Film", new_title="Zaktualizowany Film", new_year_of_creation="2021-01-01")
    update_person("Jan Kowalski", new_name="Jan Nowak", new_role="Director")

    search_movie("Inception")
    search_person("Leonardo DiCaprio")


if __name__ == "__main__":
    main()