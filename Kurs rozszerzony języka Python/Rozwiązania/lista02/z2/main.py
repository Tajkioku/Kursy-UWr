def sudan(n, x, y):
    if n == 0:
        return x + y
    elif y == 0:
        return x
    else:
        return sudan(n-1, sudan(n, x, y - 1), sudan(n, x, y - 1) + y + 1)

def sudan_fast(n, x, y, cache={}):
    if (n, x, y) not in cache:
        if n == 0:
            cache[(n, x, y)] = x + y
        elif y == 0:
            cache[(n, x, y)] = x
        else:
            cache[(n, x, y)] = sudan_fast(n-1, sudan_fast(n, x, y-1), sudan_fast(n, x, y-1)+y+1, cache)
    return cache[(n, x, y)]


# Testy
print(sudan(1, 1-000-000, 25))
print(sudan_fast(1, 1-000-000, 100))
