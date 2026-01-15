
matrix = [
    ['','','R','U','N','','','O','N'],
    ['','A','R','U','N','','','O','N'],
    ['', 'A','','','','','','','']
]

rows = len(matrix)
cols = len(matrix[0])

words = []

for row in range(rows):
    current = ""

    for c in matrix[row]:
        if c:
            current += c
        else:
            if current:
                words.append(current)
                current = ""

    if current:
        words.append(current)


print(words)