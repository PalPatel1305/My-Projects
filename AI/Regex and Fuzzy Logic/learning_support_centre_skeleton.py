"""
This file loads the data, check the utterance pattern in the regex logic file and return the response as per the utterance index from the answers.txt.
Functions :

    load_FAQ_data() : load the data of answers and regexlogic from the text file in to an array.   
    custom_comparison(): creating a key for sorting the list          
    find_Regex_Pattern(): Take the utterance of the user find the best match from the regex and return the index of response.
    generate(): Genrate the response through the best match regex index      
    main():Implements a chat session in the shell.
        
Pal Patel 000865048 25/10/2023
Assignment 2: Phase 1 & Phase 3
"""

from file_input import *
import regex as re

def load_FAQ_data():
    """
    load_FAQ_data() : load the data of answers and regexlogic from the text file in to an array. \n
        Return:
            answers: Answers array made from the answer.txt
            regexPatterns: Regex logic made from the fuzzy_logic.txt
    """


        
    answers= file_input("answers.txt")

    regexPatterns=file_input("regex_logic_for_bot.txt")

    return answers, regexPatterns
    


def custom_comparison(item):
     """
     creating a key for sorting the list items by heuristic_score(reverse) and count[0] which is error counts \n
     Parameter:
            item: taking each item of the list thats need to be sorted
     Return:
            (-item['heuristic_score'], item['counts'][0]): tuple of the keys: heuristic_score in the reverse order and counts[0] in the ascending order.
     """

     return (-item['heuristic_score'], item['counts'][0])




def find_Regex_Pattern(utterance):
    """
    find_Regex_Pattern(): Take the utterance of the user find the best match from the regex and return the index of response. \n
        Parameter: 
            Utterance: passing the utterance of the user
        Return : 
            best_Match_Regex_Index: return the best match index in the regex array to generate the response.
            If there is no match or any error returns -1 as index
    """
    global regexlogic
    matches=[]
    try:
        for logic in regexlogic:
            match = re.search(logic, utterance, flags=re.IGNORECASE|re.BESTMATCH)

            # appending useful values for calculating the best match 
            if match!=None:
                matches.append({
                    "intent":match.group(0),
                    "logic":logic,
                    "counts":match.fuzzy_counts    
                })

        # calculating the heuristic_score for each item
        for item in matches:
            length = len(item['intent'])
            errors = item['counts'][0] 
            heuristic_score = length - errors * 3
            item['heuristic_score'] = heuristic_score


        # Sort the list by 'heuristic_score'and error counts by fuzzy_counts using the custom comparison function
        sorted_list = sorted(matches, key=custom_comparison)
        if len(matches)==0:
            return -1
        else:
            # Getting the first item from the sorted_list which would be the best match after the sorting
            best_Match_Regex_Index = regexlogic.index(sorted_list[0]['logic'])
            return best_Match_Regex_Index


    except ValueError:
        return -1


def generate(intent):
    """
    generate(): Genrate the response through the best match regex index
        Parameter:
            intent: Index to generate the response from answers array
        Return:
            responses[intent]: response at the intent(index) in the responses array
    """

    global responses # declare that we will use a global variable

    if intent == -1:
        return "Sorry, I don't know the answer to that! Ask about Services, Timings of an appointment, how to book an appointment and related questions."

    return responses[intent]

## Load the questions and responses
responses, regexlogic= load_FAQ_data()

## Main Function

def main():
    """Implements a chat session in the shell."""
    print("Hello! I know stuff about Learning Support Centre, How can I help you? When you're done talking, just say 'goodbye'.")
    print()
    utterance = ""
    # It will run until user says goodbye.
    while True:
        utterance = input(">>> ")
        intent= find_Regex_Pattern(utterance.strip().lower())
        response = generate(intent)
        print(response)
        if intent == 22:
            break
                    
        print()

## Run the chat code
if __name__ == "__main__":
    main()