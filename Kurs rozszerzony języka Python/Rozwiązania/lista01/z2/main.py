import string

def is_palindrome(text):
    # Usuwam spacje
    text = text.replace(' ', '')
    # Usuwam znaki przystankowe i zamieniam na male litery
    text = ''.join(char.lower() for char in text if char not in string.punctuation)
    
    # Sprawdzam czy jest palindromema
    return text == text[::-1]

# Testy
print(is_palindrome("A man, a plan, a canal, Panama"))  # True
print(is_palindrome("race car"))  # True
print(is_palindrome("hello world"))  # False
print(is_palindrome("Eine güldne, gute Tugend: Lüge nie!"))  # True
print(is_palindrome("Míč omočím."))  # True
