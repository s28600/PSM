import random

import pygame

# game of life setup
size = 80
rules = "23/3"
alive_rules = [int(num) for num in (rules.split("/")[0])]
dead_rules = [int(num) for num in (rules.split("/")[1])]
board = [[random.randint(0, 1) for _ in range(size)] for _ in range(size)]


def process_cell(board, x, y):
    alive_neighbours = 0

    left = x - 1
    if left < 0:
        left = len(board) - 1
    up = y - 1
    if up < 0:
        up = len(board) - 1
    right = x + 1
    if right > len(board) - 1:
        right = 0
    down = y + 1
    if down > len(board) - 1:
        down = 0

    if board[up][left] == 1:
        alive_neighbours += 1
    if board[up][x] == 1:
        alive_neighbours += 1
    if board[up][right] == 1:
        alive_neighbours += 1
    if board[y][left] == 1:
        alive_neighbours += 1
    if board[y][right] == 1:
        alive_neighbours += 1
    if board[down][left] == 1:
        alive_neighbours += 1
    if board[down][x] == 1:
        alive_neighbours += 1
    if board[down][right] == 1:
        alive_neighbours += 1

    if board[y][x] == 1 and alive_neighbours not in alive_rules:
        board[y][x] = 0
    if board[y][x] == 0 and alive_neighbours in dead_rules:
        board[y][x] = 1


# pygame setup
pygame.init()
scale = 10
screen = pygame.display.set_mode((size * scale, size * scale))
clock = pygame.time.Clock()
running = True
dt = 0

while running:
    # poll for events
    # pygame.QUIT event means the user clicked X to close your window
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    # fill the screen with a color to wipe away anything from last frame
    screen.fill("white")

    for y in range(len(board)):
        for x in range(len(board[y])):
            process_cell(board, x, y)
            if board[y][x] == 1:
                pygame.draw.rect(screen, "black", (y * scale, x * scale, scale, scale))

    # flip() the display to put your work on screen
    pygame.display.flip()

    # limits FPS to 60
    # dt is delta time in seconds since last frame, used for framerate-
    # independent physics.
    dt = clock.tick(15) / 1000

pygame.quit()
