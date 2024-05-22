import random
import pygame
import re
import pygame_menu


def read_num(text):
    try:
        num = int(input(text))
        if not (3 <= num <= 100):
            raise ValueError
        return num
    except ValueError:
        print("Invalid input, please enter integer between 3 and 100")
        return read_num(text)


def read_rules():
    rules = input("Enter rules in following format {numbers}/{numbers}, max 9 per side, from 0 to 8, no duplicates: ")

    # check if input matches requirement
    pattern = re.compile("^[0-8]+/[0-8]+$")
    if not pattern.match(rules):
        return read_rules()

    alive_rules = [int(num) for num in (rules.split("/")[0])]
    dead_rules = [int(num) for num in (rules.split("/")[1])]

    # check if rules groups contain duplicates (and length by proxy)
    if len(alive_rules) != len(set(alive_rules)) or len(dead_rules) != len(set(dead_rules)):
        return read_rules()

    return alive_rules, dead_rules


# game of life setup
size = read_num("Enter the size of the board: ")
alive_rules, dead_rules = read_rules()
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
pygame.display.set_caption("Game of life")
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
    screen.fill("white", (0, 0, screen.get_width(), screen.get_height()))

    for y in range(len(board)):
        for x in range(len(board[y])):
            process_cell(board, x, y)
            if board[y][x] == 1:
                pygame.draw.rect(screen, "black", (x * scale, y * scale, scale, scale))

    # flip() the display to put your work on screen
    pygame.display.flip()

    # limit FPS to 15
    # dt is delta time in seconds since last frame, used for framerate-independent physics.
    dt = clock.tick(15)

pygame.quit()
