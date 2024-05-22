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
    left = x-1
    if left < 0:
        left = len(board)-1


for row in board:
    print(row)

# pygame setup
pygame.init()
scale = 10
screen = pygame.display.set_mode((size*scale, size*scale))
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
            if board[y][x] == 1:
                pygame.draw.rect(screen, "black", (y * scale, x * scale, scale, scale))

    # flip() the display to put your work on screen
    pygame.display.flip()

    # limits FPS to 60
    # dt is delta time in seconds since last frame, used for framerate-
    # independent physics.
    dt = clock.tick(60) / 1000

pygame.quit()
