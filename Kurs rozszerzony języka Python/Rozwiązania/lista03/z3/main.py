def is_valid_move(maze, x, y, visited):
    if (x >= 0 and x < len(maze)) and (y >= 0 and y < len(maze[0])) and maze[x][y] != 'X' and (x, y) not in visited:
        return True
    return False

def find_path_in_maze(maze, start):
    # Punkt startowy poza labiryntem
    if start[0] > len(maze) or start[1] > len(maze[0]):
        return None

    stack = [start]
    visited = set()

    while stack:
        curr_x, curr_y = stack[-1]

        visited.add((curr_x, curr_y))

        # Czy znalezlismy wyjscie
        if (curr_x == len(maze) - 1) or (curr_y == len(maze[0]) - 1) or (curr_x == 0) or (curr_y == 0):
            return stack

        directions = [(0, -1), (-1, 0), (0, 1), (1, 0)]

        found_valid_move = False
        for dx, dy in directions:
            new_x, new_y = curr_x + dx, curr_y + dy
            if is_valid_move(maze, new_x, new_y, visited):
                stack.append((new_x, new_y))
                found_valid_move = True
                break

        if not found_valid_move:
            stack.pop()

    return None  # Jesli nie znalezlismy wyjscia

# Testy
maze = [
    [' ', 'X', 'X', 'X', 'X'],
    ['X', ' ', 'X', 'X', 'X'],
    ['X', ' ', ' ', ' ', 'X'],
    ['X', 'X', 'X', ' ', 'X'],
    ['X', ' ', 'X', ' ', 'X']
]

start = (10, 10)
path = find_path_in_maze(maze, start)
print(path)

start = (0, 0)
path = find_path_in_maze(maze, start)
print(path)

start = (1, 1)
path = find_path_in_maze(maze, start)
print(path)

maze = []
start = (1, 1)
path = find_path_in_maze(maze, start)
print(path)

