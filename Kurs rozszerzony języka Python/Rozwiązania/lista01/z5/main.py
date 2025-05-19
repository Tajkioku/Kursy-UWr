# Wyznaczanie dlugosci wspolnego prefixu 2 elementow
def prefix_size(s1, s2):
    res = 0
    for i in range(min(len(s1), len(s2))):
        if s1[i].lower() != s2[i].lower():
            break
        res += 1
    return res

# Sortuje liste leksykograficznie
# Zwracam max z dlugosc wspolnego prefixu wyznaczanego dla wszystkich lst[i] i lst[i+2]
def common_prefix(lst):
    res = ''
    maxsize = 0
    lst.sort()
    for i in range(len(lst)-2):
        x = prefix_size(lst[i], lst[i+2])
        maxsize = max(maxsize, x)
        if maxsize == x:
            res = lst[i][:maxsize]
    return res.lower()

# Testy
myList = ["foo", "foobar", "foxtball", "footbag", "bar"] # foo
print(common_prefix(myList)) 
print(common_prefix(["Cyprian", "cyberotoman", "cynik", "ceniąc",  "czule"])) # cy