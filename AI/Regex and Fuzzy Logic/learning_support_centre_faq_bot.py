"""
This file will connect to the discord and give the response to the user as per the data passed in the questions.txt and answers.txt

 Class: 
    MyClient: Make a connection with the Discord getting the utterance and revert back with the response.
    
    Functions:

        __init__():This is the constructor. Sets the default 'intents' for the bot.
            Parameter:
                self: Discord Self Client
        
                
        on_ready(): It is called when the bot is fully logged in.
            Parameter:
                self: Discord Self Client
        
        
        on_message(): It is called whenever the bot receives a message. It checks the utterance and return proper responses for the message and display it.
            Parameter:
                self: Discord Self Client
                message: Message from the discord user

    
Pal Patel, 25/10/2023
Assignment 2: Phase 1 & Phase 3
"""

import discord
from learning_support_centre_skeleton import *

## MYClient Class Definition

class MyClient(discord.Client):
    """This Class represents the Client (bot user)"""

    def __init__(self):
        """This is the constructor. Sets the default 'intents' for the bot."""
        intents = discord.Intents.default()
        intents.message_content = True
        super().__init__(intents=intents)

    async def on_ready(self):
        """It is called when the bot is fully logged in."""
        print('Logged on as', self.user)

    async def on_message(self, message):
        """It is called whenever the bot receives a message. It checks the utterance and return proper responses for the message and display it."""

        # don't respond to ourselves
        if message.author == self.user:
            return
        
        # get the utterance and generate the response
        utterance = message.content
        intent = find_Regex_Pattern(utterance.strip().lower())

        response = generate(intent)
        # send the response and shows to the user
        await message.channel.send(response)

## Set up and log in
client = MyClient()
with open("bot_token.txt") as file:
    token = file.read()
client.run(token)
