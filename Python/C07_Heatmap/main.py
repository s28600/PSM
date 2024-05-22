import math
import numpy as np
import matplotlib.pyplot as plt


class Matrix:
    def __init__(self, size, top, left, bottom, right):
        self.size = size
        self.top = top
        self.left = left
        self.bottom = bottom
        self.right = right

        # Creating square matrix filled with zeros
        self.matrix = np.zeros((size * size, size * size))
        # Creating vector filled with zeros
        self.vector = np.zeros(size * size)
        # Initializing matrix
        self.initialize()

    def initialize(self):
        # Counter
        index = 0
        for i in range(self.size):
            for j in range(self.size):
                # Process cell
                self.calculate_cell(i, j, index)
                # Setting diagonal cell value to -4
                self.matrix[index, index] = -4
                index += 1

    def calculate_cell(self, i, j, index):
        # Top
        if i == self.size - 1:
            self.vector[index] += self.top
        else:
            self.matrix[index, (i + 1) * self.size + j] = 1

        # Bottom
        if i == 0:
            self.vector[index] += self.bottom
        else:
            self.matrix[index, (i - 1) * self.size + j] = 1

        # Left
        if j == self.size - 1:
            self.vector[index] += self.left
        else:
            self.matrix[index, i * self.size + j + 1] = 1

        # Right
        if j == 0:
            self.vector[index] += self.right
        else:
            self.matrix[index, i * self.size + j - 1] = 1

    def get_matrix(self):
        return self.matrix

    def get_vector(self):
        return self.vector


class Gauss:
    @staticmethod
    def solve(A, B):
        n = len(A)
        for p in range(n):
            # Finding pivot in current column
            max_index = np.argmax(np.abs(A[p:, p])) + p
            # Swapping rows A and B, to transfer pivot on diagonal
            A[[p, max_index]] = A[[max_index, p]]
            B[p], B[max_index] = B[max_index], B[p]

            # Checking for identity matrix
            if abs(A[p, p]) <= math.e:
                raise RuntimeError("Matrix is an identity matrix")

            # Gaussian elimination on rest of rows
            for i in range(p + 1, n):
                alpha = A[i, p] / A[p, p]
                B[i] -= alpha * B[p]
                A[i, p:] -= alpha * A[p, p:]

        # Backwards change of Gauss
        x = np.zeros(n)
        for i in range(n - 1, -1, -1):
            x[i] = (B[i] - np.sum(A[i, i + 1:] * x[i + 1:])) / A[i, i]

        return x


# Creating initial matrix and calculating answer
matrix = Matrix(40, 200, 100, 150, 50)
answer = Gauss.solve(matrix.get_matrix(), matrix.get_vector())
heatmap = answer.reshape(matrix.size, matrix.size)

# Getting rid of minuses
for i in range(len(heatmap)):
    for j in range(len(heatmap[i])):
        heatmap[i][j] = np.abs(heatmap[i][j])

# Printing to console
for i in range(39, -1, -1):
    for j in range(39, -1, -1):
        print(f"{answer[40 * i + j]:.2f}", end=" ")
    print()

# Drawing heatmap
plt.imshow(heatmap, cmap='inferno', origin='lower')
plt.colorbar()
plt.title('Heatmap')
plt.show()
