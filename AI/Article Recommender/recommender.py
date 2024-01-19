""" 
This Assignment focuses on generating random article options based on the users choice and liking.
This file uses sklearn to vectorize the articles and generate the recommendation list of the articles using Cosine_similarity between vectors and respond back to the user.

File contains following fuctions:

    load_articles():
            Returns a list of articles loaded from a json or csv file with a header.
    
    init_recommendations():
            This generates n random recommendations of the articles for the first time.
            
    display_recommendations():
            Displays recommendations. 
            
    display_article():
            Displays article 'art_num' from the articles.
            
    new_recommendations():
            Generates recommendations based on the user's last choice of the article.

    main():
            Main Method
    
Pal Patel, 20/11/2023, Assignment 3.

"""

from csv import DictReader
from random import randint
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import json

NUM_RECS = 10 # number of recommendations to return to the user
vectorized_texts = None # globally declaring the vectorized_texts
def load_articles(filename, num=None, filetype="csv"):
    """Returns a list of articles loaded from a json or csv file with a header.
    Each article is a dictionary. If you use one of the files provided, each
    article will have a "title" and "text" field.

    filename = name of file to load
    num = number of articles to load (random sample), or None for all articles
    filtype = "csv" or "json" """
    
    #using the global vectorized_texts
    global vectorized_texts

    #Storing the articles 
    articles = []

    # Check the file type and store the articles accordingly
    if filetype == "csv":
        with open(filename, encoding="utf-8") as csvfile:
            reader = DictReader(csvfile)
            for row in reader:
                articles.append(row)
    elif filetype == "json":
        with open(filename, encoding="utf-8") as jsonfile:
            articles = json.loads(jsonfile.read())

    print(len(articles), "articles loaded")
    
                
                # Vectorizing the data
    
    
    # Extracting the "Text" property of the article 
    texts = [article["text"] for article in articles]
    
    #initializing vectorizer to vectorize the article text
    vectorizer = CountVectorizer(
        min_df=2,
        max_df=0.8,
        ngram_range=(1, 2) # allowed unigrams and bigrams
    )
    
    # storing the vectorized text
    vectorized_texts = vectorizer.fit_transform(texts)
    
    # If the article doesn't have the title
    for idx, row in enumerate(articles):
        if row["title"] is None:
            row["title"] = row["text"][:30]

        row["vectorized_text"] = vectorized_texts[idx]
    
    return articles

def init_recommendations(n, articles):
    """
        This generates n random recommendations of the articles for the first time.\n
        Parameter:
            n: number of recommendations to be made
            articles: all the articles from which the recommendations have to be made
        Return:
            recommendations: randomly n generated recommended articles
        
    """
    
    recommendations = []
    for _  in range(n):
        article = randint(0, len(articles)-1)
        while article in recommendations:
            article = randint(0, len(articles)-1)
        recommendations.append(article)
    return recommendations

def display_recommendations(recommendations, articles):
    """ 
        Displays recommendations. 
        Parameter:
            recommendations: should be a list of index numbers representing the recommended articles.
            articles: all the articles from which the recommendations have to be made.
        
    """
    print("\n\nHere are some new recommendations for you:\n")
    
    for i in range(len(recommendations)):
        art_num = recommendations[i]
        print(str(i+1)+".",articles[art_num]["title"])

def display_article(art_num, articles):
    """ 
        Displays article 'art_num' from the articles.
        Parameter:
            art_num: the number of the article that's needed to be displayed
            articles: all the articles from which the article needed to be displayed.
        
    """
    print("\n\n")
    print("article",art_num)
    print("=========================================")
    print(articles[art_num]["title"])
    print()
    print(articles[art_num]["text"])
    print("=========================================")
    print("\n\n")

def new_recommendations(last_choice, n, articles):
    """
        Generates recommendations based on the user's last choice of the article.

        Parameter:
        
            last_choice: index number of the last article read
            n: number of recommendations
            articles: all the articles from which the recommendations have to be made.
        
        Return:
            recommendations: randomly n generated recommended articles based on the users last choice
        
    """
    global vectorized_texts 
    # Get the index of the last chosen article and its vectorized representation
    last_selected_article_index = last_choice
    last_selected_article_vector = vectorized_texts[last_selected_article_index]

    # Calculate cosine similarity between the last chosen article and all other articles
    similarities = cosine_similarity(last_selected_article_vector, vectorized_texts)

    # Flatten the similarity matrix and convert it to a list
    similarity_scores = similarities.flatten().tolist()

    # Set similarity to last article as -1 to exclude it
    similarity_scores[last_selected_article_index] = -1

    # Get indices of top similar articles and least similar articles
    similar_articles_indices = sorted(range(len(similarity_scores)), key=lambda i: similarity_scores[i], reverse=True)
    dissimilar_articles_indices = sorted(range(len(similarity_scores)), key=lambda i: similarity_scores[i])

    # Filter out the article that the user just read
    similar_articles_indices = [idx for idx in similar_articles_indices if idx != last_selected_article_index]
    dissimilar_articles_indices = [idx for idx in dissimilar_articles_indices if idx != last_selected_article_index]

    # Ensure uniqueness based on article titles
    unique_recommendations_set = set()
    recommended_articles_indices = []

    # Add most similar articles
    for idx in similar_articles_indices:
        if articles[idx]["title"] not in unique_recommendations_set:
            recommended_articles_indices.append(idx)
            unique_recommendations_set.add(articles[idx]["title"])

    # Add a couple of least similar articles
    dissimilar_count = 0
    for idx in dissimilar_articles_indices:
        if articles[idx]["title"] not in unique_recommendations_set and dissimilar_count < 2:
            recommended_articles_indices.append(idx)
            unique_recommendations_set.add(articles[idx]["title"])
            dissimilar_count += 1

    return recommended_articles_indices[:n]

def main():
    """ Main Method """
    #Used wikipedia_sample.json file
    articles = load_articles('data\wikipedia_sample.json', filetype="json")
    print("\n\n")
    # initiating first list of recommendations
    recs = init_recommendations(NUM_RECS, articles)
    while True:
        display_recommendations(recs, articles)
        # asking user to choose from the list.
        choice = int(input("\nYour choice? "))-1
        
        # choice validation
        if choice < 0 or choice >= len(recs):
            print("Invalid Choice. Goodbye!")
            break
        
        # display the article
        display_article(recs[choice], articles)
        input("Press Enter")
        # generate a list of new recommendations based on the users choice
        recs = new_recommendations(recs[choice], NUM_RECS, articles)

if __name__ == "__main__":
    main()