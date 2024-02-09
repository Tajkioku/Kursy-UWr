from sqlalchemy import create_engine, Column, Integer, String, ForeignKey, Date, Table
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship, sessionmaker
from datetime import datetime

engine = create_engine('sqlite:///my_movies.db')
Base = declarative_base()

# Movies class
class Movie(Base):
    __tablename__ = 'movies'
    id = Column(Integer, primary_key=True)
    title = Column(String, nullable=False)
    year_of_creation = Column(Date)
    people_involved = relationship('Person', secondary='movie_person_link')

# Person class
class Person(Base):
    __tablename__ = 'people'
    id = Column(Integer, primary_key=True)
    name = Column(String, nullable=False)
    role = Column(String)

# Many-to-many relationship between Movies and People
movie_person_link = Table('movie_person_link', Base.metadata,
    Column('movie_id', Integer, ForeignKey('movies.id')),
    Column('person_id', Integer, ForeignKey('people.id'))
)

# Create session
Base.metadata.create_all(engine)
Session = sessionmaker(bind=engine)
session = Session()

def add_movie(title, year, people):
    new_movie = Movie(title=title, year_of_creation=datetime.strptime(year, "%Y-%m-%d").date())
    session.add(new_movie)
    session.commit()

    for person_name, person_role in people:
        # Check if person exists
        person = session.query(Person).filter_by(name=person_name, role=person_role).first()
        if not person:
            person = Person(name=person_name, role=person_role)
            session.add(person)
        # Create a link movie-person
        new_movie.people_involved.append(person)
    session.commit()

def delete_movie_by_title(title):
    # Find the movie by title
    movie = session.query(Movie).filter_by(title=title).first()
    if movie:
        session.delete(movie)
        session.commit()
        print(f"Movie titled '{title}' has been deleted.")
    else:
        print(f"No movie found with title '{title}'.")

def list_movies():
    movies = session.query(Movie).all()
    for movie in movies:
        print(f"Movie ID: {movie.id}, Title: {movie.title}, Year of Creation: {movie.year_of_creation}")
        for person in movie.people_involved:
            print(f"Involved: {person.name}, Role: {person.role}")

def list_people():
    people = session.query(Person).all()
    for person in people:
        print(f"Person ID: {person.id}, Name: {person.name}, Role: {person.role}")




def main():
    add_movie("Inception", "2010-07-16", [("Christopher Nolan", "Director"), ("Wally Pfister", "Cinematographer")])
    add_movie("Interstellar", "2014-11-07", [("Christopher Nolan", "Director"), ("Hoyte van Hoytema", "Cinematographer")])
    add_movie("The Dark Knight", "2008-07-18", [("Christopher Nolan", "Director"), ("Wally Pfister", "Cinematographer")])

    print("Movies after adding:")
    list_movies()

    delete_movie_by_title("Interstellar")

    print("\nMovies after deletion:")
    list_movies()

# Run the main function
if __name__ == "__main__":
    main()