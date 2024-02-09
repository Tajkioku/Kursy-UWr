def max_sublist_sum(arr):
    if not arr:
        return None

    max_sum = float("-inf")
    index = (0, 0)

    for i in range(len(arr)):
        current_sum = 0
        for j in range(i, len(arr)):
            current_sum += arr[j]
            if current_sum > max_sum:
                index = (i, j)
                max_sum = current_sum
                
    return index

# Testy
arr = [1, 2, -4, 5, -2, 3, -1, 2]
print(max_sublist_sum(arr))  # (3, 6): [5, -2, 3, -1, 2]

arr = [-1, -2, -3, -4]
print(max_sublist_sum(arr))  # (0, 0): [-1]

arr = []
print(max_sublist_sum(arr))  # None

arr = [1, 2, 3, 4, 5]
print(max_sublist_sum(arr))  # (0, 4): [1, 2, 3, 4, 5]
