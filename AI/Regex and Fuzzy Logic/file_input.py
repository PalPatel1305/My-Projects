"""
It loads each line of the file into a list.

Functions:
    file_input(): It loads each line of the file into a list and returns it.


Pal Patel 000865048 25/10/2023
Assignment 2: Phase 1 & Phase 3
"""

def file_input(filename):
    """    
    file_input() : It loads each line of the file into a list and returns it.
        Parameter: 
            filename: Name of the file which needs to converted in the list.
        Return:
            lines: list of the content from the file.
    """
    lines = []
    with open(filename) as file: # opens the file and assigns it to a variable
        for line in file:
            lines.append(line.strip()) # the strip method removes extra whitespace
    return lines