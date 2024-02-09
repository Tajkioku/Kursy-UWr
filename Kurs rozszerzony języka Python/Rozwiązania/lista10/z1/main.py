import matplotlib.pyplot as plt
import matplotlib.animation as animation
import random

# Plansza
N = 10
fig, ax = plt.subplots()
ax.set_xlim(0, N)
ax.set_ylim(0, N)
ax.set_aspect('equal')

# Przeszkody
M = 2
squares = []
for _ in range(M):
    x, y = random.randint(1, N-1), random.randint(1, N-1)
    while((x, y) in squares):
        x, y = random.randint(1, N-1), random.randint(1, N-1)
    squares.append((x, y))
    square = plt.Rectangle((x-0.5, y-0.5), 1, 1, fc='blue')
    ax.add_patch(square)

# Punkt startowy
snake = [(random.randint(1, N-1), random.randint(1, N-1))]
while(snake[0] in squares):
    snake = [(random.randint(1, N-1), random.randint(1, N-1))]

prev_direction = None

# Tworze osobne listy dla koordynatów x i y
# snake_plot, biore pojedynczy element list
snake_plot, = plt.plot([pt[0] for pt in snake], [pt[1] for pt in snake], 'ro-')

def is_collision(new_head):
    return new_head in snake or new_head in squares

def update(frame):
    global prev_direction
    head = snake[-1]

    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    # Usuniecie ruchu o 180 stopni
    if prev_direction is not None:
        directions.remove((-prev_direction[0], -prev_direction[1]))
    
    # Nowy ruch
    direction = random.choice(directions)
    prev_direction = direction
    new_head = (head[0] + direction[0], head[1] + direction[1])
    
    # Koniec animacji gdy przeszkoda lub krawedz planszy
    if is_collision(new_head) or not (0 < new_head[0] < 9 and 0 < new_head[1] < 9):
        anim.event_source.stop()
    
    snake.append(new_head)
    snake_plot.set_data([pt[0] for pt in snake], [pt[1] for pt in snake])
    return snake_plot,



anim = animation.FuncAnimation(fig, update, frames=10, interval=500, blit=True)
plt.show()