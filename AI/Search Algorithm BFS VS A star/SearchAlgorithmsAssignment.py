"""
@author: Pal Patel ID 000865048
This code compares the performance of the A* code vs the Breadth First Search BFS. The main objectives of this code are:

1. Define a grid map with obstacles, start and goal positions.
2. Implement the A* algorithm and Breadth-First Algorithm to find the shortest path from the start to each goal.
3. Visualize the grid map with A* and BFS paths, goals, and the path taken.
4. Calculate and display the length of each path and the number of explored cells.

The code defines the following functions:

- is_valid(pos, grid): Checks if a position is within the grid boundaries and not an obstacle.
- heuristic(pos, goal): Calculates the Manhattan distance heuristic between two positions.
- a_star(grid, start, goal): Finds the shortest path from the start to a goal using the A* algorithm.
- bfs(grid, start, goal):    Using the Breadth First Search, determine the shortest route between a start point and a destination.
- print_grid(grid_map, paths, goals): Prints the grid map with A* paths, goals, and the path taken.
- print_search_algorithm_results(name, grid_map, path, goal,letter,explored_set):     Print the search algorithm results which includes path length and total explored cells.

The grid map is represented as a NumPy array, 
        '.' represents open cells, 
        '■' represents obstacles, 
        '🚩' represents goals,
        letters represent path taken by different goals.

The code iterates through multiple goals, runs the A* and BFS algorithm for each goal, and displays the results, including the path length and the number of explored cells.
"""


import heapq
import numpy as np
from collections import deque

# Define the grid map with obstacles (0 represents open cells, 1 represents obstacles)
grid_map = np.array([
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 1, 1, 0, 1, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0],
    [0, 1, 1, 1, 1, 0, 1, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
])

# Define the start and goal positions
start = (0, 0)
goals = [(2, 4), (4, 4), (6, 8)]  
# Define possible movement directions (up, down, left, right)
directions = [(0, 1), (0, -1), (1, 0), (-1, 0)]


def is_valid(pos, grid):
    """
    Check if a given position is valid (inside the grid and not an obstacle).

    Args:
        pos (tuple): The position to check as a tuple (x, y).
        grid (numpy.ndarray): The grid map containing obstacle information.

    Returns:
        bool: True if the position is valid, False otherwise.
    """
    x, y = pos
    return 0 <= x < grid.shape[0] and 0 <= y < grid.shape[1] and grid[x][y] == 0

def heuristic(pos, goal):
    """
    Calculate the Manhattan distance heuristic between two positions.

    Args:
        pos (tuple): The current position as a tuple (x, y).
        goal (tuple): The goal position as a tuple (x, y).

    Returns:
        int: The Manhattan distance between the two positions.
    """
    return abs(pos[0] - goal[0]) + abs(pos[1] - goal[1])


def a_star(grid, start, goal):
    """
    Find the shortest path from a start position to a goal using the A* algorithm.

    Args:
        grid (numpy.ndarray): The grid map with obstacle information.
        start (tuple): The starting position as a tuple (x, y).
        goal (tuple): The goal position as a tuple (x, y).

    Returns:
        tuple: A tuple containing the path from start to goal and a set of explored cells.
            The path is a list of positions (tuples).
    """
    open_list = [(0, start)]  # Priority queue to store nodes to be explored
    closed_set = set()  # Set to store already explored nodes
    g_score = {start: 0}  # Dictionary to store the cost from start to each node
    f_score = {start: heuristic(start, goal)}  # Dictionary to store total cost
    came_from = {}  # Dictionary to store the previous node in the path

    while open_list:
        _, current = heapq.heappop(open_list)  # Get the node with the lowest f-score

        if current == goal:
            path = []
            while current in came_from:
                path.append(current)
                current = came_from[current]
            path.append(start)
            path.reverse()
            return path, closed_set

        closed_set.add(current)

        for direction in directions:
            next_pos = (current[0] + direction[0], current[1] + direction[1])

            if not is_valid(next_pos, grid) or next_pos in closed_set:
                continue

            tentative_g_score = g_score[current] + 1  # Tentative cost from start to next node

            if next_pos not in [item[1] for item in open_list] or tentative_g_score < g_score[next_pos]:
                came_from[next_pos] = current
                g_score[next_pos] = tentative_g_score
                f_score[next_pos] = tentative_g_score + heuristic(next_pos, goal)
                heapq.heappush(open_list, (f_score[next_pos], next_pos))  # Add to the priority queue

    return None, closed_set


def bfs(grid, start, goal):
    """
    Using the Breadth First Search, determine the shortest route between a start point and a destination.

    Args: grid (numpy.ndarray): The grid map containing the obstacle data.
        start (tuple): The tuple's initial location (x, y).
        goal (tuple): The tuple (x, y) representing the goal position.

    Returns: tuple: A tuple with a set of explored cells and the path from start to goal.
            A list of positions, or tuples, makes up the path.
    """
    #queue to store nodes 
    queue = deque([(start, [])])
    explored_cells = set()

    # until the queue has nodes 
    while queue:
        # get the current location and path
        current_location, path = queue.popleft()

        # If goal is reached return path and explored_cells
        if current_location == goal:
            return path + [current_location], explored_cells

        #add current_location in the explored_cells    
        explored_cells.add(current_location)

        # Checking the next direction 
        for x_direction, y_direction in directions:
            next_position = (x_direction + current_location[0], y_direction + current_location[1])
            # if valid add that loaction in the queue
            if is_valid(next_position, grid) and next_position not in explored_cells:
                queue.append((next_position, path + [current_location]))

    return None, explored_cells

def print_path(grid_map, path, goal, letter="N"):
    """
    Print the grid with path and goal given

    Args:
        grid_map (numpy.ndarray): The grid map with obstacle information.
        path (list): List of a path, each item in the list is a position.
        goal (pos): The goal position.

    Returns:
        None
    """
    for i in range(len(grid_map)):
        for j in range(len(grid_map[i])):
            position = (i, j)
            if position == goal:
                print("\U0001F6A9", end=" ")  # 🚩 represents the goal
            elif position in path:
                print(letter, end=" ")
            elif grid_map[i][j] == 1:
                print("■", end=" ")  #  ■  represents obstacles
            else:
                print(".", end=" ")  # . represents open cells
        print()  # Start a new line for the next row


def print_search_algorithm_results(name, grid_map, path, goal,letter,explored_set):
    """
    Print the search algorithm results which includes path length and total explored cells.

    Args:
        name(String): Name of the search algorithm. 
        grid_map (numpy.ndarray): The grid map with obstacle information.
        path (list): List of a path, each item in the list is a position.
        goal (pos): The goal position.
        letter(String): A letter to represent in the grid for that particular algorithm.
        explored_set: Explored set of that particular algorithm

    Returns:
        None
    """
    print(name)
    if path:
        print_path(grid_map, path, goal, letter)
        print(f'Path Length: {len(path) - 1}')
        print(f'Explored Cells:{len(explored_set)}')
    else:
        print("No path found for this goal.")
    print()

goal_paths_by_a_star = []
explored_cells_in_a_star = []
goal_paths_by_bfs = []
explored_cells_in_bfs = []

# Run A* and BFS for each goal and print the results
for i, goal in enumerate(goals):
    # Run A* for the current goal
    a_star_path, closed_set_a_star = a_star(grid_map, start, goal)    
    # Run BFS for the current goal
    bfs_path, explored_set_bfs = bfs(grid_map, start, goal)
    # getting the result for both algorithm
    print(f"================  Goal {i + 1} ====================")
    print_search_algorithm_results("A* Search ALgorithm",grid_map, a_star_path,goal,"A",closed_set_a_star)
    print_search_algorithm_results("BFS Search ALgorithm",grid_map, bfs_path,goal,"B",explored_set_bfs)
    