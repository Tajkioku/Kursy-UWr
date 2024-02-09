# Serwer do API

from flask import Flask, request, jsonify
from sqlalchemy import create_engine, Column, Integer, String, Date, ForeignKey
from sqlalchemy.orm import relationship, sessionmaker, declarative_base
from sqlalchemy.orm import validates
from datetime import datetime

app = Flask(__name__)

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

# Definicje routes
@app.route('/movies', methods=['POST'])
def add_movie():
    data = request.json
    title = data['title']
    year = data['year_of_creation']
    people = data['people']
    
    try:
        new_movie = Movie(title=title, year_of_creation=datetime.strptime(year, "%Y-%m-%d").date())
        session.add(new_movie)
        session.commit()

        for person_data in people:
            person_name = person_data['name']
            person_role = person_data['role']
            
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

        return jsonify({"message": "Movie added successfully."}), 201

    except ValueError as e:
        session.rollback()
        return jsonify({"error": str(e)}), 400

@app.route('/movies/<int:movie_id>', methods=['GET'])
def get_movie(movie_id):
    movie = session.query(Movie).filter_by(id=movie_id).first()
    if movie:
        movie_data = {
            "id": movie.id,
            "title": movie.title,
            "year_of_creation": movie.year_of_creation.strftime("%Y-%m-%d"),
            "people": []
        }
        associations = session.query(Association).filter_by(movie_id=movie.id).all()
        for assoc in associations:
            person = session.query(Person).filter_by(id=assoc.person_id).first()
            if person:
                person_data = {
                    "name": person.name,
                    "role": assoc.role
                }
                movie_data["people"].append(person_data)
        return jsonify(movie_data)
    else:
        return jsonify({"error": "Movie not found."}), 404

# Funkcje update i search z main.py - nie zaimplementowane

if __name__ == '__main__':
    app.run(debug=True)
