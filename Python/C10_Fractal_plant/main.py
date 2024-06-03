import turtle
import sys

# Increasing limit to avoid Stack Overflow
sys.setrecursionlimit(10000)

# Setup for logic
rules = {
    'X': "F+[[X]-X]-F[-FX]+X",
    'F': "FF"
}
word = "X"
iterations = 5
angle = 25
length = 3

# Creating the whole sequence
for _ in range(iterations):
    new_word = ""
    for char in word:
        new_word += rules.get(char, char)
    word = new_word

# Setup for drawing
turtle.speed("fastest")
turtle.bgcolor("white")
turtle.color("green")
turtle.width(2)
turtle.hideturtle()
turtle.penup()
turtle.goto(0, 0)
turtle.pendown()
turtle.setheading(90)

stack = []
for char in word:
    if char == "F":
        turtle.forward(length)
    elif char == "+":
        turtle.left(angle)
    elif char == "-":
        turtle.right(angle)
    elif char == "[":
        position = turtle.position()
        heading = turtle.heading()
        stack.append((position, heading))
    elif char == "]":
        position, heading = stack.pop()
        turtle.penup()
        turtle.setposition(position)
        turtle.setheading(heading)
        turtle.pendown()
turtle.done()
